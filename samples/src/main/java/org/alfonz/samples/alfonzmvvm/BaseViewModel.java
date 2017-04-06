package org.alfonz.samples.alfonzmvvm;

import android.content.Context;

import org.alfonz.mvvm.AlfonzViewModel;
import org.alfonz.samples.SamplesApplication;


public abstract class BaseViewModel<T extends BaseView> extends AlfonzViewModel<T>
{
	public void handleError(String message)
	{
		if(getView() != null)
		{
			getView().showToast(message);
		}
	}


	public Context getApplicationContext()
	{
		return SamplesApplication.getContext();
	}
}
