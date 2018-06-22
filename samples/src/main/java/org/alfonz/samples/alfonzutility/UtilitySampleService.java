package org.alfonz.samples.alfonzutility;

import android.app.IntentService;
import android.content.Intent;

import org.alfonz.utility.Logcat;

public class UtilitySampleService extends IntentService {
	public UtilitySampleService() {
		super("UtilitySampleService");
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Logcat.d("");
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		long endTime = System.currentTimeMillis() + 5000L;
		while (System.currentTimeMillis() < endTime) {
			synchronized (this) {
				try {
					// do nothing
					wait(endTime - System.currentTimeMillis());
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Logcat.d("");
	}
}
