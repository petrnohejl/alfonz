package org.alfonz.samples.alfonzarch;

import android.content.Intent;
import android.view.LayoutInflater;

import org.alfonz.samples.databinding.FragmentArchSampleBinding;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class ArchSampleFragment extends BaseFragment<ArchSampleViewModel, FragmentArchSampleBinding> implements ArchSampleView {
	@Override
	public ArchSampleViewModel setupViewModel() {
		ArchSampleViewModelFactory factory = new ArchSampleViewModelFactory(getActivity().getIntent().getExtras());
		ArchSampleViewModel viewModel = ViewModelProviders.of(this, factory).get(ArchSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentArchSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentArchSampleBinding.inflate(inflater);
	}

	@Override
	public void onClick() {
		getViewModel().updateMessage();
	}

	@Override
	public boolean onLongClick() {
		Intent intent = ArchSampleViewModelActivity.newIntent(getActivity());
		getActivity().startActivity(intent);
		return true;
	}
}
