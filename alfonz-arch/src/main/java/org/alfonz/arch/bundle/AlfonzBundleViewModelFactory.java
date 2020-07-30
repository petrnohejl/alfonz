package org.alfonz.arch.bundle;

import android.app.Application;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import org.alfonz.arch.AlfonzActivity;
import org.alfonz.arch.AlfonzFragment;

import java.lang.reflect.InvocationTargetException;

public class AlfonzBundleViewModelFactory extends ViewModelProvider.NewInstanceFactory {
	private final Application mApplication;
	private final Bundle mBundle;

	public AlfonzBundleViewModelFactory() {
		mApplication = null;
		mBundle = null;
	}

	public AlfonzBundleViewModelFactory(@NonNull AlfonzActivity activity) {
		mApplication = null;
		mBundle = activity.getIntent().getExtras();
	}

	public AlfonzBundleViewModelFactory(@NonNull AlfonzFragment<?> fragment) {
		mApplication = null;
		mBundle = fragment.getArguments();
	}

	public AlfonzBundleViewModelFactory(@NonNull Bundle bundle) {
		mApplication = null;
		mBundle = bundle;
	}

	public AlfonzBundleViewModelFactory(@NonNull Application application, @NonNull AlfonzActivity activity) {
		mApplication = application;
		mBundle = activity.getIntent().getExtras();
	}

	public AlfonzBundleViewModelFactory(@NonNull Application application, @NonNull AlfonzFragment<?> fragment) {
		mApplication = application;
		mBundle = fragment.getArguments();
	}

	public AlfonzBundleViewModelFactory(@NonNull Application application, @NonNull Bundle bundle) {
		mApplication = application;
		mBundle = bundle;
	}

	@NonNull
	@Override
	public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
		try {
			if (mApplication != null && mBundle != null) {
				return modelClass.getDeclaredConstructor(Application.class, Bundle.class).newInstance(mApplication, mBundle);
			} else if (mBundle != null) {
				return modelClass.getDeclaredConstructor(Bundle.class).newInstance(mBundle);
			} else {
				return modelClass.getDeclaredConstructor().newInstance();
			}
		} catch (NoSuchMethodException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			throw new RuntimeException("Cannot create an instance of " + modelClass, e);
		}
	}
}
