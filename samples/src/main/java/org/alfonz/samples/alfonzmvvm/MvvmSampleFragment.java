package org.alfonz.samples.alfonzmvvm;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;

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
	public void onClick()
	{
		getViewModel().updateMessage();
	}
}
