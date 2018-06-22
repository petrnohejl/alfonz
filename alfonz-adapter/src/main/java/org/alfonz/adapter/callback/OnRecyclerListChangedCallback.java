package org.alfonz.adapter.callback;

import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import org.alfonz.adapter.BaseDataBoundRecyclerAdapter;

import java.lang.ref.WeakReference;

public class OnRecyclerListChangedCallback<T extends ObservableList<?>> extends ObservableList.OnListChangedCallback<T> {
	@NonNull private final WeakReference<BaseDataBoundRecyclerAdapter> mAdapter;

	public OnRecyclerListChangedCallback(BaseDataBoundRecyclerAdapter adapter) {
		mAdapter = new WeakReference<>(adapter);
	}

	@Override
	public void onChanged(T sender) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyDataSetChanged();
		} else {
			removeCallback(sender);
		}
	}

	@Override
	public void onItemRangeChanged(T sender, int positionStart, int itemCount) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyItemRangeChanged(positionStart, itemCount);
		} else {
			removeCallback(sender);
		}
	}

	@Override
	public void onItemRangeInserted(T sender, int positionStart, int itemCount) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyItemRangeInserted(positionStart, itemCount);
		} else {
			removeCallback(sender);
		}
	}

	@Override
	public void onItemRangeMoved(T sender, int fromPosition, int toPosition, int itemCount) {
		if (mAdapter.get() != null) {
			for (int i = 0; i < itemCount; i++) {
				mAdapter.get().notifyItemMoved(fromPosition + i, toPosition + i);
			}
		} else {
			removeCallback(sender);
		}
	}

	@Override
	public void onItemRangeRemoved(T sender, int positionStart, int itemCount) {
		if (mAdapter.get() != null) {
			mAdapter.get().notifyItemRangeRemoved(positionStart, itemCount);
		} else {
			removeCallback(sender);
		}
	}

	@SuppressWarnings("unchecked")
	private void removeCallback(T observableList) {
		observableList.removeOnListChangedCallback((ObservableList.OnListChangedCallback) this);
	}
}
