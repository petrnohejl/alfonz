package org.alfonz.rx;

import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public final class SchedulersUtility
{
	private static final ObservableTransformer<?, ?> sSchedulersTransformer =
			observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


	private SchedulersUtility() {}


	@SuppressWarnings("unchecked")
	public static <T> ObservableTransformer<T, T> applySchedulers()
	{
		return (ObservableTransformer<T, T>) sSchedulersTransformer;
	}
}
