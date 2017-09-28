package org.alfonz.samples.alfonzrest;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;

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
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<RepoEntity> repo = new ObservableField<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(repo.get() == null) loadData();
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
				state.set(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<RepoEntity>> rawSingle = RepoRxServiceProvider.getService().repo("petrnohejl", "Alfonz");
				Single<Response<RepoEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createRepoObserver());
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.OFFLINE);
		}
	}


	private DisposableSingleObserver<Response<RepoEntity>> createRepoObserver()
	{
		return AlfonzDisposableSingleObserver.newInstance(
				response ->
				{
					repo.set(response.body());
					setState(repo);
				},
				throwable ->
				{
					handleError(mRestRxManager.getHttpErrorMessage(throwable));
					setState(repo);
				}
		);
	}


	private void setState(ObservableField<RepoEntity> data)
	{
		if(data.get() != null)
		{
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}
}
