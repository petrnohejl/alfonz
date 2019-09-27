package org.alfonz.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

public abstract class BaseDataBoundPagerAdapter<T extends ViewDataBinding> extends PagerAdapter {
	private LayoutInflater mLayoutInflater;

	protected abstract void bindItem(T binding, int position);

	@LayoutRes
	public abstract int getItemLayoutId(int position);

	@NonNull
	@Override
	public Object instantiateItem(@NonNull ViewGroup container, int position) {
		if (mLayoutInflater == null) {
			mLayoutInflater = LayoutInflater.from(container.getContext());
		}

		T binding = DataBindingUtil.inflate(mLayoutInflater, getItemLayoutId(position), container, false);
		bindItem(binding, position);
		binding.executePendingBindings();
		container.addView(binding.getRoot());
		return binding.getRoot();
	}

	@Override
	public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
		return view == object;
	}
}
