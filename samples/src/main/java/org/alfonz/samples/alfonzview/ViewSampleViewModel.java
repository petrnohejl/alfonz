package org.alfonz.samples.alfonzview;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.OnLifecycleEvent;

import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.view.StatefulLayout;


public class ViewSampleViewModel extends BaseViewModel implements LifecycleObserver
{
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<String> message = new MutableLiveData<>();


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(message.getValue() == null) loadData();
	}


	private void loadData()
	{
		// set message
		String s = "";
		for(int i = 0; i < 8; i++)
		{
			s += "lorem ipsum dolor sit amet ";
		}
		message.setValue(s.trim());

		// set state
		state.setValue(StatefulLayout.CONTENT);
	}
}
