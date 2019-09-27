package org.alfonz.adapter;

import org.alfonz.adapter.callback.RecyclerListChangedCallbackHolder;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public class SimpleDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter {
	@LayoutRes private int mLayoutId;
	private AdapterView mView;
	private ObservableList<?> mItems;
	private RecyclerListChangedCallbackHolder mCallbackHolder = new RecyclerListChangedCallbackHolder();

	public SimpleDataBoundRecyclerAdapter(@LayoutRes int layoutId, AdapterView view, ObservableList<?> items) {
		mLayoutId = layoutId;
		mView = view;
		mItems = items;
	}

	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads) {
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, mItems.get(position));
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
