package org.alfonz.samples.alfonzmedia;

import org.alfonz.samples.alfonzmvvm.BaseView;


public interface MediaSampleView extends BaseView
{
	void onButtonPlaySoundClick();
	void onButtonStopSoundClick();
	void onButtonPickImageFromCameraClick();
	void onButtonPickImageFromGalleryClick();
}
