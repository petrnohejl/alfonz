package org.alfonz.arch.bundle;

import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;

import org.alfonz.arch.AlfonzViewModel;


public abstract class AlfonzBundleViewModel extends AlfonzViewModel
{
	private Application mApplication;
	private Bundle mBundle;


	public AlfonzBundleViewModel()
	{
	}


	public AlfonzBundleViewModel(@NonNull Bundle bundle)
	{
		mBundle = bundle;
	}


	public AlfonzBundleViewModel(@NonNull Application application, @NonNull Bundle bundle)
	{
		mApplication = application;
		mBundle = bundle;
	}


	public <T extends Application> T getApplication()
	{
		// noinspection unchecked
		return (T) mApplication;
	}


	public Bundle getBundle()
	{
		return mBundle;
	}
}
