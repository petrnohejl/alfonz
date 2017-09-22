package org.alfonz.adapter;

import android.databinding.ObservableList;
import android.databinding.ViewDataBinding;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;

import org.alfonz.adapter.callback.OnPagerListChangedCallback;


public abstract class MultiDataBoundPagerAdapter extends BaseDataBoundPagerAdapter
{
	private AdapterView mView;
	private ObservableList<?>[] mItems;


	@SuppressWarnings("unchecked")
	public MultiDataBoundPagerAdapter(AdapterView view, ObservableList<?>... items)
	{
		mView = view;
		mItems = items;

		OnPagerListChangedCallback callback = new OnPagerListChangedCallback(this);
		for(ObservableList<?> list : mItems)
		{
			list.addOnListChangedCallback(callback);
		}
	}


	@Override
	protected void bindItem(@NonNull ViewDataBinding binding, int position)
	{
		Object item = getItem(position);
		binding.setVariable(BR.view, mView);
		binding.setVariable(BR.data, item);
		binding.getRoot().setTag(item);
	}


	@Override
	public int getCount()
	{
		int size = 0;
		for(ObservableList<?> list : mItems)
		{
			size += list.size();
		}
		return size;
	}


	@Override
	public int getItemPosition(Object object)
	{
		Object item = ((View) object).getTag();
		if(mItems != null)
		{
			int counter = 0;
			for(ObservableList<?> list : mItems)
			{
				for(int i = 0; i < list.size(); i++)
				{
					if(item == list.get(i))
					{
						return counter;
					}
					counter++;
				}
			}
		}
		return POSITION_NONE;
	}


	@Nullable
	public Object getItem(int position)
	{
		int counter = 0;
		for(ObservableList<?> list : mItems)
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
