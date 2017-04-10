package org.alfonz.samples.alfonzrest;

import android.databinding.ObservableField;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.samples.alfonzmvvm.BaseViewModel;
import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RestHttpLogger;
import org.alfonz.samples.alfonzrest.rest.RestResponseHandler;
import org.alfonz.samples.alfonzrest.rest.provider.RepoRxServiceProvider;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;


public class RestSampleRxViewModel extends BaseViewModel<RestSampleView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<RepoEntity> repo = new ObservableField<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(repo.get() == null) loadData();
	}


	@Override
	public void onDestroy()
	{
		super.onDestroy();

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
			if(!mRestRxManager.isRunning(RepoRxServiceProvider.REPO_CALL_TYPE))
			{
				// show progress
				state.set(StatefulLayout.State.PROGRESS);

				// subscribe
				Single<Response<RepoEntity>> rawSingle = RepoRxServiceProvider.getService().repo("petrnohejl", "Alfonz");
				Single<Response<RepoEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, RepoRxServiceProvider.REPO_CALL_TYPE);
				Disposable disposable = single.subscribeWith(createRepoObserver());
				mRestRxManager.registerDisposable(disposable);
			}
		}
		else
		{
			// show offline
			state.set(StatefulLayout.State.OFFLINE);
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
			state.set(StatefulLayout.State.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.State.EMPTY);
		}
	}
}
