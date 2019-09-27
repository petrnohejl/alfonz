package org.alfonz.samples.alfonzrest;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.call.CallManager;
import org.alfonz.rest.call.Callback;
import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.samples.alfonzrest.entity.RepoEntity;
import org.alfonz.samples.alfonzrest.rest.RestHttpLogger;
import org.alfonz.samples.alfonzrest.rest.RestResponseHandler;
import org.alfonz.samples.alfonzrest.rest.router.RepoRouter;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;
import retrofit2.Call;
import retrofit2.Response;

public class RestSampleViewModel extends BaseViewModel implements LifecycleObserver {
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<RepoEntity> repo = new MutableLiveData<>();

	private CallManager mCallManager = new CallManager(new RestResponseHandler(), new RestHttpLogger());

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (repo.getValue() == null) loadData();
	}

	@Override
	public void onCleared() {
		super.onCleared();

		// cancel async tasks
		if (mCallManager != null) mCallManager.cancelRunningCalls();
	}

	public void refreshData() {
		loadData();
	}

	private void loadData() {
		if (NetworkUtility.isOnline(getApplicationContext())) {
			String callType = RepoRouter.REPO_CALL_TYPE;
			if (!mCallManager.hasRunningCall(callType)) {
				// show progress
				state.setValue(StatefulLayout.PROGRESS);

				// enqueue call
				Call<RepoEntity> call = RepoRouter.getService().repo("petrnohejl", "Alfonz");
				RepoCallback callback = new RepoCallback(mCallManager);
				mCallManager.enqueueCall(call, callback, callType);
			}
		} else {
			// show offline
			state.setValue(StatefulLayout.OFFLINE);
		}
	}

	private void setState(MutableLiveData<RepoEntity> data) {
		if (data.getValue() != null) {
			state.setValue(StatefulLayout.CONTENT);
		} else {
			state.setValue(StatefulLayout.EMPTY);
		}
	}

	private class RepoCallback extends Callback<RepoEntity> {
		public RepoCallback(CallManager callManager) {
			super(callManager);
		}

		@Override
		public void onSuccess(@NonNull Call<RepoEntity> call, @NonNull Response<RepoEntity> response) {
			repo.setValue(response.body());
			setState(repo);
		}

		@Override
		public void onError(@NonNull Call<RepoEntity> call, @NonNull HttpException exception) {
			handleError(mCallManager.getHttpErrorMessage(exception));
			setState(repo);
		}

		@Override
		public void onFail(@NonNull Call<RepoEntity> call, @NonNull Throwable throwable) {
			handleError(mCallManager.getHttpErrorMessage(throwable));
			setState(repo);
		}
	}
}
