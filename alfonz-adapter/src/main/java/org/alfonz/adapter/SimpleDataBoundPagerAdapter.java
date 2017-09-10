package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.View;

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
	protected void bindItem(@NonNull ViewDataBinding binding, int position)
	{
		Object item = mItems.get(position);
		binding.setVariable(BR.view, mView);
		binding.setVariable(BR.data, item);
		binding.getRoot().setTag(item);
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


	@Override
	public int getItemPosition(Object object)
	{
		Object item = ((View) object).getTag();
		if(mItems != null)
		{
			for(int i = 0; i < mItems.size(); i++)
			{
				if(item == mItems.get(i))
				{
					return i;
				}
			}
		}
		return POSITION_NONE;
	}


	private static class OnListChangedCallback extends ObservableList.OnListChangedCallback<ObservableList<?>>
	{
		@NonNull private final WeakReference<SimpleDataBoundPagerAdapter> mAdapter;


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
