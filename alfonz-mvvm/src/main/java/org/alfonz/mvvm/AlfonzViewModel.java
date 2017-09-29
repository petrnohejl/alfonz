package org.alfonz.mvvm;

import android.databinding.Observable;
import android.databinding.PropertyChangeRegistry;
import android.support.annotation.NonNull;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import eu.inloop.viewmodel.AbstractViewModel;


@Deprecated
public abstract class AlfonzViewModel<T extends AlfonzView> extends AbstractViewModel<T> implements Observable
{
	private transient PropertyChangeRegistry mObservableCallbacks;
	private Queue<ViewAction<T>> mViewActionQueue;


	public interface ViewAction<T>
	{
		void run(@NonNull T view);
	}


	@Override
	public void onBindView(@NonNull T view)
	{
		super.onBindView(view);
		processPendingViewActions(view);
	}


	@Override
	public synchronized void addOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback)
	{
		if(mObservableCallbacks == null)
		{
			mObservableCallbacks = new PropertyChangeRegistry();
		}
		mObservableCallbacks.add(callback);
	}


	@Override
	public synchronized void removeOnPropertyChangedCallback(@NonNull OnPropertyChangedCallback callback)
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


	public void runViewAction(@NonNull ViewAction<T> viewAction)
	{
		if(getView() != null)
		{
			viewAction.run(getView());
		}
		else
		{
			addPendingViewAction(viewAction);
		}
	}


	private synchronized void addPendingViewAction(@NonNull ViewAction<T> viewAction)
	{
		if(mViewActionQueue == null)
		{
			mViewActionQueue = new ConcurrentLinkedQueue<>();
		}
		mViewActionQueue.add(viewAction);
	}


	private void processPendingViewActions(@NonNull T view)
	{
		while(mViewActionQueue != null && !mViewActionQueue.isEmpty())
		{
			ViewAction<T> viewAction = mViewActionQueue.remove();
			viewAction.run(view);
		}
	}
}
