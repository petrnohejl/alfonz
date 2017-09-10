package org.alfonz.adapter;

import android.databinding.ObservableArrayList;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;

import org.alfonz.adapter.callback.RecyclerListChangedCallbackHolder;

import java.util.List;


public abstract class MultiDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter
{
	private AdapterView mView;
	private ObservableArrayList<?>[] mItems;
	private RecyclerListChangedCallbackHolder mCallbackHolder = new RecyclerListChangedCallbackHolder();


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


	@Override
	public void onAttachedToRecyclerView(RecyclerView recyclerView)
	{
		super.onAttachedToRecyclerView(recyclerView);
		mCallbackHolder.register(this, mItems);
	}


	@Override
	public void onDetachedFromRecyclerView(RecyclerView recyclerView)
	{
		super.onDetachedFromRecyclerView(recyclerView);
		mCallbackHolder.unregister(mItems);
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
