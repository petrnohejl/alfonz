package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;

import java.util.List;


public class SimpleDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableArrayList<?> mItems;


	public SimpleDataBoundRecyclerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableArrayList<?> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, mItems.get(position));
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
