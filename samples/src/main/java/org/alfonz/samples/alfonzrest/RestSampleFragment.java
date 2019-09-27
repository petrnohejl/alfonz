package org.alfonz.samples.alfonzrest;

import android.view.LayoutInflater;

import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentRestSampleBinding;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class RestSampleFragment extends BaseFragment<RestSampleRxViewModel, FragmentRestSampleBinding> implements RestSampleView {
	@Override
	public RestSampleRxViewModel setupViewModel() {
		RestSampleRxViewModel viewModel = ViewModelProviders.of(this).get(RestSampleRxViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentRestSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentRestSampleBinding.inflate(inflater);
	}

	@Override
	public void onClick() {
		getViewModel().refreshData();
	}
}
