/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.alfonz.adapter;

import android.databinding.OnRebindCallback;
import android.databinding.ViewDataBinding;
import android.support.annotation.CallSuper;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/**
 * A reference implementation for an adapter that wants to use data binding "the right way". It
 * works with {@link BaseDataBoundRecyclerViewHolder}.
 * <p>
 * It listens for layout invalidations and notifies RecyclerView about them before views refresh
 * themselves. It also avoids invalidating the full item when data in the bound item dispatches
 * proper notify events.
 * <p>
 * This class uses layout id as the item type.
 * <p>
 * It can be used for both single type lists and multiple type lists.
 *
 * @param <T> The type of the ViewDataBinding class. Can be omitted in multiple-binding-type use
 * case.
 */
public abstract class BaseDataBoundRecyclerAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter<BaseDataBoundRecyclerViewHolder<T>> {
	private static final Object DB_PAYLOAD = new Object();

	private LayoutInflater mLayoutInflater;
	@Nullable
	private RecyclerView mRecyclerView;

	/**
	 * This is used to block items from updating themselves. RecyclerView wants to know when an
	 * item is invalidated and it prefers to refresh it via onRebind. It also helps with performance
	 * since data binding will not update views that are not changed.
	 */
	private final OnRebindCallback mOnRebindCallback = new OnRebindCallback() {
		@Override
		public boolean onPreBind(@NonNull ViewDataBinding binding) {
			if (mRecyclerView == null || mRecyclerView.isComputingLayout()) {
				return true;
			}
			int childAdapterPosition = mRecyclerView.getChildAdapterPosition(binding.getRoot());
			if (childAdapterPosition == RecyclerView.NO_POSITION) {
				return true;
			}
			notifyItemChanged(childAdapterPosition, DB_PAYLOAD);
			return false;
		}
	};

	/**
	 * Override this method to handle binding your items into views
	 *
	 * @param holder The ViewHolder that has the binding instance
	 * @param position The position of the item in the adapter
	 * @param payloads The payloads that were passed into the onBind method
	 */
	protected abstract void bindItem(BaseDataBoundRecyclerViewHolder<T> holder, int position, List<Object> payloads);

	@LayoutRes
	public abstract int getItemLayoutId(int position);

	@Override
	@CallSuper
	@NonNull
	public BaseDataBoundRecyclerViewHolder<T> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		if (mLayoutInflater == null) {
			mLayoutInflater = LayoutInflater.from(parent.getContext());
		}

		BaseDataBoundRecyclerViewHolder<T> vh = BaseDataBoundRecyclerViewHolder.create(mLayoutInflater, parent, viewType);
		vh.binding.addOnRebindCallback(mOnRebindCallback);
		return vh;
	}

	@Override
	public final void onBindViewHolder(@NonNull BaseDataBoundRecyclerViewHolder<T> holder, int position, @NonNull List<Object> payloads) {
		// when a VH is rebound to the same item, we don't have to call the setters
		if (payloads.isEmpty() || hasNonDataBindingInvalidate(payloads)) {
			bindItem(holder, position, payloads);
		}
		holder.binding.executePendingBindings();
	}

	@Override
	public final void onBindViewHolder(@NonNull BaseDataBoundRecyclerViewHolder<T> holder, int position) {
		throw new IllegalArgumentException("just overridden to make final.");
	}

	@Override
	public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
		mRecyclerView = recyclerView;
	}

	@Override
	public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
		mRecyclerView = null;
		mLayoutInflater = null;
	}

	@Override
	public final int getItemViewType(int position) {
		return getItemLayoutId(position);
	}

	private boolean hasNonDataBindingInvalidate(@NonNull List<Object> payloads) {
		for (Object payload : payloads) {
			if (payload != DB_PAYLOAD) {
				return true;
			}
		}
		return false;
	}
}
