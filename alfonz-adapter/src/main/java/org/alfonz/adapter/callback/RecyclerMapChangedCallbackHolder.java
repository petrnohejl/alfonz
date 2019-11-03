package org.alfonz.adapter.callback;

import androidx.databinding.ObservableMap;

import org.alfonz.adapter.BaseDataBoundRecyclerAdapter;

public class RecyclerMapChangedCallbackHolder {
	private OnRecyclerMapChangedCallback mCallback;

	@SuppressWarnings("unchecked")
	public void register(BaseDataBoundRecyclerAdapter adapter, ObservableMap<?, ?> items) {
		if (mCallback == null) {
			mCallback = new OnRecyclerMapChangedCallback(adapter);
			items.addOnMapChangedCallback(mCallback);
		}
	}

	@SuppressWarnings("unchecked")
	public void register(BaseDataBoundRecyclerAdapter adapter, ObservableMap<?, ?>[] items) {
		if (mCallback == null) {
			mCallback = new OnRecyclerMapChangedCallback(adapter);
			for (ObservableMap<?, ?> map : items) {
				map.addOnMapChangedCallback(mCallback);
			}
		}
	}

	@SuppressWarnings("unchecked")
	public void unregister(ObservableMap<?, ?> items) {
		if (mCallback != null) {
			items.removeOnMapChangedCallback(mCallback);
			mCallback = null;
		}
	}

	@SuppressWarnings("unchecked")
	public void unregister(ObservableMap<?, ?>[] items) {
		if (mCallback != null) {
			for (ObservableMap<?, ?> map : items) {
				map.removeOnMapChangedCallback(mCallback);
			}
			mCallback = null;
		}
	}
}
