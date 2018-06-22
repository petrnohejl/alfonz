package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.SimpleDataBoundRecyclerAdapter;
import org.alfonz.samples.R;

public class MessageListSimpleAdapter extends SimpleDataBoundRecyclerAdapter {
	public MessageListSimpleAdapter(AdapterSampleView view, AdapterSampleViewModel viewModel) {
		super(
				R.layout.fragment_adapter_sample_list_message_item,
				view,
				viewModel.messages
		);
	}
}
