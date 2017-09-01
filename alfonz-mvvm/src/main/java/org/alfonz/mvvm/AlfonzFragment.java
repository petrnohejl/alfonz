package org.alfonz.mvvm;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;

import eu.inloop.viewmodel.base.ViewModelBaseFragment;


public abstract class AlfonzFragment<T extends AlfonzView, R extends AlfonzViewModel<T>> extends ViewModelBaseFragment<T, R> implements AlfonzView
{
	@Override
	@SuppressWarnings("unchecked")
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView((T) this);
	}


	@Override
	public Bundle getExtras()
	{
		return getActivity().getIntent().getExtras();
	}


	@NonNull
	public AlfonzActivity getAlfonzActivity()
	{
		return (AlfonzActivity) getActivity();
	}
}
