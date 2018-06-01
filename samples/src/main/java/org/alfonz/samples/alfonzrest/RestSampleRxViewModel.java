package org.alfonz.samples.alfonzrest;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RestHttpLogger;
import org.alfonz.samples.alfonzrest.rest.RestResponseHandler;
import org.alfonz.samples.alfonzrest.rest.provider.RepoRxServiceProvider;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class RestSampleRxViewModel extends BaseViewModel implements LifecycleObserver
{
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<RepoEntity> repo = new MutableLiveData<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(repo.getValue() == null) loadData();
	}


	@Override
	public void onCleared()
	{
		super.onCleared();

		// unsubscribe
		mRestRxManager.disposeAll();
	}


	public void refreshData()
	{
		loadData();
	}


	private void loadData()
	{
		if(NetworkUtility.isOnline(getApplicationContext()))
		{
			String callType = RepoRxServiceProvider.REPO_CALL_TYPE;
			if(!mRestRxManager.isRunning(callType))
			{
				// show progress
				state.setValue(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<RepoEntity>> rawSingle = RepoRxServiceProvider.getService().repo("petrnohejl", "Alfonz");
				Single<Response<RepoEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createRepoObserver());
			}
		}
		else
		{
			// show offline
			state.setValue(StatefulLayout.OFFLINE);
		}
	}


	private DisposableSingleObserver<Response<RepoEntity>> createRepoObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					repo.setValue(response.body());
					setState(repo);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
					setState(repo);
				}
		);
	}


	private void setState(MutableLiveData<RepoEntity> data)
	{
		if(data.getValue() != null)
		{
			state.setValue(StatefulLayout.CONTENT);
		}
		else
		{
			state.setValue(StatefulLayout.EMPTY);
		}
	}
}
