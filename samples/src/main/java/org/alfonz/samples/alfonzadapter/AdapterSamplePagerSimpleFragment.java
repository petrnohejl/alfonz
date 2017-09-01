package org.alfonz.samples.alfonzadapter;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerBinding;


public class AdapterSamplePagerSimpleFragment extends BaseFragment<AdapterSampleView, AdapterSampleViewModel, FragmentAdapterSamplePagerBinding> implements AdapterSampleView
{
	private MessagePagerSimpleAdapter mAdapter;


	public static AdapterSamplePagerSimpleFragment newInstance()
	{
		return new AdapterSamplePagerSimpleFragment();
	}


	@Nullable
	@Override
	public Class<AdapterSampleViewModel> getViewModelClass()
	{
		return AdapterSampleViewModel.class;
	}


	@Override
	public FragmentAdapterSamplePagerBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentAdapterSamplePagerBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupAdapter();
	}


	@Override
	public void onItemClick(String message)
	{
		showToast(message);
	}


	@Override
	public boolean onItemLongClick(String message)
	{
		showSnackbar(message);
		return true;
	}


	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new MessagePagerSimpleAdapter(this, getViewModel());
			getBinding().fragmentAdapterSamplePager.setAdapter(mAdapter);
		}
	}
}
