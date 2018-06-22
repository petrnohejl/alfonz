package org.alfonz.samples.alfonzadapter;

import org.alfonz.adapter.AdapterView;
import org.alfonz.arch.AlfonzView;

public interface AdapterSampleView extends AlfonzView, AdapterView {
	void onItemClick(String message);
	boolean onItemLongClick(String message);
}
