package org.alfonz.rx;

import androidx.annotation.NonNull;

import com.jakewharton.rxrelay3.PublishRelay;
import com.jakewharton.rxrelay3.Relay;

import io.reactivex.rxjava3.core.Observable;

public class RxBus {
	private static volatile RxBus sRxBus;

	private final Relay<Object> mBus = PublishRelay.create().toSerialized();

	private RxBus() {}

	public static RxBus getInstance() {
		if (sRxBus == null) {
			synchronized (RxBus.class) {
				if (sRxBus == null) {
					sRxBus = new RxBus();
				}
			}
		}
		return sRxBus;
	}

	public <T> Observable<T> onEvent(@NonNull Class<T> eventClass) {
		return mBus.ofType(eventClass);
	}

	public void send(@NonNull final Object event) {
		mBus.accept(event);
	}

	@NonNull
	public Observable<Object> toObservable() {
		return mBus;
	}

	public boolean hasObservers() {
		return mBus.hasObservers();
	}
}
