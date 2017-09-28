package org.alfonz.samples.alfonzmedia;

import android.databinding.ObservableField;
import android.graphics.Bitmap;

import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.samples.alfonzutility.utility.PermissionRationaleHandler;
import org.alfonz.utility.PermissionManager;


public class MediaSampleViewModel extends BaseViewModel
{
	public final ObservableField<Bitmap> bitmap = new ObservableField<>();

	public final PermissionManager permissionManager = new PermissionManager(new PermissionRationaleHandler());


	public void updateBitmap(Bitmap bitmap)
	{
		this.bitmap.set(bitmap);
	}
}
