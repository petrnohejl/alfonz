package org.alfonz.samples.alfonzrx;

import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.rx.RxBus;
import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentRxSampleBinding;


public class RxSampleFragment extends BaseFragment<RxSampleView, RxSampleViewModel, FragmentRxSampleBinding> implements RxSampleView
{
	@Nullable
	@Override
	public Class<RxSampleViewModel> getViewModelClass()
	{
		return RxSampleViewModel.class;
	}


	@Override
	public FragmentRxSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentRxSampleBinding.inflate(inflater);
	}


	@Override
	public void onButtonRunClick()
	{
		getViewModel().run();
	}


	@Override
	public void onButtonTerminateClick()
	{
		getViewModel().terminate();
	}


	@Override
	public void onButtonIsRunningClick()
	{
		getViewModel().isRunning();
	}


	@Override
	public void onButtonEventClick()
	{
		RxBus.getInstance().send(new Long(System.currentTimeMillis()));
		RxBus.getInstance().send(new String("Dummy"));
	}
}
