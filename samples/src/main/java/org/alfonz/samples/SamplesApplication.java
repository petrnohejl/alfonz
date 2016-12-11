package org.alfonz.samples;

import android.app.Application;
import android.content.Context;


public class SamplesApplication extends Application
{
	private static SamplesApplication sInstance;


	public SamplesApplication()
	{
		sInstance = this;
	}


	public static Context getContext()
	{
		return sInstance;
	}
}
