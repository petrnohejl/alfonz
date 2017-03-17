package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.SimpleDataBoundPagerAdapter;
import org.alfonz.samples.R;
import org.alfonz.samples.databinding.FragmentAdapterSamplePagerItemBinding;


public class MessagePagerAdapter extends SimpleDataBoundPagerAdapter<FragmentAdapterSamplePagerItemBinding>
{
	public MessagePagerAdapter(AdapterSampleView view, AdapterSampleViewModel viewModel)
	{
		super(
				R.layout.fragment_adapter_sample_pager_item,
				view,
				viewModel.messages
		);
	}
}
