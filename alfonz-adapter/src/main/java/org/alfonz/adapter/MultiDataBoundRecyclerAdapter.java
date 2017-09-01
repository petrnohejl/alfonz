package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;


public abstract class MultiDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	private AdapterView mView;
	private ObservableArrayList<?>[] mItems;


	public MultiDataBoundRecyclerAdapter(AdapterView view, ObservableArrayList<?>... items)
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
		for(ObservableArrayList<?> list : mItems)
		{
			size += list.size();
		}
		return size;
	}


	@Nullable
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
}
