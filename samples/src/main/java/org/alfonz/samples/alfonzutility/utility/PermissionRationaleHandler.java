package org.alfonz.samples.alfonzutility.utility;

import android.Manifest;
import android.support.design.widget.Snackbar;
import android.view.View;

import org.alfonz.samples.R;
import org.alfonz.samples.SamplesApplication;
import org.alfonz.utility.PermissionManager;


public class PermissionRationaleHandler implements PermissionManager.RationaleHandler
{
	@Override
	public String getRationaleMessage(String permission)
	{
		int resId;

		switch(permission)
		{
			case Manifest.permission.READ_EXTERNAL_STORAGE:
				resId = R.string.permission_read_external_storage;
				break;
			case Manifest.permission.WRITE_EXTERNAL_STORAGE:
				resId = R.string.permission_write_external_storage;
				break;
			case Manifest.permission.ACCESS_COARSE_LOCATION:
				resId = R.string.permission_access_location;
				break;
			case Manifest.permission.ACCESS_FINE_LOCATION:
				resId = R.string.permission_access_location;
				break;
			default:
				resId = R.string.permission_unknown;
		}

		return SamplesApplication.getContext().getString(resId);
	}


	@Override
	public void showRationale(View rootView, String rationaleMessage, PermissionManager.PermissionAction confirmAction)
	{
		Snackbar
				.make(rootView, rationaleMessage, Snackbar.LENGTH_INDEFINITE)
				.setAction(android.R.string.ok, view -> confirmAction.run())
				.show();
	}
}
