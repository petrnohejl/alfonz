package org.alfonz.mvvm;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import eu.inloop.viewmodel.AbstractViewModel;


public abstract class AlfonzViewModel<T extends AlfonzView> extends AbstractViewModel<T> implements Observable
{
	private transient PropertyChangeRegistry mObservableCallbacks;
	private Queue<ViewCallback<T>> mViewCallbackQueue;


	public interface ViewCallback<T>
	{
		void run(@NonNull T view);
	}


	@Override
	public void onBindView(@NonNull T view)
	{
		super.onBindView(view);
		processPendingViewCallbacks(view);
	}


	@Override
	public synchronized void addOnPropertyChangedCallback(OnPropertyChangedCallback callback)
	{
		if(mObservableCallbacks == null)
		{
			mObservableCallbacks = new PropertyChangeRegistry();
		}
		mObservableCallbacks.add(callback);
	}


	@Override
	public synchronized void removeOnPropertyChangedCallback(OnPropertyChangedCallback callback)
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.remove(callback);
		}
	}


	public synchronized void notifyChange()
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.notifyCallbacks(this, 0, null);
		}
	}


	public void notifyPropertyChanged(int fieldId)
	{
		if(mObservableCallbacks != null)
		{
			mObservableCallbacks.notifyCallbacks(this, fieldId, null);
		}
	}


	public void runViewCallback(ViewCallback<T> viewCallback)
	{
		if(getView() != null)
		{
			viewCallback.run(getView());
		}
		else
		{
			addPendingViewCallback(viewCallback);
		}
	}


	private synchronized void addPendingViewCallback(ViewCallback<T> viewCallback)
	{
		if(mViewCallbackQueue == null)
		{
			mViewCallbackQueue = new ConcurrentLinkedQueue<>();
		}
		mViewCallbackQueue.add(viewCallback);
	}


	private void processPendingViewCallbacks(@NonNull T view)
	{
		while(mViewCallbackQueue != null && !mViewCallbackQueue.isEmpty())
		{
			ViewCallback<T> viewCallback = mViewCallbackQueue.remove();
			viewCallback.run(view);
		}
	}
}
