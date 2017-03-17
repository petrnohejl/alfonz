package org.alfonz.samples.alfonzrest;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentRestSampleBinding;


public class RestSampleFragment extends BaseFragment<RestSampleView, RestSampleRxViewModel, FragmentRestSampleBinding> implements RestSampleView
{
	@Nullable
	@Override
	public Class<RestSampleRxViewModel> getViewModelClass()
	{
		return RestSampleRxViewModel.class;
	}


	@Override
	public FragmentRestSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentRestSampleBinding.inflate(inflater);
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
		getViewModel().refreshData();
	}
}
