package org.alfonz.rx;

import android.support.annotation.NonNull;
import android.util.Log;

import org.alfonz.rx.utility.SchedulersUtility;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Completable;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class RxManager
{
	private static final String TAG = "ALFONZ";

	private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
	private Map<String, Short> mRunningCalls = new HashMap<>();


	public <T> Observable<T> setupObservable(@NonNull Observable<T> observable, @NonNull String callType)
	{
		return observable
				.doOnSubscribe(disposable ->
				{
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}


	public <T> Observable<T> setupObservableWithSchedulers(@NonNull Observable<T> observable, @NonNull String callType)
	{
		return setupObservable(observable, callType).compose(SchedulersUtility.applyObservableSchedulers());
	}


	public <T> Single<T> setupSingle(@NonNull Single<T> single, @NonNull String callType)
	{
		return single
				.doOnSubscribe(disposable ->
				{
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}


	public <T> Single<T> setupSingleWithSchedulers(@NonNull Single<T> single, @NonNull String callType)
	{
		return setupSingle(single, callType).compose(SchedulersUtility.applySingleSchedulers());
	}


	public Completable setupCompletable(@NonNull Completable completable, @NonNull String callType)
	{
		return completable
				.doOnSubscribe(disposable ->
				{
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}


	public Completable setupCompletableWithSchedulers(@NonNull Completable completable, @NonNull String callType)
	{
		return setupCompletable(completable, callType).compose(SchedulersUtility.applyCompletableSchedulers());
	}


	public <T> Maybe<T> setupMaybe(@NonNull Maybe<T> maybe, @NonNull String callType)
	{
		return maybe
				.doOnSubscribe(disposable ->
				{
					addRunningCall(callType);
					registerDisposable(disposable);
				})
				.doFinally(() -> removeRunningCall(callType));
	}


	public <T> Maybe<T> setupMaybeWithSchedulers(@NonNull Maybe<T> maybe, @NonNull String callType)
	{
		return setupMaybe(maybe, callType).compose(SchedulersUtility.applyMaybeSchedulers());
	}


	public void disposeAll()
	{
		mCompositeDisposable.clear();
		mRunningCalls.clear();
	}


	public boolean isRunning(@NonNull String callType)
	{
		return mRunningCalls.containsKey(callType);
	}


	public void printAll()
	{
		String codeLocation = "[" + RxManager.class.getSimpleName() + ".printAll] ";

		if(mRunningCalls.isEmpty())
		{
			Log.d(TAG, codeLocation + "empty");
			return;
		}

		for(Map.Entry<String, Short> entry : mRunningCalls.entrySet())
		{
			Log.d(TAG, codeLocation + entry.getKey() + ": " + entry.getValue());
		}
	}


	private void registerDisposable(@NonNull Disposable disposable)
	{
		mCompositeDisposable.add(disposable);
	}


	private synchronized void addRunningCall(@NonNull String callType)
	{
		short count = 0;
		if(mRunningCalls.containsKey(callType))
		{
			count = mRunningCalls.get(callType);
		}
		mRunningCalls.put(callType, ++count);
	}


	private synchronized void removeRunningCall(@NonNull String callType)
	{
		Short count = mRunningCalls.get(callType);
		if(count == null) return;

		if(count > 1)
		{
			mRunningCalls.put(callType, --count);
		}
		else
		{
			mRunningCalls.remove(callType);
		}
	}
}
