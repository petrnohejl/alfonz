package org.alfonz.mvvm;

import android.os.Bundle;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;


public abstract class AlfonzFragment<T extends AlfonzView, R extends AlfonzViewModel<T>> extends ViewModelBaseFragment<T, R> implements AlfonzView
{
	@Override
	public Bundle getExtras()
	{
		return getActivity().getIntent().getExtras();
	}


	public AlfonzActivity getAlfonzActivity()
	{
		return (AlfonzActivity) getActivity();
	}
}
