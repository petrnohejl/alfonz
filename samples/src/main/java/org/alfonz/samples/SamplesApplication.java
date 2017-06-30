package org.alfonz.samples;

import android.app.Application;
import android.content.Context;

import com.squareup.leakcanary.LeakCanary;
import com.squareup.leakcanary.RefWatcher;

import org.alfonz.utility.Logcat;


public class SamplesApplication extends Application
{
	private static SamplesApplication sInstance;

	private RefWatcher mRefWatcher;


	public SamplesApplication()
	{
		sInstance = this;
	}


	public static Context getContext()
	{
		return sInstance;
	}


	public static RefWatcher getRefWatcher()
	{
		SamplesApplication application = (SamplesApplication) getContext();
		return application.mRefWatcher;
	}


	@Override
	public void onCreate()
	{
		super.onCreate();

		// this process is dedicated to leak canary for heap analysis
		if(LeakCanary.isInAnalyzerProcess(this)) return;

		// init leak canary
		mRefWatcher = LeakCanary.install(this);

		// init logcat
		Logcat.init(SamplesConfig.LOGS, "ALFONZ");
	}
}
