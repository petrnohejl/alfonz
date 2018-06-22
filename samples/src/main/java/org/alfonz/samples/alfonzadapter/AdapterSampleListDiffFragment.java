package org.alfonz.samples.alfonzadapter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSampleListDiffBinding;


public class AdapterSampleListDiffFragment extends BaseFragment<AdapterSampleDiffViewModel, FragmentAdapterSampleListDiffBinding> implements AdapterSampleView
{
	private MessageListDiffAdapter mAdapter;


	public static AdapterSampleListDiffFragment newInstance()
	{
		return new AdapterSampleListDiffFragment();
	}


	@Override
	public AdapterSampleDiffViewModel setupViewModel()
	{
		AdapterSampleDiffViewModel viewModel = ViewModelProviders.of(this).get(AdapterSampleDiffViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}


	@Override
	public FragmentAdapterSampleListDiffBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentAdapterSampleListDiffBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getBinding().executePendingBindings(); // helps to reload recycler scroll position after orientation change
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
			mAdapter = new MessageListDiffAdapter(this);
			getBinding().fragmentAdapterSampleListDiffRecycler.setAdapter(mAdapter);
			getViewModel().items.observe(this, mAdapter::submitList);
		}
	}
}
