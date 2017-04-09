package org.alfonz.rx;

import android.support.annotation.Nullable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableMaybeObserver;


public class AlfonzDisposableMaybeObserver<T> extends DisposableMaybeObserver<T>
{
	@Nullable private Consumer<T> mOnSuccessAction;
	@Nullable private Consumer<Throwable> mOnErrorAction;
	@Nullable private Action mOnCompleteAction;


	private AlfonzDisposableMaybeObserver(@Nullable Consumer<T> onSuccessAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		mOnSuccessAction = onSuccessAction;
		mOnErrorAction = onErrorAction;
		mOnCompleteAction = onCompleteAction;
	}


	public static <T> AlfonzDisposableMaybeObserver<T> newInstance()
	{
		return newInstance(null, null, null);
	}


	public static <T> AlfonzDisposableMaybeObserver<T> newInstance(@Nullable Consumer<T> onSuccessAction)
	{
		return newInstance(onSuccessAction, null, null);
	}


	public static <T> AlfonzDisposableMaybeObserver<T> newInstance(@Nullable Consumer<T> onSuccessAction, @Nullable Consumer<Throwable> onErrorAction)
	{
		return newInstance(onSuccessAction, onErrorAction, null);
	}


	public static <T> AlfonzDisposableMaybeObserver<T> newInstance(@Nullable Consumer<T> onSuccessAction, @Nullable Consumer<Throwable> onErrorAction, @Nullable Action onCompleteAction)
	{
		return new AlfonzDisposableMaybeObserver<>(onSuccessAction, onErrorAction, onCompleteAction);
	}


	@Override
	public void onSuccess(@NonNull T value)
	{
		if(mOnSuccessAction == null) return;
		try
		{
			mOnSuccessAction.accept(value);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}


	@Override
	public void onError(@NonNull Throwable t)
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
