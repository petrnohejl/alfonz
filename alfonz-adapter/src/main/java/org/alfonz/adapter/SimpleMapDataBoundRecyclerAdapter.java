package org.alfonz.adapter;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableArrayMap;
import androidx.recyclerview.widget.RecyclerView;

import org.alfonz.adapter.callback.RecyclerMapChangedCallbackHolder;

import java.util.List;

public class SimpleMapDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter {
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableArrayMap<?, ?> mItems;
	private RecyclerMapChangedCallbackHolder mCallbackHolder = new RecyclerMapChangedCallbackHolder();

	public SimpleMapDataBoundRecyclerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableArrayMap<?, ?> items) {
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}

	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads) {
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, mItems.valueAt(position));
	}

	@Override
	public int getItemLayoutId(int position) {
		return mLayoutId;
	}

	@Override
	public int getItemCount() {
		return mItems.size();
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onAttachedToRecyclerView(recyclerView);
		mCallbackHolder.register(this, mItems);
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		super.onDetachedFromRecyclerView(recyclerView);
		mCallbackHolder.unregister(mItems);
	}
}
