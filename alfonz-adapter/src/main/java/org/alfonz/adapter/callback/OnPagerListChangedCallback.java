package org.alfonz.adapter.callback;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import org.alfonz.adapter.BaseDataBoundPagerAdapter;

import java.lang.ref.WeakReference;

public class OnPagerListChangedCallback<T extends ObservableList<?>> extends ObservableList.OnListChangedCallback<T> {
	@NonNull private final WeakReference<BaseDataBoundPagerAdapter> mAdapter;

	public OnPagerListChangedCallback(BaseDataBoundPagerAdapter adapter) {
		mAdapter = new WeakReference<>(adapter);
	}

	@Override
	public void onChanged(T sender) {
		onUpdate(sender);
	}

	@Override
	public void onItemRangeChanged(T sender, int positionStart, int itemCount) {
		onUpdate(sender);
	}

	@Override
	public void onItemRangeInserted(T sender, int positionStart, int itemCount) {
		onUpdate(sender);
	}

	@Override
	public void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount) {
		onUpdate(sender);
	}

	@Override
	public void onItemRangeRemoved(T sender, int positionStart, int itemCount) {
		onUpdate(sender);
	}

	@SuppressWarnings("unchecked")
	private void onUpdate(T observableList) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyDataSetChanged();
		} else {
			observableList.removeOnListChangedCallback((ObservableList.OnListChangedCallback) this);
		}
	}
}
