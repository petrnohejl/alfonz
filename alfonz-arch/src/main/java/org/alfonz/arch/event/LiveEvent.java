package org.alfonz.arch.event;

import java.util.concurrent.atomic.AtomicBoolean;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

// source: https://github.com/googlesamples/android-architecture-components/issues/63
public class LiveEvent<T> extends MutableLiveData<T> {
	private final AtomicBoolean mPending = new AtomicBoolean(false);

	@MainThread
	public void observe(@NonNull LifecycleOwner lifecycleOwner, @NonNull final Observer<? super T> observer) {
		// observe the internal MutableLiveData
		super.observe(lifecycleOwner, new Observer<T>() {
			@Override
			public void onChanged(@Nullable T value) {
				if (mPending.compareAndSet(true, false)) {
					observer.onChanged(value);
				}
			}
		});
	}

	@MainThread
	public void setValue(@Nullable T value) {
		mPending.set(true);
		super.setValue(value);
	}

	@MainThread
	public void call() {
		setValue(null);
	}
}
