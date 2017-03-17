package org.alfonz.samples.alfonzrx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentRxSampleBinding;


public class RxSampleFragment extends BaseFragment<RxSampleView, RxSampleViewModel, FragmentRxSampleBinding> implements RxSampleView
{
	@Nullable
	@Override
	public Class<RxSampleViewModel> getViewModelClass()
	{
		return RxSampleViewModel.class;
	}


	@Override
	public FragmentRxSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentRxSampleBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onButtonRunClick()
	{
		getViewModel().run();
	}


	@Override
	public void onButtonTerminateClick()
	{
		getViewModel().terminate();
	}


	@Override
	public void onButtonIsRunningClick()
	{
		getViewModel().isRunning();
	}
}
