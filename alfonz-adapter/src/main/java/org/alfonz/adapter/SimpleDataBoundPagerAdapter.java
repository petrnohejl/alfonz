package org.alfonz.adapter;

import android.view.View;

import org.alfonz.adapter.callback.OnPagerListChangedCallback;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.databinding.ViewDataBinding;

public class SimpleDataBoundPagerAdapter extends BaseDataBoundPagerAdapter {
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableList<?> mItems;

	@SuppressWarnings("unchecked")
	public SimpleDataBoundPagerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableList<?> items) {
		mLayoutId = layoutId;
		mView = view;
		mItems = items;

		mItems.addOnListChangedCallback(new OnPagerListChangedCallback(this));
	}

	@Override
	protected void bindItem(@NonNull ViewDataBinding binding, int position) {
		Object item = mItems.get(position);
		binding.setVariable(BR.view, mView);
		binding.setVariable(BR.data, item);
		binding.getRoot().setTag(item);
	}

	@Override
	public int getItemLayoutId(int position) {
		return mLayoutId;
	}

	@Override
	public int getCount() {
		return mItems.size();
	}

	@Override
	public int getItemPosition(@NonNull Object object) {
		Object item = ((View) object).getTag();
		if (mItems != null) {
			for (int i = 0; i < mItems.size(); i++) {
				if (item == mItems.get(i)) {
					return i;
				}
			}
		}
		return POSITION_NONE;
	}
}
