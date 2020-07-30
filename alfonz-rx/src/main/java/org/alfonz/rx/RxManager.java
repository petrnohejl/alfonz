package org.alfonz.rx;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.collection.ArrayMap;

import org.alfonz.rx.utility.SchedulersUtility;

import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Maybe;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.disposables.Disposable;

public class RxManager {
	private static final String TAG = "ALFONZ";

	private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
	private Map<String, Short> mPendingCalls = new ArrayMap<>();
	private Map<String, Short> mRunningCalls = new ArrayMap<>();

	public <T> Observable<T> setupObservable(@NonNull Observable<T> observable, @NonNull String callType) {
		addPendingCall(callType);
		return observable
				.doOnSubscribe(disposable ->
				{
					removePendingCall(callType);
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}

	public <T> Observable<T> setupObservableWithSchedulers(@NonNull Observable<T> observable, @NonNull String callType) {
		return setupObservable(observable, callType).compose(SchedulersUtility.applyObservableSchedulers());
	}

	public <T> Single<T> setupSingle(@NonNull Single<T> single, @NonNull String callType) {
		addPendingCall(callType);
		return single
				.doOnSubscribe(disposable ->
				{
					removePendingCall(callType);
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}

	public <T> Single<T> setupSingleWithSchedulers(@NonNull Single<T> single, @NonNull String callType) {
		return setupSingle(single, callType).compose(SchedulersUtility.applySingleSchedulers());
	}

	public Completable setupCompletable(@NonNull Completable completable, @NonNull String callType) {
		addPendingCall(callType);
		return completable
				.doOnSubscribe(disposable ->
				{
					removePendingCall(callType);
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}

	public Completable setupCompletableWithSchedulers(@NonNull Completable completable, @NonNull String callType) {
		return setupCompletable(completable, callType).compose(SchedulersUtility.applyCompletableSchedulers());
	}

	public <T> Maybe<T> setupMaybe(@NonNull Maybe<T> maybe, @NonNull String callType) {
		addPendingCall(callType);
		return maybe
				.doOnSubscribe(disposable ->
				{
					removePendingCall(callType);
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}

	public <T> Maybe<T> setupMaybeWithSchedulers(@NonNull Maybe<T> maybe, @NonNull String callType) {
		return setupMaybe(maybe, callType).compose(SchedulersUtility.applyMaybeSchedulers());
	}

	public void disposeAll() {
		mCompositeDisposable.clear();
		mPendingCalls.clear();
		mRunningCalls.clear();
	}

	public boolean isPending(@NonNull String callType) {
		return mPendingCalls.containsKey(callType);
	}

	public boolean isRunning(@NonNull String callType) {
		return mRunningCalls.containsKey(callType);
	}

	public synchronized void printAll() {
		String codeLocation = "[" + RxManager.class.getSimpleName() + ".printAll] ";

		if (mPendingCalls.isEmpty()) {
			Log.d(TAG, codeLocation + "no pending calls");
		} else {
			for (Map.Entry<String, Short> entry : mPendingCalls.entrySet()) {
				Log.d(TAG, codeLocation + entry.getKey() + ": " + entry.getValue() + " pending");
			}
		}

		if (mRunningCalls.isEmpty()) {
			Log.d(TAG, codeLocation + "no running calls");
		} else {
			for (Map.Entry<String, Short> entry : mRunningCalls.entrySet()) {
				Log.d(TAG, codeLocation + entry.getKey() + ": " + entry.getValue() + " running");
			}
		}
	}

	private void registerDisposable(@NonNull Disposable disposable) {
		mCompositeDisposable.add(disposable);
	}

	private synchronized void addPendingCall(@NonNull String callType) {
		short count = 0;
		if (mPendingCalls.containsKey(callType)) {
			count = mPendingCalls.get(callType);
		}
		mPendingCalls.put(callType, ++count);
	}

	private synchronized void removePendingCall(@NonNull String callType) {
		Short count = mPendingCalls.get(callType);
		if (count == null) return;

		if (count > 1) {
			mPendingCalls.put(callType, --count);
		} else {
			mPendingCalls.remove(callType);
		}
	}

	private synchronized void addRunningCall(@NonNull String callType) {
		short count = 0;
		if (mRunningCalls.containsKey(callType)) {
			count = mRunningCalls.get(callType);
		}
		mRunningCalls.put(callType, ++count);
	}

	private synchronized void removeRunningCall(@NonNull String callType) {
		Short count = mRunningCalls.get(callType);
		if (count == null) return;

		if (count > 1) {
			mRunningCalls.put(callType, --count);
		} else {
			mRunningCalls.remove(callType);
		}
	}
}
