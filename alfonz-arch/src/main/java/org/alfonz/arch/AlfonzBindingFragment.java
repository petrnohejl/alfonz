package org.alfonz.arch;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public abstract class AlfonzBindingFragment<T extends AlfonzViewModel, B extends ViewDataBinding> extends AlfonzFragment<T>
{
	private B mBinding;


	public abstract B inflateBindingLayout(@NonNull LayoutInflater inflater);


	@Override
	public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
	{
		super.onCreateView(inflater, container, savedInstanceState);
		mBinding = setupBinding(inflater);
		return mBinding.getRoot();
	}


	public B getBinding()
	{
		return mBinding;
	}


	private B setupBinding(@NonNull LayoutInflater inflater)
	{
		B binding = inflateBindingLayout(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		return binding;
	}
}
