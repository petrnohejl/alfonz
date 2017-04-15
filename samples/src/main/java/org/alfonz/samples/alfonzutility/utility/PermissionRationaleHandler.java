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

		if(Manifest.permission.READ_EXTERNAL_STORAGE.equals(permission))
			resId = R.string.permission_read_external_storage;
		else if(Manifest.permission.WRITE_EXTERNAL_STORAGE.equals(permission))
			resId = R.string.permission_write_external_storage;
		else if(Manifest.permission.ACCESS_COARSE_LOCATION.equals(permission))
			resId = R.string.permission_access_location;
		else if(Manifest.permission.ACCESS_FINE_LOCATION.equals(permission))
			resId = R.string.permission_access_location;
		else
			resId = R.string.permission_unknown;

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
