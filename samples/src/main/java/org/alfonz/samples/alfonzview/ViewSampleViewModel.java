package org.alfonz.samples.alfonzview;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;

import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.view.StatefulLayout;


public class ViewSampleViewModel extends BaseViewModel implements LifecycleObserver
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<String> message = new ObservableField<>();


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(message.get() == null) loadData();
	}


	private void loadData()
	{
		// set message
		String s = "";
		for(int i = 0; i < 8; i++)
		{
			s += "lorem ipsum dolor sit amet ";
		}
		message.set(s.trim());

		// set state
		state.set(StatefulLayout.CONTENT);
	}
}
