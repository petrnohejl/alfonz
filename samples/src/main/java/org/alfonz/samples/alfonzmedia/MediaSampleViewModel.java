package org.alfonz.samples.alfonzmedia;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import org.alfonz.samples.alfonzmvvm.BaseViewModel;


public class MediaSampleViewModel extends BaseViewModel<MediaSampleView>
{
	public final ObservableField<Bitmap> bitmap = new ObservableField<>();


	public void updateBitmap(Bitmap bitmap)
	{
		this.bitmap.set(bitmap);
	}
}
