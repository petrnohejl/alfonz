package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.AdapterView;
import org.alfonz.samples.alfonzmvvm.BaseView;


public interface AdapterSampleView extends BaseView, AdapterView
{
	void onItemClick(String message);
	boolean onItemLongClick(String message);
}
