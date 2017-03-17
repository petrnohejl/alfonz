package org.alfonz.samples.alfonzadapter;

import android.databinding.ObservableArrayList;

import org.alfonz.samples.alfonzmvvm.BaseViewModel;

import java.text.DateFormatSymbols;


public class AdapterSampleViewModel extends BaseViewModel<AdapterSampleView>
{
	public final ObservableArrayList<String> messages = new ObservableArrayList<>();
	public final ObservableArrayList<Integer> numbers = new ObservableArrayList<>();
	public final ObservableArrayList<Boolean> bits = new ObservableArrayList<>();


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(messages.isEmpty()) loadData();
	}


	private void loadData()
	{
		loadMessages();
		loadNumbers();
		loadBits();
	}


	private void loadMessages()
	{
		String[] months = new DateFormatSymbols().getMonths();
		for(int i = 0; i < months.length; i++)
		{
			messages.add(months[i]);
		}
	}


	private void loadNumbers()
	{
		int a = 0;
		int b = 1;

		for(int i = 0; i < 16; i++)
		{
			numbers.add(a);
			a = a + b;
			b = a - b;
		}
	}


	private void loadBits()
	{
		for(int i = 0; i < 6; i++)
		{
			bits.add(i % 2 == 0);
		}
	}
}
