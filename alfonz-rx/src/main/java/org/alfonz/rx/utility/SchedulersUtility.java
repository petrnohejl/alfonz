package org.alfonz.rx.utility;

import io.reactivex.CompletableTransformer;
import io.reactivex.FlowableTransformer;
import io.reactivex.MaybeTransformer;
import io.reactivex.ObservableTransformer;
import io.reactivex.SingleTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;


public final class SchedulersUtility
{
	private static ObservableTransformer<?, ?> sSchedulersObservableTransformer;
	private static SingleTransformer<?, ?> sSchedulersSingleTransformer;
	private static CompletableTransformer sSchedulersCompletableTransformer;
	private static MaybeTransformer<?, ?> sSchedulersMaybeTransformer;
	private static FlowableTransformer<?, ?> sSchedulersFlowableTransformer;


	private SchedulersUtility() {}


	@SuppressWarnings("unchecked")
	public static <T> ObservableTransformer<T, T> applyObservableSchedulers()
	{
		if(sSchedulersObservableTransformer == null)
		{
			sSchedulersObservableTransformer = observable -> observable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return (ObservableTransformer<T, T>) sSchedulersObservableTransformer;
	}


	@SuppressWarnings("unchecked")
	public static <T> SingleTransformer<T, T> applySingleSchedulers()
	{
		if(sSchedulersSingleTransformer == null)
		{
			sSchedulersSingleTransformer = single -> single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return (SingleTransformer<T, T>) sSchedulersSingleTransformer;
	}


	public static <T> CompletableTransformer applyCompletableSchedulers()
	{
		if(sSchedulersCompletableTransformer == null)
		{
			sSchedulersCompletableTransformer = completable -> completable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return sSchedulersCompletableTransformer;
	}


	@SuppressWarnings("unchecked")
	public static <T> MaybeTransformer<T, T> applyMaybeSchedulers()
	{
		if(sSchedulersMaybeTransformer == null)
		{
			sSchedulersMaybeTransformer = maybe -> maybe.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return (MaybeTransformer<T, T>) sSchedulersMaybeTransformer;
	}


	@SuppressWarnings("unchecked")
	public static <T> FlowableTransformer<T, T> applyFlowableSchedulers()
	{
		if(sSchedulersFlowableTransformer == null)
		{
			sSchedulersFlowableTransformer = flowable -> flowable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
		}
		return (FlowableTransformer<T, T>) sSchedulersFlowableTransformer;
	}
}
