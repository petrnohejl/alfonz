package org.alfonz.samples.alfonzarch;

import android.content.Context;

import org.alfonz.arch.AlfonzViewModel;
import org.alfonz.samples.SamplesApplication;
import org.alfonz.samples.alfonzarch.event.ToastEvent;


public abstract class BaseViewModel extends AlfonzViewModel
{
	public Context getApplicationContext()
	{
		return SamplesApplication.getContext();
	}


	public void handleError(String message)
	{
		sendEvent(new ToastEvent(message));
	}
}
