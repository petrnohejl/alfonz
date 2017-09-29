package org.alfonz.mvvm;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import eu.inloop.viewmodel.AbstractViewModel;
import eu.inloop.viewmodel.ProxyViewHelper;
import eu.inloop.viewmodel.ViewModelHelper;
import eu.inloop.viewmodel.binding.ViewModelBindingConfig;


@Deprecated
public abstract class AlfonzBindingActivity<T extends AlfonzView, R extends AlfonzViewModel<T>, B extends ViewDataBinding> extends AlfonzActivity implements AlfonzView
{
	private final ViewModelHelper<T, R> mViewModelHelper = new ViewModelHelper<>();
	private B mBinding;


	public abstract B inflateBindingLayout(@NonNull LayoutInflater inflater);


	@CallSuper
	@Override
	@SuppressWarnings("unchecked")
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);

		// get viewmodel class and init
		Class<? extends AbstractViewModel<T>> viewModelClass = getViewModelClass();
		if(viewModelClass == null)
		{
			viewModelClass = (Class<? extends AbstractViewModel<T>>) ProxyViewHelper.getGenericType(getClass(), AbstractViewModel.class);
		}
		mViewModelHelper.onCreate(this, savedInstanceState, viewModelClass, getIntent().getExtras());

		// setup binding and content view
		mBinding = setupBinding(getLayoutInflater());
		setContentView(mBinding.getRoot());

		// set view
		setModelView((T) this);
	}


	@CallSuper
	@Override
	public void onStart()
	{
		super.onStart();
		mViewModelHelper.onStart();
	}


	@CallSuper
	@Override
	public void onStop()
	{
		super.onStop();
		mViewModelHelper.onStop();
	}


	@CallSuper
	@Override
	public void onDestroy()
	{
		mViewModelHelper.onDestroy(this);
		super.onDestroy();
	}


	@CallSuper
	@Override
	public void onSaveInstanceState(@NonNull final Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mViewModelHelper.onSaveInstanceState(outState);
	}


	@Override
	public void removeViewModel()
	{
		mViewModelHelper.removeViewModel(this);
	}


	@Nullable
	@Override
	public ViewModelBindingConfig getViewModelBindingConfig()
	{
		return null;
	}


	@Override
	public Bundle getExtras()
	{
		return getIntent().getExtras();
	}


	public B getBinding()
	{
		return mBinding;
	}


	@Nullable
	public Class<R> getViewModelClass()
	{
		return null;
	}


	@SuppressWarnings("unused")
	@NonNull
	public R getViewModel()
	{
		return mViewModelHelper.getViewModel();
	}


	@SuppressWarnings("unused")
	public void setModelView(@NonNull final T view)
	{
		mViewModelHelper.setView(view);
	}


	private B setupBinding(@NonNull LayoutInflater inflater)
	{
		B binding = inflateBindingLayout(inflater);
		binding.setVariable(BR.view, this);
		binding.setVariable(BR.viewModel, getViewModel());
		return binding;
	}
}
