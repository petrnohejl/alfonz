package org.alfonz.samples.alfonzmvvm;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.alfonz.samples.databinding.FragmentMvvmSampleBinding;


public class MvvmSampleFragment extends BaseFragment<MvvmSampleView, MvvmSampleViewModel, FragmentMvvmSampleBinding> implements MvvmSampleView
{
	@Nullable
	@Override
	public Class<MvvmSampleViewModel> getViewModelClass()
	{
		return MvvmSampleViewModel.class;
	}


	@Override
	public FragmentMvvmSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentMvvmSampleBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onClick()
	{
		getViewModel().updateMessage();
	}
}
