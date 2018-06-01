package org.alfonz.samples.alfonzmedia;

import android.arch.lifecycle.MutableLiveData;
import android.graphics.Bitmap;

import org.alfonz.samples.alfonzarch.BaseViewModel;
import org.alfonz.samples.alfonzutility.utility.PermissionRationaleHandler;
import org.alfonz.utility.PermissionManager;


public class MediaSampleViewModel extends BaseViewModel
{
	public final MutableLiveData<Bitmap> bitmap = new MutableLiveData<>();

	public final PermissionManager permissionManager = new PermissionManager(new PermissionRationaleHandler());


	public void updateBitmap(Bitmap bitmap)
	{
		this.bitmap.setValue(bitmap);
	}
}
