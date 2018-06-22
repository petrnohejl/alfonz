package org.alfonz.adapter;

import android.databinding.ObservableArrayMap;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import org.alfonz.adapter.callback.RecyclerMapChangedCallbackHolder;

import java.util.List;


public abstract class MultiMapDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	private AdapterView mView;
	private ObservableArrayMap<?, ?>[] mItems;
	private RecyclerMapChangedCallbackHolder mCallbackHolder = new RecyclerMapChangedCallbackHolder();


	public MultiMapDataBoundRecyclerAdapter(AdapterView view, ObservableArrayMap<?, ?>... items)
	{
		mView = view;
		mItems = items;
	}


	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads)
	{
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, getItem(position));
	}


	@Override
	public int getItemCount()
	{
		int size = 0;
		for(ObservableArrayMap<?, ?> map : mItems)
		{
			size += map.size();
		}
		return size;
	}


	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView)
	{
		super.onAttachedToRecyclerView(recyclerView);
		mCallbackHolder.register(this, mItems);
	}


	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView)
	{
		super.onDetachedFromRecyclerView(recyclerView);
		mCallbackHolder.unregister(mItems);
	}


	@Nullable
	public Object getItem(int position)
	{
		int counter = 0;
		for(ObservableArrayMap<?, ?> map : mItems)
		{
			if(position - counter - map.size() < 0)
			{
				int index = position - counter;
				return map.valueAt(index);
			}
			counter += map.size();
		}
		return null;
	}
}
