package org.alfonz.samples.alfonzarch;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.arch.AlfonzBindingActivity;
import org.alfonz.arch.widget.ToolbarIndicator;
import org.alfonz.samples.databinding.ActivityArchSampleViewModelBinding;


public class ArchSampleViewModelActivity extends AlfonzBindingActivity<ArchSampleViewModel, ActivityArchSampleViewModelBinding> implements ArchSampleView
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, ArchSampleViewModelActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public ArchSampleViewModel setupViewModel()
	{
		ArchSampleViewModel viewModel = ViewModelProviders.of(this).get(ArchSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}


	@Override
	public ActivityArchSampleViewModelBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return ActivityArchSampleViewModelBinding.inflate(inflater);
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setupActionBar(ToolbarIndicator.BACK);
	}


	@Override
	public void onClick()
	{
		getViewModel().updateMessage();
	}


	@Override
	public boolean onLongClick()
	{
		getViewModel().updateMessage();
		return true;
	}
}
