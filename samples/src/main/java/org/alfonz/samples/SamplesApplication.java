package org.alfonz.samples;

import android.app.Application;
import android.content.Context;

import org.alfonz.utility.Logcat;

public class SamplesApplication extends Application {
	private static SamplesApplication sInstance;

	public SamplesApplication() {
		sInstance = this;
	}

	public static Context getContext() {
		return sInstance;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		// init logcat
		Logcat.init(SamplesConfig.LOGS, "ALFONZ");
	}
}
