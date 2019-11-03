package org.alfonz.samples.alfonzadapter;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerBinding;

public class AdapterSamplePagerSimpleFragment extends BaseFragment<AdapterSampleViewModel, FragmentAdapterSamplePagerBinding> implements AdapterSampleView {
	private MessagePagerSimpleAdapter mAdapter;

	public static AdapterSamplePagerSimpleFragment newInstance() {
		return new AdapterSamplePagerSimpleFragment();
	}

	@Override
	public AdapterSampleViewModel setupViewModel() {
		AdapterSampleViewModel viewModel = ViewModelProviders.of(this).get(AdapterSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentAdapterSamplePagerBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentAdapterSamplePagerBinding.inflate(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupAdapter();
	}

	@Override
	public void onItemClick(String message) {
		String newMessage = getViewModel().addMessage();
		showSnackbar(getString(R.string.adapter_sample_hello, newMessage));
	}

	@Override
	public boolean onItemLongClick(String message) {
		getViewModel().removeMessage(message);
		showToast(getString(R.string.adapter_sample_goodbye, message));
		return true;
	}

	private void setupAdapter() {
		if (mAdapter == null) {
			mAdapter = new MessagePagerSimpleAdapter(this, getViewModel());
			getBinding().adapterSamplePager.setAdapter(mAdapter);
		}
	}
}
