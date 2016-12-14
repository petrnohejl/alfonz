package org.alfonz.adapter;

import android.databinding.ObservableArrayMap;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;

import org.alfonz.mvvm.AlfonzView;

import java.util.List;


public abstract class SimpleMapDataBoundRecyclerAdapter<T extends ViewDataBinding> extends BaseDataBoundRecyclerAdapter<T>
{
	@LayoutRes private int mLayoutId;
	private AlfonzView mView;
	private ObservableArrayMap<?, ?> mItems;


	public SimpleMapDataBoundRecyclerAdapter(@LayoutRes int layoutId, AlfonzView view, ObservableArrayMap<?, ?> items)
	{
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
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
