package org.alfonz.adapter;

import android.databinding.ObservableArrayMap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;


public class SimpleMapDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableArrayMap<?, ?> mItems;


	public SimpleMapDataBoundRecyclerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableArrayMap<?, ?> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, mItems.valueAt(position));
	}


	@Override
	public int getItemLayoutId(int position)
	{
		return mLayoutId;
	}


	@Override
	public int getItemCount()
	{
		return mItems.size();
	}
}
