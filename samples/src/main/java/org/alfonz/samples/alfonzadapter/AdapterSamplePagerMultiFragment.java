package org.alfonz.samples.alfonzadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerBinding;


public class AdapterSamplePagerMultiFragment extends BaseFragment<AdapterSampleView, AdapterSampleViewModel, FragmentAdapterSamplePagerBinding> implements AdapterSampleView
{
	private MessagePagerMultiAdapter mAdapter;


	public static AdapterSamplePagerMultiFragment newInstance()
	{
		return new AdapterSamplePagerMultiFragment();
	}


	@Nullable
	@Override
	public Class<AdapterSampleViewModel> getViewModelClass()
	{
		return AdapterSampleViewModel.class;
	}


	@Override
	public FragmentAdapterSamplePagerBinding inflateBindingLayout(LayoutInflater inflater)
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
			mAdapter = new MessagePagerMultiAdapter(this, getViewModel());
			getBinding().fragmentAdapterSamplePager.setAdapter(mAdapter);
		}
	}
}
