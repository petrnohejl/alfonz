package org.alfonz.samples.alfonzarch;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ArchSampleViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	private final Bundle mExtras;

	public ArchSampleViewModelFactory(Bundle extras) {
		mExtras = extras;
	}

	@NonNull
	@Override
	@SuppressWarnings("unchecked")
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		return (T) new ArchSampleViewModel(mExtras);
	}
}
