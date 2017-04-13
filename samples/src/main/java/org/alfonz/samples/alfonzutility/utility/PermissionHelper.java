package org.alfonz.samples.alfonzutility.utility;

import android.Manifest;
import android.support.v4.app.Fragment;

import org.alfonz.samples.R;
import org.alfonz.utility.PermissionUtility;


public final class PermissionHelper
{
	public static final int REQUEST_PERMISSION_READ_EXTERNAL_STORAGE = 1;
	public static final int REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE = 2;
	public static final int REQUEST_PERMISSION_ACCESS_LOCATION = 3;
	public static final int REQUEST_PERMISSION_ALL = 4;


	private PermissionHelper() {}


	public static boolean checkPermissionReadExternalStorage(final Fragment fragment)
	{
		return PermissionUtility.check(fragment,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				R.string.permission_read_external_storage,
				REQUEST_PERMISSION_READ_EXTERNAL_STORAGE);
	}


	public static boolean checkPermissionWriteExternalStorage(final Fragment fragment)
	{
		return PermissionUtility.check(fragment,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				R.string.permission_write_external_storage,
				REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE);
	}


	public static boolean checkPermissionAccessLocation(final Fragment fragment)
	{
		return PermissionUtility.check(fragment,
				new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
				new int[]{R.string.permission_access_location, R.string.permission_access_location},
				REQUEST_PERMISSION_ACCESS_LOCATION);
	}


	public static boolean checkPermissionAll(final Fragment fragment)
	{
		return PermissionUtility.check(fragment,
				new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
				new int[]{R.string.permission_read_external_storage, R.string.permission_write_external_storage, R.string.permission_access_location, R.string.permission_access_location},
				REQUEST_PERMISSION_ALL);
	}
}
