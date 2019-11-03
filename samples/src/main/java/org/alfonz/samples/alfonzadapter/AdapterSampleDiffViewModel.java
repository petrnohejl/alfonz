package org.alfonz.samples.alfonzadapter;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.OnLifecycleEvent;

import org.alfonz.samples.alfonzarch.BaseViewModel;

import java.text.DateFormatSymbols;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AdapterSampleDiffViewModel extends BaseViewModel implements LifecycleObserver {
	public final MediatorLiveData<List<?>> items = new MediatorLiveData<>();

	private final MutableLiveData<List<String>> messages = new MutableLiveData<>();
	private final MutableLiveData<List<Integer>> numbers = new MutableLiveData<>();
	private final MutableLiveData<List<Boolean>> bits = new MutableLiveData<>();

	private int mCounter = 0;

	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart() {
		// load data
		if (messages.getValue() == null) loadData();

		// setup mediator live data
		if (items.getValue() == null) {
			items.addSource(messages, items -> updateItems());
			items.addSource(numbers, items -> updateItems());
			items.addSource(bits, items -> updateItems());
		}
	}

	public String addMessage() {
		String[] months = new DateFormatSymbols().getMonths();
		String message = createMessage(months[mCounter % 12]);
		List<String> list = new ArrayList<>(messages.getValue());
		list.add(message);
		messages.setValue(list);
		return message;
	}

	public void removeMessage(String message) {
		List<String> list = new ArrayList<>(messages.getValue());
		list.remove(message);
		messages.setValue(list);
	}

	private void loadData() {
		loadMessages();
		loadNumbers();
		loadBits();
	}

	private void loadMessages() {
		String[] months = new DateFormatSymbols().getMonths();
		List<String> list = new ArrayList<>();
		for (int i = 0; i < months.length; i++) {
			list.add(createMessage(months[i]));
		}
		messages.setValue(list);
	}

	private void loadNumbers() {
		int a = 0;
		int b = 1;

		List<Integer> list = new ArrayList<>();
		for (int i = 0; i < 16; i++) {
			list.add(a);
			a = a + b;
			b = a - b;
		}
		numbers.setValue(list);
	}

	private void loadBits() {
		List<Boolean> list = new ArrayList<>();
		for (int i = 0; i < 6; i++) {
			list.add(i % 2 == 0);
		}
		bits.setValue(list);
	}

	private String createMessage(String month) {
		return String.format(Locale.US, "%03d %s", ++mCounter, month);
	}

	private void updateItems() {
		List<Object> list = new ArrayList<>();
		list.addAll(messages.getValue());
		list.addAll(numbers.getValue());
		list.addAll(bits.getValue());
		items.setValue(list);
	}
}
