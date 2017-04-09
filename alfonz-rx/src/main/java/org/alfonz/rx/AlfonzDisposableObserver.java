package org.alfonz.rx;

import android.support.annotation.Nullable;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableObserver;


public class AlfonzDisposableObserver<T> extends DisposableObserver<T>
{
	@Nullable private Consumer<T> mOnNextAction;
	@Nullable private Consumer<Throwable> mOnErrorAction;
	@Nullable private Action mOnCompleteAction;


	private AlfonzDisposableObserver(@Nullable Consumer<T> onNextAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		mOnNextAction = onNextAction;
		mOnErrorAction = onErrorAction;
		mOnCompleteAction = onCompleteAction;
	}


	public static <T> AlfonzDisposableObserver<T> newInstance()
	{
		return newInstance(null, null, null);
	}


	public static <T> AlfonzDisposableObserver<T> newInstance(@Nullable Consumer<T> onNextAction)
	{
		return newInstance(onNextAction, null, null);
	}


	public static <T> AlfonzDisposableObserver<T> newInstance(@Nullable Consumer<T> onNextAction, @Nullable Consumer<Throwable> onErrorAction)
	{
		return newInstance(onNextAction, onErrorAction, null);
	}


	public static <T> AlfonzDisposableObserver<T> newInstance(@Nullable Consumer<T> onNextAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		return new AlfonzDisposableObserver<>(onNextAction, onErrorAction, onCompleteAction);
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
