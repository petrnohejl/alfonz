package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.MultiDataBoundRecyclerAdapter;
import org.alfonz.samples.R;

public class MessageListMultiAdapter extends MultiDataBoundRecyclerAdapter {
	public MessageListMultiAdapter(AdapterSampleView view, AdapterSampleViewModel viewModel) {
		super(
				view,
				viewModel.messages,
				viewModel.numbers,
				viewModel.bits
		);
	}

	@Override
	public int getItemLayoutId(int position) {
		Object item = getItem(position);
		if (item instanceof String) {
			return R.layout.fragment_adapter_sample_list_message_item;
		} else if (item instanceof Integer) {
			return R.layout.fragment_adapter_sample_list_number_item;
		} else if (item instanceof Boolean) {
			return R.layout.fragment_adapter_sample_list_bit_item;
		}
		throw new IllegalArgumentException("Unknown item type " + item);
	}
}
