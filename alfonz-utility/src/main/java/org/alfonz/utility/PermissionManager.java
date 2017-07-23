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

	private RationaleHandler mRationaleHandler;
	private PermissionCallback mPermissionCallback;
	private PermissionsCallback mPermissionsCallback;


	public interface RationaleHandler
	{
		String getRationaleMessage(String permission);
		void showRationale(View rootView, String rationaleMessage, ConfirmAction confirmAction);
	}


	public interface ConfirmAction
	{
		void run();
	}


	public interface PermissionAction<T>
	{
		void run(@NonNull T requestable);
	}


	public interface PermissionCallback<T>
	{
		void onPermissionGranted(@NonNull T requestable);
		void onPermissionDenied(@NonNull T requestable);
		void onPermissionBlocked(@NonNull T requestable);
	}


	public interface PermissionsCallback<T>
	{
		void onPermissionsResult(@NonNull T requestable, @NonNull PermissionsResult permissionsResult);
	}


	private interface PermissionRequestable<T>
	{
		T getRequestable();
		Context getContext();
		View getRootView();
		boolean shouldShowRationale(String permission);
		void requestPermission(String permission);
		void requestPermissions(String[] permissions);
	}


	public PermissionManager(@NonNull RationaleHandler rationaleHandler)
	{
		mRationaleHandler = rationaleHandler;
	}


	public boolean check(Activity activity, String permission)
	{
		return check(new ActivityRequestable<>(activity), permission);
	}


	public boolean check(Fragment fragment, String permission)
	{
		return check(new FragmentRequestable<>(fragment), permission);
	}


	public PermissionsResult check(Activity activity, String... permissions)
	{
		return check(new ActivityRequestable<>(activity), permissions);
	}


	public PermissionsResult check(Fragment fragment, String... permissions)
	{
		return check(new FragmentRequestable<>(fragment), permissions);
	}


	public <T extends Activity> void request(T activity, String permission, PermissionAction<T> grantedAction)
	{
		request(new ActivityRequestable<>(activity), permission, grantedAction);
	}


	public <T extends Fragment> void request(T fragment, String permission, PermissionAction<T> grantedAction)
	{
		request(new FragmentRequestable<>(fragment), permission, grantedAction);
	}


	public <T extends Activity> void request(T activity, String permission, PermissionAction<T> grantedAction, PermissionAction<T> deniedAction)
	{
		request(new ActivityRequestable<>(activity), permission, grantedAction, deniedAction);
	}


	public <T extends Fragment> void request(T fragment, String permission, PermissionAction<T> grantedAction, PermissionAction<T> deniedAction)
	{
		request(new FragmentRequestable<>(fragment), permission, grantedAction, deniedAction);
	}


	public <T extends Activity> void request(T activity, String permission, PermissionAction<T> grantedAction, PermissionAction<T> deniedAction, PermissionAction<T> blockedAction)
	{
		request(new ActivityRequestable<>(activity), permission, grantedAction, deniedAction, blockedAction);
	}


	public <T extends Fragment> void request(T fragment, String permission, PermissionAction<T> grantedAction, PermissionAction<T> deniedAction, PermissionAction<T> blockedAction)
	{
		request(new FragmentRequestable<>(fragment), permission, grantedAction, deniedAction, blockedAction);
	}


	public <T extends Activity> void request(T activity, String permission, PermissionCallback<T> permissionCallback)
	{
		request(new ActivityRequestable<>(activity), permission, permissionCallback);
	}


	public <T extends Fragment> void request(T fragment, String permission, PermissionCallback<T> permissionCallback)
	{
		request(new FragmentRequestable<>(fragment), permission, permissionCallback);
	}


	public <T extends Activity> void request(T activity, String[] permissions, PermissionsCallback<T> permissionsCallback)
	{
		request(new ActivityRequestable<>(activity), permissions, permissionsCallback);
	}


	public <T extends Fragment> void request(T fragment, String[] permissions, PermissionsCallback<T> permissionsCallback)
	{
		request(new FragmentRequestable<>(fragment), permissions, permissionsCallback);
	}


	public <T extends Activity> void onRequestPermissionsResult(T activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		onRequestPermissionsResult(new ActivityRequestable<>(activity), requestCode, permissions, grantResults);
	}


	public <T extends Fragment> void onRequestPermissionsResult(T fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		onRequestPermissionsResult(new FragmentRequestable<>(fragment), requestCode, permissions, grantResults);
	}


	private boolean check(PermissionRequestable permissionRequestable, String permission)
	{
		int result = ContextCompat.checkSelfPermission(permissionRequestable.getContext(), permission);
		return result == PackageManager.PERMISSION_GRANTED;
	}


	private PermissionsResult check(PermissionRequestable permissionRequestable, String... permissions)
	{
		Map<String, Boolean> resultMap = new HashMap<>();
		for(String permission : permissions)
		{
			int result = ContextCompat.checkSelfPermission(permissionRequestable.getContext(), permission);
			resultMap.put(permission, result == PackageManager.PERMISSION_GRANTED);
		}
		return new PermissionsResult(resultMap);
	}


	private <T> void request(PermissionRequestable<T> permissionRequestable, String permission, final PermissionAction<T> grantedAction)
	{
		request(permissionRequestable, permission, new PermissionCallback<T>()
		{
			@Override
			public void onPermissionGranted(@NonNull T requestable)
			{
				grantedAction.run(requestable);
			}


			@Override
			public void onPermissionDenied(@NonNull T requestable) {}


			@Override
			public void onPermissionBlocked(@NonNull T requestable) {}
		});
	}


	private <T> void request(PermissionRequestable<T> permissionRequestable, String permission, final PermissionAction<T> grantedAction, final PermissionAction<T> deniedAction)
	{
		request(permissionRequestable, permission, new PermissionCallback<T>()
		{
			@Override
			public void onPermissionGranted(@NonNull T requestable)
			{
				grantedAction.run(requestable);
			}


			@Override
			public void onPermissionDenied(@NonNull T requestable)
			{
				deniedAction.run(requestable);
			}


			@Override
			public void onPermissionBlocked(@NonNull T requestable) {}
		});
	}


	private <T> void request(PermissionRequestable<T> permissionRequestable, String permission, final PermissionAction<T> grantedAction, final PermissionAction<T> deniedAction, final PermissionAction<T> blockedAction)
	{
		request(permissionRequestable, permission, new PermissionCallback<T>()
		{
			@Override
			public void onPermissionGranted(@NonNull T requestable)
			{
				grantedAction.run(requestable);
			}


			@Override
			public void onPermissionDenied(@NonNull T requestable)
			{
				deniedAction.run(requestable);
			}


			@Override
			public void onPermissionBlocked(@NonNull T requestable)
			{
				blockedAction.run(requestable);
			}
		});
	}


	private <T> void request(PermissionRequestable<T> permissionRequestable, String permission, PermissionCallback<T> permissionCallback)
	{
		if(check(permissionRequestable, permission))
		{
			permissionCallback.onPermissionGranted(permissionRequestable.getRequestable());
		}
		else
		{
			if(permissionRequestable.shouldShowRationale(permission))
			{
				mRationaleHandler.showRationale(
						permissionRequestable.getRootView(),
						mRationaleHandler.getRationaleMessage(permission),
						createRationaleConfirmAction(permissionRequestable, permission, permissionCallback));
			}
			else
			{
				mPermissionCallback = permissionCallback;
				permissionRequestable.requestPermission(permission);
			}
		}
	}


	private <T> void request(PermissionRequestable<T> permissionRequestable, String[] permissions, PermissionsCallback<T> permissionsCallback)
	{
		PermissionsResult permissionsResult = check(permissionRequestable, permissions);

		if(permissionsResult.isGranted())
		{
			permissionsCallback.onPermissionsResult(permissionRequestable.getRequestable(), permissionsResult);
		}
		else
		{
			boolean rationaleShown = false;
			for(String permission : permissions)
			{
				if(permissionRequestable.shouldShowRationale(permission))
				{
					mRationaleHandler.showRationale(
							permissionRequestable.getRootView(),
							mRationaleHandler.getRationaleMessage(permission),
							createRationaleConfirmAction(permissionRequestable, permissionsResult.getDeniedPermissions(), permissionsCallback));
					rationaleShown = true;
					break;
				}
			}

			if(!rationaleShown)
			{
				mPermissionsCallback = permissionsCallback;
				permissionRequestable.requestPermissions(permissionsResult.getDeniedPermissions());
			}
		}
	}


	@SuppressWarnings("unchecked")
	private <T> void onRequestPermissionsResult(PermissionRequestable<T> permissionRequestable, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		if(mPermissionCallback != null && requestCode == REQUEST_CODE_PERMISSION)
		{
			if(grantResults.length != 0)
			{
				if(grantResults[0] == PackageManager.PERMISSION_GRANTED)
				{
					mPermissionCallback.onPermissionGranted(permissionRequestable.getRequestable());
				}
				else
				{
					if(permissionRequestable.shouldShowRationale(permissions[0]))
					{
						mPermissionCallback.onPermissionDenied(permissionRequestable.getRequestable());
					}
					else
					{
						mPermissionCallback.onPermissionBlocked(permissionRequestable.getRequestable());
					}
				}
			}
			mPermissionCallback = null;
		}

		else if(mPermissionsCallback != null && requestCode == REQUEST_CODE_PERMISSIONS)
		{
			mPermissionsCallback.onPermissionsResult(permissionRequestable.getRequestable(), new PermissionsResult(permissions, grantResults));
			mPermissionsCallback = null;
		}
	}


	private ConfirmAction createRationaleConfirmAction(final PermissionRequestable permissionRequestable, final String permission, final PermissionCallback permissionCallback)
	{
		return new ConfirmAction()
		{
			@Override
			public void run()
			{
				mPermissionCallback = permissionCallback;
				permissionRequestable.requestPermission(permission);
			}
		};
	}


	private ConfirmAction createRationaleConfirmAction(final PermissionRequestable permissionRequestable, final String[] permissions, final PermissionsCallback permissionsCallback)
	{
		return new ConfirmAction()
		{
			@Override
			public void run()
			{
				mPermissionsCallback = permissionsCallback;
				permissionRequestable.requestPermissions(permissions);
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


	private static class ActivityRequestable<T extends Activity> implements PermissionRequestable<T>
	{
		private T mActivity;


		public ActivityRequestable(T activity)
		{
			mActivity = activity;
		}


		@Override
		public T getRequestable()
		{
			return mActivity;
		}


		@Override
		public Context getContext()
		{
			return mActivity.getApplicationContext();
		}


		@Override
		public View getRootView()
		{
			return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
		}


		@Override
		public boolean shouldShowRationale(String permission)
		{
			return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
		}


		@Override
		public void requestPermission(String permission)
		{
			ActivityCompat.requestPermissions(mActivity, new String[]{permission}, REQUEST_CODE_PERMISSION);
		}


		@Override
		public void requestPermissions(String[] permissions)
		{
			ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSIONS);
		}
	}


	private static class FragmentRequestable<T extends Fragment> implements PermissionRequestable<T>
	{
		private T mFragment;


		public FragmentRequestable(T fragment)
		{
			mFragment = fragment;
		}


		@Override
		public T getRequestable()
		{
			return mFragment;
		}


		@Override
		public Context getContext()
		{
			return mFragment.getActivity().getApplicationContext();
		}


		@Override
		public View getRootView()
		{
			return mFragment.getView();
		}


		@Override
		public boolean shouldShowRationale(String permission)
		{
			return mFragment.shouldShowRequestPermissionRationale(permission);
		}


		@Override
		public void requestPermission(String permission)
		{
			mFragment.requestPermissions(new String[]{permission}, REQUEST_CODE_PERMISSION);
		}


		@Override
		public void requestPermissions(String[] permissions)
		{
			mFragment.requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
		}
	}
}
