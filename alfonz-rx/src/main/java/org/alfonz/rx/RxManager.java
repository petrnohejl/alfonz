package org.alfonz.rx;

import android.util.Log;

import org.alfonz.rx.utility.SchedulersUtility;

import java.util.HashMap;
import java.util.Map;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class RxManager
{
	private static final String TAG = "ALFONZ";

	private CompositeDisposable mCompositeDisposable = new CompositeDisposable();
	private Map<String, Short> mRunningCalls = new HashMap<>();


	public void registerDisposable(Disposable disposable)
	{
		mCompositeDisposable.add(disposable);
	}


	public void unregisterDisposable(Disposable disposable)
	{
		mCompositeDisposable.remove(disposable);
	}


	public void disposeAll()
	{
		mCompositeDisposable.clear();
		mRunningCalls.clear();
	}


	public boolean isRunning(String callType)
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


	public <T> Observable<T> setupObservable(Observable<T> observable, String callType)
	{
		return observable
				.doOnSubscribe(disposable -> addRunningCall(callType))
				.doFinally(() -> removeRunningCall(callType));
	}


	public <T> Observable<T> setupObservableWithSchedulers(Observable<T> observable, String callType)
	{
		return setupObservable(observable, callType).compose(SchedulersUtility.applyObservableSchedulers());
	}


	private synchronized void addRunningCall(String callType)
	{
		short count = 0;
		if(mRunningCalls.containsKey(callType))
		{
			count = mRunningCalls.get(callType);
		}
		mRunningCalls.put(callType, ++count);
	}


	private synchronized void removeRunningCall(String callType)
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
