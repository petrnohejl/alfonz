package org.alfonz.samples.alfonzarch;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.os.Bundle;


public class ArchSampleViewModelFactory extends ViewModelProvider.NewInstanceFactory
{
	private final Bundle mExtras;


	public ArchSampleViewModelFactory(Bundle extras)
	{
		mExtras = extras;
	}


	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(Class<T> modelClass)
	{
		return (T) new ArchSampleViewModel(mExtras);
	}
}
