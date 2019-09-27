package org.alfonz.arch;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.databinding.ViewDataBinding;

public abstract class AlfonzBindingActivity<T extends AlfonzViewModel, B extends ViewDataBinding> extends AlfonzActivity implements AlfonzView {
	private T mViewModel;
	private B mBinding;

	public abstract T setupViewModel();
	public abstract B inflateBindingLayout(@NonNull LayoutInflater inflater);

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = setupViewModel();
		mBinding = setupBinding(getLayoutInflater());
		setContentView(mBinding.getRoot());
	}

	public T getViewModel() {
		return mViewModel;
	}

	public B getBinding() {
		return mBinding;
	}

	private B setupBinding(@NonNull LayoutInflater inflater) {
		B binding = inflateBindingLayout(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		binding.setLifecycleOwner(this);
		return binding;
	}
}
