package org.alfonz.samples.alfonzmvvm;

import android.content.Intent;
import android.support.annotation.NonNull;
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
	public FragmentMvvmSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentMvvmSampleBinding.inflate(inflater);
	}


	@Override
	public void onClick()
	{
		getViewModel().updateMessage();
	}


	@Override
	public boolean onLongClick()
	{
		Intent intent = MvvmSampleViewModelActivity.newIntent(getActivity());
		getActivity().startActivity(intent);
		return true;
	}
}
