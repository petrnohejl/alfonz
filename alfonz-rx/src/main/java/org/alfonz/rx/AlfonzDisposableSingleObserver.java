package org.alfonz.rx;

import android.support.annotation.Nullable;

import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.observers.DisposableSingleObserver;

public class AlfonzDisposableSingleObserver<T> extends DisposableSingleObserver<T> {
	@Nullable private Consumer<T> mOnSuccessAction;
	@Nullable private Consumer<Throwable> mOnErrorAction;

	private AlfonzDisposableSingleObserver(@Nullable Consumer<T> onSuccessAction, @Nullable Consumer<Throwable> onErrorAction) {
		mOnSuccessAction = onSuccessAction;
		mOnErrorAction = onErrorAction;
	}

	public static <T> AlfonzDisposableSingleObserver<T> newInstance() {
		return newInstance(null, null);
	}

	public static <T> AlfonzDisposableSingleObserver<T> newInstance(@Nullable Consumer<T> onSuccessAction) {
		return newInstance(onSuccessAction, null);
	}

	public static <T> AlfonzDisposableSingleObserver<T> newInstance(@Nullable Consumer<T> onSuccessAction, @Nullable Consumer<Throwable> onErrorAction) {
		return new AlfonzDisposableSingleObserver<>(onSuccessAction, onErrorAction);
	}

	@Override
	public void onSuccess(@NonNull T value) {
		if (mOnSuccessAction == null) return;
		try {
			mOnSuccessAction.accept(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onError(@NonNull Throwable t) {
		if (mOnErrorAction == null) return;
		try {
			mOnErrorAction.accept(t);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
