package org.alfonz.rx;

import androidx.annotation.Nullable;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.functions.Action;
import io.reactivex.rxjava3.functions.Consumer;
import io.reactivex.rxjava3.observers.DisposableCompletableObserver;

public class AlfonzDisposableCompletableObserver extends DisposableCompletableObserver {
	@Nullable private Action mOnCompleteAction;
	@Nullable private Consumer<Throwable> mOnErrorAction;

	private AlfonzDisposableCompletableObserver(@Nullable Action onCompleteAction, @Nullable Consumer<Throwable> onErrorAction) {
		mOnCompleteAction = onCompleteAction;
		mOnErrorAction = onErrorAction;
	}

	public static AlfonzDisposableCompletableObserver newInstance() {
		return newInstance(null, null);
	}

	public static AlfonzDisposableCompletableObserver newInstance(@Nullable Action onCompleteAction) {
		return newInstance(onCompleteAction, null);
	}

	public static AlfonzDisposableCompletableObserver newInstance(@Nullable Action onCompleteAction, @Nullable Consumer<Throwable> onErrorAction) {
		return new AlfonzDisposableCompletableObserver(onCompleteAction, onErrorAction);
	}

	@Override
	public void onComplete() {
		if (mOnCompleteAction == null) return;
		try {
			mOnCompleteAction.run();
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}

	@Override
	public void onError(@NonNull Throwable t) {
		if (mOnErrorAction == null) return;
		try {
			mOnErrorAction.accept(t);
		} catch (Throwable throwable) {
			throwable.printStackTrace();
		}
	}
}
