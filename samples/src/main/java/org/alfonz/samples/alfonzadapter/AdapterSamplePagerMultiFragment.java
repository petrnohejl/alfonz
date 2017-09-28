package org.alfonz.samples.alfonzadapter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerBinding;


public class AdapterSamplePagerMultiFragment extends BaseFragment<AdapterSampleViewModel, FragmentAdapterSamplePagerBinding> implements AdapterSampleView
{
	private MessagePagerMultiAdapter mAdapter;


	public static AdapterSamplePagerMultiFragment newInstance()
	{
		return new AdapterSamplePagerMultiFragment();
	}


	@Override
	public AdapterSampleViewModel setupViewModel()
	{
		AdapterSampleViewModel viewModel = ViewModelProviders.of(this).get(AdapterSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
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
		String newMessage = getViewModel().addMessage();
		showSnackbar(getString(R.string.fragment_adapter_sample_hello, newMessage));
	}


	@Override
	public boolean onItemLongClick(String message)
	{
		getViewModel().removeMessage(message);
		showToast(getString(R.string.fragment_adapter_sample_goodbye, message));
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
