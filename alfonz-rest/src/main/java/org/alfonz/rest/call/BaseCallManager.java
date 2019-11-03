package org.alfonz.rest.call;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;

class BaseCallManager {
	private static final String TAG = "ALFONZ";

	private Map<Integer, Call> mCalls = new ArrayMap<>();
	private Map<Integer, String> mTypes = new ArrayMap<>();

	public <T> void enqueueCall(@NonNull Call<T> call, @NonNull Callback<T> callback, @NonNull String callType) {
		mCalls.put(callback.hashCode(), call);
		mTypes.put(callback.hashCode(), callType);
		call.enqueue(callback);
	}

	public void finishCall(@NonNull Callback callback) {
		mCalls.remove(callback.hashCode());
		mTypes.remove(callback.hashCode());
	}

	public <T> Call getCall(@NonNull Callback<T> callback) {
		return mCalls.get(callback.hashCode());
	}

	public String getCallType(@NonNull Callback callback) {
		return mTypes.get(callback.hashCode());
	}

	public int getCallsCount() {
		return mCalls.size();
	}

	public boolean hasRunningCall(@NonNull String callType) {
		return mTypes.containsValue(callType);
	}

	public void cancelRunningCalls() {
		for (Call call : mCalls.values()) {
			if (call != null) {
				call.cancel();
			}
		}
		mCalls.clear();
		mTypes.clear();
	}

	public void printRunningCalls() {
		String codeLocation = "[" + BaseCallManager.class.getSimpleName() + ".printRunningCalls] ";

		if (mCalls.isEmpty()) {
			Log.d(TAG, codeLocation + "empty");
			return;
		}

		for (String callType : mTypes.values()) {
			Log.d(TAG, codeLocation + callType);
		}
	}
}
