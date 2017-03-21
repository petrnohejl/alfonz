package org.alfonz.samples.alfonzview;

import android.databinding.ObservableField;

import org.alfonz.samples.alfonzmvvm.BaseViewModel;
import org.alfonz.view.StatefulLayout;


public class ViewSampleViewModel extends BaseViewModel<ViewSampleView>
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


	private void loadData()
	{
		// set message
		String s = "";
		for(int i = 0; i<8; i++)
		{
			s += "lorem ipsum dolor sit amet ";
		}
		message.set(s.trim());

		// set state
		state.set(StatefulLayout.State.CONTENT);
	}
}
