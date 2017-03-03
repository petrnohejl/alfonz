package org.alfonz.rx;

import io.reactivex.FlowableTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public final class SchedulersUtility
{
	private static final ObservableTransformer<?, ?> sSchedulersObservableTransformer =
			observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	private static final FlowableTransformer<?, ?> sSchedulersFlowableTransformer =
			flowable -> flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());


	private SchedulersUtility() {}


	@SuppressWarnings("unchecked")
	public static <T> ObservableTransformer<T, T> applyObservableSchedulers()
	{
		return (ObservableTransformer<T, T>) sSchedulersObservableTransformer;
	}


	@SuppressWarnings("unchecked")
	public static <T> FlowableTransformer<T, T> applyFlowableSchedulers()
	{
		return (FlowableTransformer<T, T>) sSchedulersFlowableTransformer;
	}
}
