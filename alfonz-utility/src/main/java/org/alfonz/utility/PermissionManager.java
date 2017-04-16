package org.alfonz.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.View;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class PermissionManager
{
	private static final int REQUEST_CODE_PERMISSION = 1;
	private static final int REQUEST_CODE_PERMISSIONS = 2;

	private Activity mActivity;
	private Fragment mFragment;
	private RationaleHandler mRationaleHandler;
	private PermissionCallback mPermissionCallback;
	private PermissionsCallback mPermissionsCallback;


	public interface RationaleHandler
	{
		String getRationaleMessage(String permission);
		void showRationale(View rootView, String rationaleMessage, PermissionAction confirmAction);
	}


	public interface PermissionAction
	{
		void run();
	}


	public interface PermissionCallback
	{
		void onPermissionGranted();
		void onPermissionDenied();
		void onPermissionBlocked();
	}


	public interface PermissionsCallback
	{
		void onPermissionsResult(PermissionsResult permissionsResult);
	}


	public PermissionManager(@NonNull Activity activity, @NonNull RationaleHandler rationaleHandler)
	{
		mActivity = activity;
		mRationaleHandler = rationaleHandler;
	}


	public PermissionManager(@NonNull Fragment fragment, @NonNull RationaleHandler rationaleHandler)
	{
		mFragment = fragment;
		mRationaleHandler = rationaleHandler;
	}


	public boolean check(String permission)
	{
		int result = ContextCompat.checkSelfPermission(getContext(), permission);
		return result == PackageManager.PERMISSION_GRANTED;
	}


	public PermissionsResult check(String... permissions)
	{
		Map<String, Boolean> resultMap = new HashMap<>();
		for(String permission : permissions)
		{
			int result = ContextCompat.checkSelfPermission(getContext(), permission);
			resultMap.put(permission, result == PackageManager.PERMISSION_GRANTED);
		}
		return new PermissionsResult(resultMap);
	}


	public void request(final String permission, final PermissionAction grantedAction)
	{
		request(permission, new PermissionCallback()
		{
			@Override
			public void onPermissionGranted()
			{
				grantedAction.run();
			}


			@Override
			public void onPermissionDenied() {}


			@Override
			public void onPermissionBlocked() {}
		});
	}


	public void request(final String permission, final PermissionAction grantedAction, final PermissionAction deniedAction)
	{
		request(permission, new PermissionCallback()
		{
			@Override
			public void onPermissionGranted()
			{
				grantedAction.run();
			}


			@Override
			public void onPermissionDenied()
			{
				deniedAction.run();
			}


			@Override
			public void onPermissionBlocked() {}
		});
	}


	public void request(final String permission, final PermissionAction grantedAction, final PermissionAction deniedAction, final PermissionAction blockedAction)
	{
		request(permission, new PermissionCallback()
		{
			@Override
			public void onPermissionGranted()
			{
				grantedAction.run();
			}


			@Override
			public void onPermissionDenied()
			{
				deniedAction.run();
			}


			@Override
			public void onPermissionBlocked()
			{
				blockedAction.run();
			}
		});
	}


	public void request(String permission, PermissionCallback permissionCallback)
	{
		if(check(permission))
		{
			permissionCallback.onPermissionGranted();
		}
		else
		{
			if(shouldShowRationale(permission))
			{
				mRationaleHandler.showRationale(
						getRootView(),
						mRationaleHandler.getRationaleMessage(permission),
						createRationaleConfirmAction(permission, permissionCallback));
			}
			else
			{
				requestPermission(permission, permissionCallback);
			}
		}
	}


	public void request(String[] permissions, PermissionsCallback permissionsCallback)
	{
		PermissionsResult permissionsResult = check(permissions);

		if(permissionsResult.isGranted())
		{
			permissionsCallback.onPermissionsResult(permissionsResult);
		}
		else
		{
			boolean rationaleShown = false;
			for(String permission : permissions)
			{
				if(shouldShowRationale(permission))
				{
					mRationaleHandler.showRationale(
							getRootView(),
							mRationaleHandler.getRationaleMessage(permission),
							createRationaleConfirmAction(permissionsResult.getDeniedPermissions(), permissionsCallback));
					rationaleShown = true;
					break;
				}
			}

			if(!rationaleShown)
			{
				requestPermissions(permissionsResult.getDeniedPermissions(), permissionsCallback);
			}
		}
	}


	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(mPermissionCallback != null && requestCode == REQUEST_CODE_PERMISSION)
		{
			if(grantResults.length != 0)
			{
				if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					mPermissionCallback.onPermissionGranted();
				}
				else
				{
					if(shouldShowRationale(permissions[0]))
					{
						mPermissionCallback.onPermissionDenied();
					}
					else
					{
						mPermissionCallback.onPermissionBlocked();
					}
				}
			}
			mPermissionCallback = null;
		}

		else if(mPermissionsCallback != null && requestCode == REQUEST_CODE_PERMISSIONS)
		{
			mPermissionsCallback.onPermissionsResult(new PermissionsResult(permissions, grantResults));
			mPermissionsCallback = null;
		}
	}


	private Context getContext()
	{
		if(mActivity != null)
		{
			return mActivity.getApplicationContext();
		}
		else
		{
			return mFragment.getActivity().getApplicationContext();
		}
	}


	private View getRootView()
	{
		if(mActivity != null)
		{
			return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
		}
		else
		{
			return mFragment.getView();
		}
	}


	private boolean shouldShowRationale(String permission)
	{
		if(mActivity != null)
		{
			return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
		}
		else
		{
			return mFragment.shouldShowRequestPermissionRationale(permission);
		}
	}


	private void requestPermission(String permission, PermissionCallback permissionCallback)
	{
		mPermissionCallback = permissionCallback;

		if(mActivity != null)
		{
			ActivityCompat.requestPermissions(mActivity, new String[]{permission}, REQUEST_CODE_PERMISSION);
		}
		else
		{
			mFragment.requestPermissions(new String[]{permission}, REQUEST_CODE_PERMISSION);
		}
	}


	private void requestPermissions(String[] permissions, PermissionsCallback permissionsCallback)
	{
		mPermissionsCallback = permissionsCallback;

		if(mActivity != null)
		{
			ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSIONS);
		}
		else
		{
			mFragment.requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
		}
	}


	private PermissionAction createRationaleConfirmAction(final String permission, final PermissionCallback permissionCallback)
	{
		return new PermissionAction()
		{
			@Override
			public void run()
			{
				requestPermission(permission, permissionCallback);
			}
		};
	}


	private PermissionAction createRationaleConfirmAction(final String[] permissions, final PermissionsCallback permissionsCallback)
	{
		return new PermissionAction()
		{
			@Override
			public void run()
			{
				requestPermissions(permissions, permissionsCallback);
			}
		};
	}


	public static class PermissionsResult
	{
		private final Map<String, Boolean> mResultMap;


		public PermissionsResult(Map<String, Boolean> resultMap)
		{
			mResultMap = resultMap;
		}


		public PermissionsResult(String[] permissions, int[] grantResults)
		{
			mResultMap = new HashMap<>();
			for(int i = 0; i < permissions.length; i++)
			{
				mResultMap.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
			}
		}


		public Map<String, Boolean> getResultMap()
		{
			return mResultMap;
		}


		public boolean isGranted()
		{
			return !mResultMap.values().contains(false);
		}


		public boolean isGranted(String... permissions)
		{
			for(String permission : permissions)
			{
				if(!mResultMap.get(permission)) return false;
			}
			return true;
		}


		private String[] getDeniedPermissions()
		{
			Set<String> denied = new HashSet<>();
			for(Map.Entry<String, Boolean> entry : mResultMap.entrySet())
			{
				if(!entry.getValue())
				{
					denied.add(entry.getKey());
				}
			}
			return denied.toArray(new String[denied.size()]);
		}
	}
}
