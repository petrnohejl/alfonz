package org.alfonz.arch;

import android.arch.lifecycle.LifecycleRegistry;
import android.arch.lifecycle.LifecycleRegistryOwner;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;


public abstract class AlfonzFragment<T extends AlfonzViewModel> extends Fragment implements LifecycleRegistryOwner, AlfonzView
{
	private T mViewModel;
	private LifecycleRegistry mLifecycleRegistry = new LifecycleRegistry(this);


	public abstract T setupViewModel();


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		mViewModel = setupViewModel();
	}


	@NonNull
	@Override
	public LifecycleRegistry getLifecycle()
	{
		return mLifecycleRegistry;
	}


	public T getViewModel()
	{
		return mViewModel;
	}


	@NonNull
	public AlfonzActivity getAlfonzActivity()
	{
		return (AlfonzActivity) getActivity();
	}
}
