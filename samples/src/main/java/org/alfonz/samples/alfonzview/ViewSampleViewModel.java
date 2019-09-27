package org.alfonz.samples.alfonzview;

import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.view.StatefulLayout;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

public class ViewSampleViewModel extends BaseViewModel implements LifecycleObserver {
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<String> message = new MutableLiveData<>();

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (message.getValue() == null) loadData();
	}

	private void loadData() {
		// set message
		String s = "";
		for (int i = 0; i < 8; i++) {
			s += "lorem ipsum dolor sit amet ";
		}
		message.setValue(s.trim());

		// set state
		state.setValue(StatefulLayout.CONTENT);
	}
}
