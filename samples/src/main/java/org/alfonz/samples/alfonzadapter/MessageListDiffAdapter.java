package org.alfonz.samples.alfonzadapter;

import androidx.recyclerview.widget.DiffUtil;

import org.alfonz.adapter.DataBoundListAdapter;
import org.alfonz.samples.R;

public class MessageListDiffAdapter extends DataBoundListAdapter {
	public MessageListDiffAdapter(AdapterSampleView view) {
		super(
				view,
				item -> {
					if (item instanceof String) {
						return R.layout.fragment_adapter_sample_list_message_item;
					} else if (item instanceof Integer) {
						return R.layout.fragment_adapter_sample_list_number_item;
					} else if (item instanceof Boolean) {
						return R.layout.fragment_adapter_sample_list_bit_item;
					}
					throw new IllegalArgumentException("Unknown item type " + item);
				},
				new DiffUtil.ItemCallback() {
					@Override
					public boolean areItemsTheSame(Object oldItem, Object newItem) {
						return oldItem.equals(newItem);
					}

					@Override
					public boolean areContentsTheSame(Object oldItem, Object newItem) {
						return oldItem.equals(newItem);
					}
				}
		);
	}
}
