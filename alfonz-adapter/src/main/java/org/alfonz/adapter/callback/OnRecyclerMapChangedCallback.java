package org.alfonz.adapter.callback;

import android.databinding.ObservableMap;
import android.support.annotation.NonNull;

import org.alfonz.adapter.BaseDataBoundRecyclerAdapter;

import java.lang.ref.WeakReference;

public class OnRecyclerMapChangedCallback<T extends ObservableMap<K, V>, K, V> extends ObservableMap.OnMapChangedCallback<T, K, V> {
	@NonNull private final WeakReference<BaseDataBoundRecyclerAdapter> mAdapter;

	public OnRecyclerMapChangedCallback(BaseDataBoundRecyclerAdapter adapter) {
		mAdapter = new WeakReference<>(adapter);
	}

	@Override
	public void onMapChanged(T sender, K key) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyDataSetChanged();
		} else {
			sender.removeOnMapChangedCallback(this);
		}
	}
}
