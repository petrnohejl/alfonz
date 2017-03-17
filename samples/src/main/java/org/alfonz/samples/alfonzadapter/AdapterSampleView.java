package org.alfonz.samples.alfonzadapter;

import org.alfonz.samples.alfonzmvvm.BaseView;


public interface AdapterSampleView extends BaseView
{
	void onItemClick(String message);
	boolean onItemLongClick(String message);
}
