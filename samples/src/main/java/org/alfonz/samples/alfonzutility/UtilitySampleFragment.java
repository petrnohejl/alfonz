package org.alfonz.samples.alfonzutility;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.alfonzutility.utility.PermissionHelper;
import org.alfonz.samples.databinding.FragmentUtilitySampleBinding;
import org.alfonz.utility.KeyboardUtility;


public class UtilitySampleFragment extends BaseFragment<UtilitySampleView, UtilitySampleViewModel, FragmentUtilitySampleBinding> implements UtilitySampleView
{
	@Nullable
	@Override
	public Class<UtilitySampleViewModel> getViewModelClass()
	{
		return UtilitySampleViewModel.class;
	}


	@Override
	public FragmentUtilitySampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentUtilitySampleBinding.inflate(inflater);
	}


	@Override
	public void onViewCreated(View view, Bundle savedInstanceState)
	{
		super.onViewCreated(view, savedInstanceState);
		setModelView(this);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		switch(requestCode)
		{
			case PermissionHelper.REQUEST_PERMISSION_READ_EXTERNAL_STORAGE:
			case PermissionHelper.REQUEST_PERMISSION_WRITE_EXTERNAL_STORAGE:
			case PermissionHelper.REQUEST_PERMISSION_ACCESS_LOCATION:
			case PermissionHelper.REQUEST_PERMISSION_ALL:
			{
				if(grantResults.length > 0)
				{
					for(int i = 0; i < grantResults.length; i++)
					{
						if(grantResults[i] == PackageManager.PERMISSION_GRANTED)
						{
							// permission granted
							String permission = permissions[i];
							if(permission.equals(Manifest.permission.READ_EXTERNAL_STORAGE))
							{
								// do something
							}
							else if(permission.equals(Manifest.permission.WRITE_EXTERNAL_STORAGE))
							{
								// do something
							}
							else if(permission.equals(Manifest.permission.ACCESS_COARSE_LOCATION) ||
									permission.equals(Manifest.permission.ACCESS_FINE_LOCATION))
							{
								// do something
							}
						}
						else
						{
							// permission denied
						}
					}
				}
				else
				{
					// all permissions denied
				}
				break;
			}
		}
	}


	@Override
	public void onButtonShowKeyboardClick()
	{
		performShowKeyboard();
	}


	@Override
	public void onButtonHideKeyboardClick()
	{
		performHideKeyboard();
	}


	@Override
	public void onButtonPermissionClick()
	{
		performPermissionUtility();
	}


	@Override
	public void onButtonLogcatClick()
	{
		getViewModel().performLogcat();
	}


	@Override
	public void onButtonDownloadClick()
	{
		getViewModel().performDownloadUtility();
	}


	@Override
	public void onButtonZipClick()
	{
		getViewModel().performZipUtility();
	}


	private void performShowKeyboard()
	{
		KeyboardUtility.showKeyboard(getBinding().fragmentUtilitySampleEdittext);
	}


	private void performHideKeyboard()
	{
		KeyboardUtility.hideKeyboard(getBinding().fragmentUtilitySampleEdittext);
	}


	private void performPermissionUtility()
	{
		if(PermissionHelper.checkPermissionAll(this))
		{
			// do something
		}
	}
}
