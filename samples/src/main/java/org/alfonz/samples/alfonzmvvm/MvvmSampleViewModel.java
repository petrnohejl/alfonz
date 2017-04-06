package org.alfonz.samples.alfonzmvvm;

import android.databinding.ObservableField;

import org.alfonz.utility.NetworkUtility;
import org.alfonz.view.StatefulLayout;


public class MvvmSampleViewModel extends BaseViewModel<MvvmSampleView>
{
	public final ObservableField<StatefulLayout.State> state = new ObservableField<>();
	public final ObservableField<String> message = new ObservableField<>();


	@Override
	public void onStart()
	{
		super.onStart();

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
			state.set(StatefulLayout.State.PROGRESS);

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
			state.set(StatefulLayout.State.OFFLINE);
		}
	}


	private void onLoadData(String s)
	{
		// save data
		message.set(s);

		// show content
		if(message.get() != null)
		{
			state.set(StatefulLayout.State.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.State.EMPTY);
		}
	}
}
