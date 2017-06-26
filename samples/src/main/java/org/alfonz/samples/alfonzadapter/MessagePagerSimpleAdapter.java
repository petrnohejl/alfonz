package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.SimpleDataBoundPagerAdapter;
import org.alfonz.samples.R;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerMessageItemBinding;


public class MessagePagerSimpleAdapter extends SimpleDataBoundPagerAdapter<FragmentAdapterSamplePagerMessageItemBinding>
{
	public MessagePagerSimpleAdapter(AdapterSampleView view, AdapterSampleViewModel viewModel)
	{
		super(
				R.layout.fragment_adapter_sample_pager_message_item,
				view,
				viewModel.messages
		);
	}
}
