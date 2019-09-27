package org.alfonz.samples.alfonzrest;

import org.alfonz.rest.rx.RestRxManager;
import org.alfonz.rx.AlfonzDisposableSingleObserver;
import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RestHttpLogger;
import org.alfonz.samples.alfonzrest.rest.RestResponseHandler;
import org.alfonz.samples.alfonzrest.rest.router.RepoRxRouter;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import retrofit2.Response;

public class RestSampleRxViewModel extends BaseViewModel implements LifecycleObserver {
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<RepoEntity> repo = new MutableLiveData<>();

	private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (repo.getValue() == null) loadData();
	}

	@Override
	public void onCleared() {
		super.onCleared();

		// unsubscribe
		mRestRxManager.disposeAll();
	}

	public void refreshData() {
		loadData();
	}

	private void loadData() {
		if (NetworkUtility.isOnline(getApplicationContext())) {
			String callType = RepoRxRouter.REPO_CALL_TYPE;
			if (!mRestRxManager.isRunning(callType)) {
				// show progress
				state.setValue(StatefulLayout.PROGRESS);

				// subscribe
				Single<Response<RepoEntity>> rawSingle = RepoRxRouter.getService().repo("petrnohejl", "Alfonz");
				Single<Response<RepoEntity>> single = mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
				single.subscribeWith(createRepoObserver());
			}
		} else {
			// show offline
			state.setValue(StatefulLayout.OFFLINE);
		}
	}

	private DisposableSingleObserver<Response<RepoEntity>> createRepoObserver() {
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

	private void setState(MutableLiveData<RepoEntity> data) {
		if (data.getValue() != null) {
			state.setValue(StatefulLayout.CONTENT);
		} else {
			state.setValue(StatefulLayout.EMPTY);
		}
	}
}
