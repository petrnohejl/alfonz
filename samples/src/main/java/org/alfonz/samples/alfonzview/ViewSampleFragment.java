package org.alfonz.samples.alfonzview;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentViewSampleBinding;
import org.alfonz.utility.Logcat;
import org.alfonz.view.ObservableScrollView;


public class ViewSampleFragment extends BaseFragment<ViewSampleViewModel, FragmentViewSampleBinding> implements ViewSampleView
{
	@Override
	public ViewSampleViewModel setupViewModel()
	{
		ViewSampleViewModel viewModel = ViewModelProviders.of(this).get(ViewSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}


	@Override
	public FragmentViewSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentViewSampleBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupScrollview();
	}


	private void setupScrollview()
	{
		getBinding().fragmentViewSampleScrollview.setOnScrollViewListener(new ObservableScrollView.OnScrollViewListener()
		{
			@Override
			public void onScrollChanged(@NonNull ObservableScrollView scrollView, int x, int y, int oldx, int oldy)
			{
				Logcat.d("%d", y);
			}
		});
	}
}
