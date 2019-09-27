package org.alfonz.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class DataBoundListAdapter<T> extends ListAdapter<T, BaseDataBoundRecyclerViewHolder> {
	private AdapterView mView;
	private ItemViewType mItemViewType;
	private LayoutInflater mLayoutInflater;

	public interface ItemViewType {
		int getLayoutId(Object item);
	}

	public DataBoundListAdapter(AdapterView view, @LayoutRes final int layoutId, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
		this(view, new ItemViewType() {
			@Override
			public int getLayoutId(Object item) {
				return layoutId;
			}
		}, diffCallback);
	}

	public DataBoundListAdapter(AdapterView view, ItemViewType itemViewType, @NonNull DiffUtil.ItemCallback<T> diffCallback) {
		super(diffCallback);
		mView = view;
		mItemViewType = itemViewType;
	}

	@NonNull
	@Override
	public BaseDataBoundRecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mLayoutInflater == null) {
			mLayoutInflater = LayoutInflater.from(parent.getContext());
		}

		return BaseDataBoundRecyclerViewHolder.create(mView, mLayoutInflater, parent, viewType);
	}

	@Override
	public void onBindViewHolder(@NonNull BaseDataBoundRecyclerViewHolder holder, int position) {
		holder.binding.setVariable(BR.view, mView);
		holder.binding.setVariable(BR.data, getItem(position));
		holder.binding.executePendingBindings();
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		mLayoutInflater = null;
	}

	@Override
	public int getItemViewType(int position) {
		return mItemViewType.getLayoutId(getItem(position));
	}
}
