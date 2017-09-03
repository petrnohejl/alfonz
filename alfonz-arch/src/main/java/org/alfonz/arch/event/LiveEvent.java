package org.alfonz.arch.event;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.MainThread;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.concurrent.atomic.AtomicBoolean;


// source: https://github.com/googlesamples/android-architecture-components/issues/63
public class LiveEvent<T> extends MutableLiveData<T>
{
	private final AtomicBoolean mPending = new AtomicBoolean(false);


	@MainThread
	public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull final Observer<T> observer)
	{
		// observe the internal MutableLiveData
		super.observe(lifecycleOwner, new Observer<T>()
		{
			@Override
			public void onChanged(@Nullable T value)
			{
				if(mPending.compareAndSet(true, false))
				{
					observer.onChanged(value);
				}
			}
		});
	}


	@MainThread
	public void setValue(@Nullable T value)
	{
		mPending.set(true);
		super.setValue(value);
	}


	@MainThread
	public void call()
	{
		setValue(null);
	}
}
