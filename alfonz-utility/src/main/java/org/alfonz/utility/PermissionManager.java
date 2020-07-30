package org.alfonz.utility;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.collection.ArrayMap;
import androidx.collection.ArraySet;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import java.util.Map;
import java.util.Set;

public class PermissionManager {
	private static final int REQUEST_CODE_PERMISSION = 1;
	private static final int REQUEST_CODE_PERMISSIONS = 2;

	private RationaleHandler mRationaleHandler;
	private PermissionCallback mPermissionCallback;
	private PermissionsCallback mPermissionsCallback;

	public interface RationaleHandler {
		String getRationaleMessage(@NonNull String permission);
		void showRationale(@NonNull View rootView, @NonNull String rationaleMessage, @NonNull ConfirmAction confirmAction);
	}

	public interface ConfirmAction {
		void run();
	}

	public interface PermissionAction<T> {
		void run(@NonNull T requestable);
	}

	public interface PermissionCallback<T> {
		void onPermissionGranted(@NonNull T requestable);
		void onPermissionDenied(@NonNull T requestable);
		void onPermissionBlocked(@NonNull T requestable);
	}

	public interface PermissionsCallback<T> {
		void onPermissionsResult(@NonNull T requestable, @NonNull PermissionsResult permissionsResult);
	}

	private interface PermissionRequestable<T> {
		T getRequestable();
		Context getContext();
		View getRootView();
		boolean shouldShowRationale(String permission);
		void requestPermission(String permission);
		void requestPermissions(String[] permissions);
	}

	public PermissionManager(@NonNull RationaleHandler rationaleHandler) {
		mRationaleHandler = rationaleHandler;
	}

	public static boolean check(@NonNull Context context, @NonNull String permission) {
		int result = ContextCompat.checkSelfPermission(context, permission);
		return result == PackageManager.PERMISSION_GRANTED;
	}

	@NonNull
	public static PermissionsResult check(@NonNull Context context, @NonNull String... permissions) {
		Map<String, Boolean> resultMap = new ArrayMap<>();
		for (String permission : permissions) {
			int result = ContextCompat.checkSelfPermission(context, permission);
			resultMap.put(permission, result == PackageManager.PERMISSION_GRANTED);
		}
		return new PermissionsResult(resultMap);
	}

	public <T extends Activity> void request(@NonNull T activity, @NonNull String permission, @NonNull PermissionAction<T> grantedAction) {
		request(new ActivityRequestable<>(activity), permission, grantedAction, null, null);
	}

	public <T extends Fragment> void request(@NonNull T fragment, @NonNull String permission, @NonNull PermissionAction<T> grantedAction) {
		request(new FragmentRequestable<>(fragment), permission, grantedAction, null, null);
	}

	public <T extends Activity> void request(@NonNull T activity, @NonNull String permission, @NonNull PermissionAction<T> grantedAction, @NonNull PermissionAction<T> deniedAction) {
		request(new ActivityRequestable<>(activity), permission, grantedAction, deniedAction, null);
	}

	public <T extends Fragment> void request(@NonNull T fragment, @NonNull String permission, @NonNull PermissionAction<T> grantedAction, @NonNull PermissionAction<T> deniedAction) {
		request(new FragmentRequestable<>(fragment), permission, grantedAction, deniedAction, null);
	}

	public <T extends Activity> void request(@NonNull T activity, @NonNull String permission, @NonNull PermissionAction<T> grantedAction, @NonNull PermissionAction<T> deniedAction, @NonNull PermissionAction<T> blockedAction) {
		request(new ActivityRequestable<>(activity), permission, grantedAction, deniedAction, blockedAction);
	}

	public <T extends Fragment> void request(@NonNull T fragment, @NonNull String permission, @NonNull PermissionAction<T> grantedAction, @NonNull PermissionAction<T> deniedAction, @NonNull PermissionAction<T> blockedAction) {
		request(new FragmentRequestable<>(fragment), permission, grantedAction, deniedAction, blockedAction);
	}

	public <T extends Activity> void request(@NonNull T activity, @NonNull String permission, @NonNull PermissionCallback<T> permissionCallback) {
		request(new ActivityRequestable<>(activity), permission, permissionCallback);
	}

	public <T extends Fragment> void request(@NonNull T fragment, @NonNull String permission, @NonNull PermissionCallback<T> permissionCallback) {
		request(new FragmentRequestable<>(fragment), permission, permissionCallback);
	}

	public <T extends Activity> void request(@NonNull T activity, @NonNull String[] permissions, @NonNull PermissionsCallback<T> permissionsCallback) {
		request(new ActivityRequestable<>(activity), permissions, permissionsCallback);
	}

	public <T extends Fragment> void request(@NonNull T fragment, @NonNull String[] permissions, @NonNull PermissionsCallback<T> permissionsCallback) {
		request(new FragmentRequestable<>(fragment), permissions, permissionsCallback);
	}

	public <T extends Activity> void onRequestPermissionsResult(@NonNull T activity, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		onRequestPermissionsResult(new ActivityRequestable<>(activity), requestCode, permissions, grantResults);
	}

	public <T extends Fragment> void onRequestPermissionsResult(@NonNull T fragment, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		onRequestPermissionsResult(new FragmentRequestable<>(fragment), requestCode, permissions, grantResults);
	}

	private <T> void request(@NonNull PermissionRequestable<T> permissionRequestable, @NonNull String permission, @Nullable final PermissionAction<T> grantedAction, @Nullable final PermissionAction<T> deniedAction, @Nullable final PermissionAction<T> blockedAction) {
		request(permissionRequestable, permission, new PermissionCallback<T>() {
			@Override
			public void onPermissionGranted(@NonNull T requestable) {
				if (grantedAction != null) grantedAction.run(requestable);
			}

			@Override
			public void onPermissionDenied(@NonNull T requestable) {
				if (deniedAction != null) deniedAction.run(requestable);
			}

			@Override
			public void onPermissionBlocked(@NonNull T requestable) {
				if (blockedAction != null) blockedAction.run(requestable);
			}
		});
	}

	private <T> void request(@NonNull PermissionRequestable<T> permissionRequestable, @NonNull String permission, @NonNull PermissionCallback<T> permissionCallback) {
		if (check(permissionRequestable.getContext(), permission)) {
			permissionCallback.onPermissionGranted(permissionRequestable.getRequestable());
		} else {
			if (permissionRequestable.shouldShowRationale(permission)) {
				mRationaleHandler.showRationale(
						permissionRequestable.getRootView(),
						mRationaleHandler.getRationaleMessage(permission),
						createRationaleConfirmAction(permissionRequestable, permission, permissionCallback));
			} else {
				mPermissionCallback = permissionCallback;
				permissionRequestable.requestPermission(permission);
			}
		}
	}

	private <T> void request(@NonNull PermissionRequestable<T> permissionRequestable, @NonNull String[] permissions, @NonNull PermissionsCallback<T> permissionsCallback) {
		PermissionsResult permissionsResult = check(permissionRequestable.getContext(), permissions);

		if (permissionsResult.isGranted()) {
			permissionsCallback.onPermissionsResult(permissionRequestable.getRequestable(), permissionsResult);
		} else {
			boolean rationaleShown = false;
			for (String permission : permissions) {
				if (permissionRequestable.shouldShowRationale(permission)) {
					mRationaleHandler.showRationale(
							permissionRequestable.getRootView(),
							mRationaleHandler.getRationaleMessage(permission),
							createRationaleConfirmAction(permissionRequestable, permissionsResult.getDeniedPermissions(), permissionsCallback));
					rationaleShown = true;
					break;
				}
			}

			if (!rationaleShown) {
				mPermissionsCallback = permissionsCallback;
				permissionRequestable.requestPermissions(permissionsResult.getDeniedPermissions());
			}
		}
	}

	@SuppressWarnings("unchecked")
	private <T> void onRequestPermissionsResult(@NonNull PermissionRequestable<T> permissionRequestable, int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		if (mPermissionCallback != null && requestCode == REQUEST_CODE_PERMISSION) {
			if (grantResults.length != 0) {
				if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
					mPermissionCallback.onPermissionGranted(permissionRequestable.getRequestable());
				} else {
					if (permissionRequestable.shouldShowRationale(permissions[0])) {
						mPermissionCallback.onPermissionDenied(permissionRequestable.getRequestable());
					} else {
						mPermissionCallback.onPermissionBlocked(permissionRequestable.getRequestable());
					}
				}
			}
			mPermissionCallback = null;
		} else if (mPermissionsCallback != null && requestCode == REQUEST_CODE_PERMISSIONS) {
			mPermissionsCallback.onPermissionsResult(permissionRequestable.getRequestable(), new PermissionsResult(permissions, grantResults));
			mPermissionsCallback = null;
		}
	}

	@NonNull
	private ConfirmAction createRationaleConfirmAction(@NonNull final PermissionRequestable<?> permissionRequestable, @NonNull final String permission, @NonNull final PermissionCallback<?> permissionCallback) {
		return new ConfirmAction() {
			@Override
			public void run() {
				mPermissionCallback = permissionCallback;
				permissionRequestable.requestPermission(permission);
			}
		};
	}

	@NonNull
	private ConfirmAction createRationaleConfirmAction(@NonNull final PermissionRequestable<?> permissionRequestable, @NonNull final String[] permissions, @NonNull final PermissionsCallback<?> permissionsCallback) {
		return new ConfirmAction() {
			@Override
			public void run() {
				mPermissionsCallback = permissionsCallback;
				permissionRequestable.requestPermissions(permissions);
			}
		};
	}

	public static class PermissionsResult {
		private final Map<String, Boolean> mResultMap;

		public PermissionsResult(@NonNull Map<String, Boolean> resultMap) {
			mResultMap = resultMap;
		}

		public PermissionsResult(@NonNull String[] permissions, @NonNull int[] grantResults) {
			mResultMap = new ArrayMap<>();
			for (int i = 0; i < permissions.length; i++) {
				mResultMap.put(permissions[i], grantResults[i] == PackageManager.PERMISSION_GRANTED);
			}
		}

		public Map<String, Boolean> getResultMap() {
			return mResultMap;
		}

		public boolean isGranted() {
			return !mResultMap.containsValue(false);
		}

		public boolean isGranted(@NonNull String... permissions) {
			for (String permission : permissions) {
				if (!mResultMap.get(permission)) return false;
			}
			return true;
		}

		private String[] getDeniedPermissions() {
			Set<String> denied = new ArraySet<>();
			for (Map.Entry<String, Boolean> entry : mResultMap.entrySet()) {
				if (!entry.getValue()) {
					denied.add(entry.getKey());
				}
			}
			return denied.toArray(new String[denied.size()]);
		}
	}

	private static class ActivityRequestable<T extends Activity> implements PermissionRequestable<T> {
		private T mActivity;

		public ActivityRequestable(@NonNull T activity) {
			mActivity = activity;
		}

		@Override
		public T getRequestable() {
			return mActivity;
		}

		@Override
		public Context getContext() {
			return mActivity.getApplicationContext();
		}

		@Override
		public View getRootView() {
			return mActivity.getWindow().getDecorView().findViewById(android.R.id.content);
		}

		@Override
		public boolean shouldShowRationale(String permission) {
			return ActivityCompat.shouldShowRequestPermissionRationale(mActivity, permission);
		}

		@Override
		public void requestPermission(String permission) {
			ActivityCompat.requestPermissions(mActivity, new String[]{permission}, REQUEST_CODE_PERMISSION);
		}

		@Override
		public void requestPermissions(String[] permissions) {
			ActivityCompat.requestPermissions(mActivity, permissions, REQUEST_CODE_PERMISSIONS);
		}
	}

	private static class FragmentRequestable<T extends Fragment> implements PermissionRequestable<T> {
		private T mFragment;

		public FragmentRequestable(@NonNull T fragment) {
			mFragment = fragment;
		}

		@Override
		public T getRequestable() {
			return mFragment;
		}

		@Override
		public Context getContext() {
			return mFragment.getActivity().getApplicationContext();
		}

		@Override
		public View getRootView() {
			return mFragment.getView();
		}

		@Override
		public boolean shouldShowRationale(String permission) {
			return mFragment.shouldShowRequestPermissionRationale(permission);
		}

		@Override
		public void requestPermission(String permission) {
			mFragment.requestPermissions(new String[]{permission}, REQUEST_CODE_PERMISSION);
		}

		@Override
		public void requestPermissions(String[] permissions) {
			mFragment.requestPermissions(permissions, REQUEST_CODE_PERMISSIONS);
		}
	}
}
