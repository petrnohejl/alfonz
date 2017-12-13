package org.alfonz.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class BaseDataBoundPagerAdapter<T extends ViewDataBinding> extends PagerAdapter
{
	private LayoutInflater mLayoutInflater;


	protected abstract void bindItem(T binding, int position);


	@LayoutRes
	public abstract int getItemLayoutId(int position);


	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position)
	{
		if(mLayoutInflater == null)
		{
			mLayoutInflater = LayoutInflater.from(container.getContext());
		}

		T binding = DataBindingUtil.inflate(mLayoutInflater, getItemLayoutId(position), container, false);
		bindItem(binding, position);
		binding.executePendingBindings();
		container.addView(binding.getRoot());
		return binding.getRoot();
	}


	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object)
	{
		container.removeView((View) object);
	}


	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object)
	{
		return view == object;
	}
}
