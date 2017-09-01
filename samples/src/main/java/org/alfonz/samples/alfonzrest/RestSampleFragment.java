package org.alfonz.samples.alfonzrest;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

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
	public FragmentRestSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentRestSampleBinding.inflate(inflater);
	}


	@Override
	public void onClick()
	{
		getViewModel().refreshData();
	}
}
