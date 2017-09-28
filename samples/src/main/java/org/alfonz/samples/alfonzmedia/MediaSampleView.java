package org.alfonz.samples.alfonzmedia;

import org.alfonz.arch.AlfonzView;


public interface MediaSampleView extends AlfonzView
{
	void onButtonPlaySoundClick();
	void onButtonStopSoundClick();
	void onButtonPickImageFromCameraClick();
	void onButtonPickImageFromGalleryClick();
}
