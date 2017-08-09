package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import java.lang.ref.WeakReference;


public class SimpleDataBoundPagerAdapter extends BaseDataBoundPagerAdapter
{
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableArrayList<?> mItems;


	public SimpleDataBoundPagerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableArrayList<?> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;

		mItems.addOnListChangedCallback(new OnListChangedCallback(this));
	}


	@Override
	protected void bindItem(ViewDataBinding binding, int position)
	{
		binding.setVariable(BR.view, mView);
		binding.setVariable(BR.data, mItems.get(position));
	}


	@Override
	public int getItemLayoutId(int position)
	{
		return mLayoutId;
	}


	@Override
	public int getCount()
	{
		return mItems.size();
	}


	private static class OnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<?>>
	{
		private final WeakReference<SimpleDataBoundPagerAdapter> mAdapter;


		public OnListChangedCallback(SimpleDataBoundPagerAdapter adapter)
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
