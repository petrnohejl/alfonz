package org.alfonz.adapter.callback;

import android.databinding.ObservableList;

import org.alfonz.adapter.BaseDataBoundRecyclerAdapter;


public class RecyclerListChangedCallbackHolder
{
	private OnRecyclerListChangedCallback mCallback;


	@SuppressWarnings("unchecked")
	public void register(BaseDataBoundRecyclerAdapter adapter, ObservableList<?> items)
	{
		if(mCallback == null)
		{
			mCallback = new OnRecyclerListChangedCallback(adapter);
			items.addOnListChangedCallback(mCallback);
		}
	}


	@SuppressWarnings("unchecked")
	public void register(BaseDataBoundRecyclerAdapter adapter, ObservableList<?>[] items)
	{
		if(mCallback == null)
		{
			mCallback = new OnRecyclerListChangedCallback(adapter);
			for(ObservableList<?> list : items)
			{
				list.addOnListChangedCallback(mCallback);
			}
		}
	}


	@SuppressWarnings("unchecked")
	public void unregister(ObservableList<?> items)
	{
		if(mCallback != null)
		{
			items.removeOnListChangedCallback(mCallback);
			mCallback = null;
		}
	}


	@SuppressWarnings("unchecked")
	public void unregister(ObservableList<?>[] items)
	{
		if(mCallback != null)
		{
			for(ObservableList<?> list : items)
			{
				list.removeOnListChangedCallback(mCallback);
			}
			mCallback = null;
		}
	}
}
