package org.alfonz.mvvm;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AlfonzBindingFragment<T extends AlfonzView, R extends AlfonzViewModel<T>, B extends ViewDataBinding> extends AlfonzFragment<T, R> implements AlfonzView
{
	private B mBinding;


	public abstract B inflateBindingLayout(LayoutInflater inflater);


	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		mBinding = setupBinding(inflater);
		return mBinding.getRoot();
	}


	public B getBinding()
	{
		return mBinding;
	}


	private B setupBinding(LayoutInflater inflater)
	{
		B binding = inflateBindingLayout(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		return binding;
	}
}
