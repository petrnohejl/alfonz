package org.alfonz.samples.alfonzadapter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSampleListBinding;


public class AdapterSampleListSimpleFragment extends BaseFragment<AdapterSampleView, AdapterSampleViewModel, FragmentAdapterSampleListBinding> implements AdapterSampleView
{
	private MessageListSimpleAdapter mAdapter;


	public static AdapterSampleListSimpleFragment newInstance()
	{
		return new AdapterSampleListSimpleFragment();
	}


	@Nullable
	@Override
	public Class<AdapterSampleViewModel> getViewModelClass()
	{
		return AdapterSampleViewModel.class;
	}


	@Override
	public FragmentAdapterSampleListBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentAdapterSampleListBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		getBinding().executePendingBindings(); // set layout manager in recycler via binding adapter
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
			mAdapter = new MessageListSimpleAdapter(this, getViewModel());
			getBinding().fragmentAdapterSampleListRecycler.setAdapter(mAdapter);
		}
	}
}
