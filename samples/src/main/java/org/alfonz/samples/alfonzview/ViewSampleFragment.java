package org.alfonz.samples.alfonzview;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentViewSampleBinding;
import org.alfonz.utility.Logcat;

public class ViewSampleFragment extends BaseFragment<ViewSampleViewModel, FragmentViewSampleBinding> implements ViewSampleView {
	@Override
	public ViewSampleViewModel setupViewModel() {
		ViewSampleViewModel viewModel = new ViewModelProvider(this).get(ViewSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentViewSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentViewSampleBinding.inflate(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupScrollview();
	}

	private void setupScrollview() {
		getBinding().viewSampleScrollview.setOnScrollViewListener((scrollView, x, y, oldx, oldy) -> Logcat.d("%d", y));
	}
}
