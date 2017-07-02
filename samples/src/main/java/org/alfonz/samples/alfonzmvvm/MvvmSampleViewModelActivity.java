package org.alfonz.samples.alfonzmvvm;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.view.LayoutInflater;

import org.alfonz.mvvm.AlfonzBindingActivity;
import org.alfonz.samples.databinding.ActivityMvvmSampleViewModelBinding;


public class MvvmSampleViewModelActivity extends AlfonzBindingActivity<MvvmSampleView, MvvmSampleViewModel, ActivityMvvmSampleViewModelBinding> implements MvvmSampleView
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, MvvmSampleViewModelActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Nullable
	@Override
	public Class<MvvmSampleViewModel> getViewModelClass()
	{
		return MvvmSampleViewModel.class;
	}


	@Override
	public ActivityMvvmSampleViewModelBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return ActivityMvvmSampleViewModelBinding.inflate(inflater);
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setupActionBar(INDICATOR_BACK);
	}


	@Override
	public void showToast(@StringRes int stringRes)
	{
	}


	@Override
	public void showToast(String message)
	{
	}


	@Override
	public void showSnackbar(@StringRes int stringRes)
	{
	}


	@Override
	public void showSnackbar(String message)
	{
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
