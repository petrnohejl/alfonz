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
	protected abstract void bindItem(T binding, int position);


	@LayoutRes
	public abstract int getItemLayoutId(int position);


	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position)
	{
		T binding = DataBindingUtil.inflate(LayoutInflater.from(container.getContext()), getItemLayoutId(position), container, false);
		bindItem(binding, position);
		binding.executePendingBindings();
		container.addView(binding.getRoot());
		return binding.getRoot();
	}


	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, Object object)
	{
		container.removeView((View) object);
	}


	@Override
	public boolean isViewFromObject(View view, Object object)
	{
		return view == object;
	}
}
