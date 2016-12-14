package org.alfonz.rx;

import android.support.annotation.Nullable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


// @RxLogSubscriber // TODO: Frodo 2
public class LoggedObserver<T> extends DisposableObserver<T>
{
	@Nullable private Consumer<T> mOnNextAction;
	@Nullable private Consumer<Throwable> mOnErrorAction;
	@Nullable private Action mOnCompleteAction;


	private LoggedObserver(@Nullable Consumer<T> onNextAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		mOnNextAction = onNextAction;
		mOnErrorAction = onErrorAction;
		mOnCompleteAction = onCompleteAction;
	}


	public static <T> LoggedObserver<T> newInstance()
	{
		return newInstance(null, null, null);
	}


	public static <T> LoggedObserver<T> newInstance(Consumer<T> onNextAction)
	{
		return newInstance(onNextAction, null, null);
	}


	public static <T> LoggedObserver<T> newInstance(Consumer<T> onNextAction, Consumer<Throwable> onErrorAction)
	{
		return newInstance(onNextAction, onErrorAction, null);
	}


	public static <T> LoggedObserver<T> newInstance(@Nullable Consumer<T> onNextAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		return new LoggedObserver<T>(onNextAction, onErrorAction, onCompleteAction);
	}


	@Override
	public void onNext(T value)
	{
		if(mOnNextAction == null) return;
		try
		{
			mOnNextAction.accept(value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void onError(Throwable t)
	{
		if(mOnErrorAction == null) return;
		try
		{
			mOnErrorAction.accept(t);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void onComplete()
	{
		if(mOnCompleteAction == null) return;
		try
		{
			mOnCompleteAction.run();
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
