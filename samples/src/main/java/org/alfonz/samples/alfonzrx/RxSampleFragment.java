package org.alfonz.samples.alfonzrx;

import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import org.alfonz.rx.RxBus;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentRxSampleBinding;

public class RxSampleFragment extends BaseFragment<RxSampleViewModel, FragmentRxSampleBinding> implements RxSampleView {
	@Override
	public RxSampleViewModel setupViewModel() {
		return ViewModelProviders.of(this).get(RxSampleViewModel.class);
	}

	@Override
	public FragmentRxSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentRxSampleBinding.inflate(inflater);
	}

	@Override
	public void onButtonRunClick() {
		getViewModel().run();
	}

	@Override
	public void onButtonTerminateClick() {
		getViewModel().terminate();
	}

	@Override
	public void onButtonIsRunningClick() {
		getViewModel().isRunning();
	}

	@Override
	public void onButtonEventClick() {
		RxBus.getInstance().send(Long.valueOf(System.currentTimeMillis()));
		RxBus.getInstance().send(new String("Dummy"));
	}
}
