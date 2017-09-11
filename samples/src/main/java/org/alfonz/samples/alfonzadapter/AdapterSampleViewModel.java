package org.alfonz.samples.alfonzadapter;

import android.databinding.ObservableArrayList;

import org.alfonz.samples.alfonzmvvm.BaseViewModel;

import java.text.DateFormatSymbols;
import java.util.Locale;


public class AdapterSampleViewModel extends BaseViewModel<AdapterSampleView>
{
	public final ObservableArrayList<String> messages = new ObservableArrayList<>();
	public final ObservableArrayList<Integer> numbers = new ObservableArrayList<>();
	public final ObservableArrayList<Boolean> bits = new ObservableArrayList<>();

	private int mCounter = 0;


	@Override
	public void onStart()
	{
		super.onStart();

		// load data
		if(messages.isEmpty()) loadData();
	}


	public String addMessage()
	{
		String[] months = new DateFormatSymbols().getMonths();
		String message = createMessage(months[mCounter % 12]);
		messages.add(message);
		return message;
	}


	public void removeMessage(String message)
	{
		messages.remove(message);
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
			messages.add(createMessage(months[i]));
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


	private String createMessage(String month)
	{
		return String.format(Locale.US, "%03d %s", ++mCounter, month);
	}
}
