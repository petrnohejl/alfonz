package org.alfonz.samples.alfonzarch;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.arch.lifecycle.OnLifecycleEvent;
import android.databinding.ObservableField;
import android.os.Bundle;

import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;


public class ArchSampleViewModel extends BaseViewModel implements LifecycleObserver
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<String> message = new ObservableField<>();


	public ArchSampleViewModel(Bundle extras)
	{
	}


	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(message.get() == null) loadData();
	}


	public void updateMessage()
	{
		String s = message.get();
		s += "o";
		message.set(s);
	}


	private void loadData()
	{
		if(NetworkUtility.isOnline(getApplicationContext()))
		{
			// show progress
			state.set(StatefulLayout.PROGRESS);

			// load data
			new Thread(new Runnable()
			{
				@Override
				public void run()
				{
					try
					{
						Thread.sleep(2000L);
						String s = "Hello";
						onLoadData(s);
					}
					catch(InterruptedException e)
					{
						e.printStackTrace();
					}
				}
			}).start();
		}
		else
		{
			state.set(StatefulLayout.OFFLINE);
		}
	}


	private void onLoadData(String s)
	{
		// save data
		message.set(s);

		// show content
		if(message.get() != null)
		{
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}
}
