package org.alfonz.samples.alfonzarch;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;

import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

public class ArchSampleViewModel extends BaseViewModel implements LifecycleObserver {
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<String> message = new MutableLiveData<>();

	public ArchSampleViewModel(Bundle extras) {
	}

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (message.getValue() == null) loadData();
	}

	public void updateMessage() {
		String s = message.getValue();
		s += "o";
		message.setValue(s);
	}

	private void loadData() {
		if (NetworkUtility.isOnline(getApplicationContext())) {
			// show progress
			state.setValue(StatefulLayout.PROGRESS);

			// load data
			@SuppressLint("StaticFieldLeak")
			AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
				@Override
				protected String doInBackground(Void... voids) {
					try {
						Thread.sleep(2000L);
						return "Hello";
					} catch (InterruptedException e) {
						e.printStackTrace();
						return null;
					}
				}

				@Override
				protected void onPostExecute(String s) {
					onLoadData(s);
				}
			}.execute();
		} else {
			state.setValue(StatefulLayout.OFFLINE);
		}
	}

	private void onLoadData(String s) {
		// save data
		message.setValue(s);

		// show content
		if (message.getValue() != null) {
			state.setValue(StatefulLayout.CONTENT);
		} else {
			state.setValue(StatefulLayout.EMPTY);
		}
	}
}
