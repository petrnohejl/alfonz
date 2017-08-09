package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;

import java.lang.ref.WeakReference;


public abstract class MultiDataBoundPagerAdapter extends BaseDataBoundPagerAdapter
{
	private AdapterView mView;
	private ObservableArrayList<?>[] mItems;
	private OnListChangedCallback mOnListChangedCallback;


	public MultiDataBoundPagerAdapter(AdapterView view, ObservableArrayList<?>... items)
	{
		mView = view;
		mItems = items;

		mOnListChangedCallback = new OnListChangedCallback(this);
		for(ObservableArrayList<?> list : mItems)
		{
			list.addOnListChangedCallback(mOnListChangedCallback);
		}
	}


	@Override
	protected void bindItem(ViewDataBinding binding, int position)
	{
		binding.setVariable(BR.view, mView);
		binding.setVariable(BR.data, getItem(position));
	}


	@Override
	public int getCount()
	{
		int size = 0;
		for(ObservableArrayList<?> list : mItems)
		{
			size += list.size();
		}
		return size;
	}


	public Object getItem(int position)
	{
		int counter = 0;
		for(ObservableArrayList<?> list : mItems)
		{
			if(position - counter - list.size() < 0)
			{
				int index = position - counter;
				return list.get(index);
			}
			counter += list.size();
		}
		return null;
	}


	private static class OnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<?>>
	{
		private final WeakReference<MultiDataBoundPagerAdapter> mAdapter;


		public OnListChangedCallback(MultiDataBoundPagerAdapter adapter)
		{
			mAdapter = new WeakReference<>(adapter);
		}


		@Override
		public void onChanged(ObservableList<?> sender)
		{
			onUpdate();
		}


		@Override
		public void onItemRangeChanged(ObservableList<?> sender, int positionStart, int itemCount)
		{
			onUpdate();
		}


		@Override
		public void onItemRangeInserted(ObservableList<?> sender, int positionStart, int itemCount)
		{
			onUpdate();
		}


		@Override
		public void onItemRangeMoved(ObservableList<?> sender, int fromPosition, int toPosition, int itemCount)
		{
			onUpdate();
		}


		@Override
		public void onItemRangeRemoved(ObservableList<?> sender, int positionStart, int itemCount)
		{
			onUpdate();
		}


		private void onUpdate()
		{
			if(mAdapter.get() != null)
			{
				mAdapter.get().notifyDataSetChanged();
			}
		}
	}
}
