package org.alfonz.adapter;

import org.alfonz.adapter.callback.RecyclerListChangedCallbackHolder;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ObservableList;
import androidx.recyclerview.widget.RecyclerView;

public abstract class MultiDataBoundRecyclerAdapter extends BaseDataBoundRecyclerAdapter {
	private AdapterView mView;
	private ObservableList<?>[] mItems;
	private RecyclerListChangedCallbackHolder mCallbackHolder = new RecyclerListChangedCallbackHolder();

	public MultiDataBoundRecyclerAdapter(AdapterView view, ObservableList<?>... items) {
		mView = view;
		mItems = items;
	}

	@Override
	protected void bindItem(@NonNull BaseDataBoundRecyclerViewHolder holder, int position, List payloads) {
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, getItem(position));
	}

	@Override
	public int getItemCount() {
		int size = 0;
		for (ObservableList<?> list : mItems) {
			size += list.size();
		}
		return size;
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

	@Nullable
	public Object getItem(int position) {
		int counter = 0;
		for (ObservableList<?> list : mItems) {
			if (position - counter - list.size() < 0) {
				int index = position - counter;
				return list.get(index);
			}
			counter += list.size();
		}
		return null;
	}
}
