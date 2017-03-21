package org.alfonz.samples.alfonzview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentViewSampleBinding;
import org.alfonz.utility.Logcat;
import org.alfonz.view.ObservableScrollView;


public class ViewSampleFragment extends BaseFragment<ViewSampleView, ViewSampleViewModel, FragmentViewSampleBinding> implements ViewSampleView
{
	@Nullable
	@Override
	public Class<ViewSampleViewModel> getViewModelClass()
	{
		return ViewSampleViewModel.class;
	}


	@Override
	public FragmentViewSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentViewSampleBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
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
			public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy)
			{
				Logcat.d("%d", y);
			}
		});
	}
}
