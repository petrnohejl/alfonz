package org.alfonz.samples.alfonzutility;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentUtilitySampleBinding;
import org.alfonz.utility.IntentUtility;
import org.alfonz.utility.KeyboardUtility;

public class UtilitySampleFragment extends BaseFragment<UtilitySampleViewModel, FragmentUtilitySampleBinding> implements UtilitySampleView {
	@Override
	public UtilitySampleViewModel setupViewModel() {
		return ViewModelProviders.of(this).get(UtilitySampleViewModel.class);
	}

	@Override
	public FragmentUtilitySampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentUtilitySampleBinding.inflate(inflater);
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getViewModel().performUtilities(getContext());
		getActivity().startService(new Intent(getActivity(), UtilitySampleService.class));
	}

	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		getViewModel().permissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
	}

	@Override
	public void onButtonShowKeyboardClick() {
		performShowKeyboard();
	}

	@Override
	public void onButtonHideKeyboardClick() {
		performHideKeyboard();
	}

	@Override
	public void onButtonStartWebClick() {
		performStartWebActivity();
	}

	@Override
	public void onButtonStartStoreClick() {
		performStartStoreActivity();
	}

	@Override
	public void onButtonStartShareClick() {
		performStartShareActivity();
	}

	@Override
	public void onButtonStartEmailClick() {
		performStartEmailActivity();
	}

	@Override
	public void onButtonStartSmsClick() {
		performStartSmsActivity();
	}

	@Override
	public void onButtonStartCallClick() {
		performStartCallActivity();
	}

	@Override
	public void onButtonStartMapCoordinatesClick() {
		performStartMapCoordinatesActivity();
	}

	@Override
	public void onButtonStartMapSearchClick() {
		performStartMapSearchActivity();
	}

	@Override
	public void onButtonStartNavigationClick() {
		performStartNavigationActivity();
	}

	@Override
	public void onButtonStartCalendarClick() {
		performStartCalendarActivity();
	}

	@Override
	public void onButtonStartNotificationSettingsClick() {
		performStartNotificationSettingsActivity();
	}

	@Override
	public void onButtonIsCallableClick() {
		performIsCallable();
	}

	@Override
	public void onButtonPermissionClick() {
		performPermissionRequest();
	}

	@Override
	public void onButtonPermissionsClick() {
		performPermissionsRequest();
	}

	@Override
	public void onButtonLogcatClick() {
		getViewModel().performLogcat();
	}

	@Override
	public void onButtonDownloadClick() {
		getViewModel().permissionManager.request(
				this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				requestable -> requestable.getViewModel().performDownloadUtility());
	}

	@Override
	public void onButtonZipClick() {
		getViewModel().permissionManager.request(
				this,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				requestable -> requestable.getViewModel().performZipUtility());
	}

	private void performShowKeyboard() {
		KeyboardUtility.showKeyboard(getBinding().utilitySampleEdittext);
	}

	private void performHideKeyboard() {
		KeyboardUtility.hideKeyboard(getBinding().utilitySampleEdittext);
	}

	private void performStartWebActivity() {
		IntentUtility.startWebActivity(getContext(), "http://alfonz.org");
	}

	private void performStartStoreActivity() {
		IntentUtility.startStoreActivity(getContext());
	}

	private void performStartShareActivity() {
		IntentUtility.startShareActivity(getContext(), "Alfonz", "Hello world!", "Share Alfonz");
	}

	private void performStartEmailActivity() {
		IntentUtility.startEmailActivity(getContext(), "hello@world.com", "Alfonz", "Hello world!");
	}

	private void performStartSmsActivity() {
		IntentUtility.startSmsActivity(getContext(), "123456789", "Hello world!");
	}

	private void performStartCallActivity() {
		IntentUtility.startCallActivity(getContext(), "123456789");
	}

	private void performStartMapCoordinatesActivity() {
		IntentUtility.startMapCoordinatesActivity(getContext(), 49.194793, 16.608594, 18, "Brno");
	}

	private void performStartMapSearchActivity() {
		IntentUtility.startMapSearchActivity(getContext(), "Brno");
	}

	private void performStartNavigationActivity() {
		IntentUtility.startNavigationActivity(getContext(), 49.194793, 16.608594);
	}

	private void performStartCalendarActivity() {
		IntentUtility.startCalendarActivity(getContext(), "Alfonz", "Hello world!", 1956567600000L, 1956571200000L);
	}

	private void performStartNotificationSettingsActivity() {
		IntentUtility.startNotificationSettingsActivity(getContext());
	}

	private void performIsCallable() {
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/AndroidOfficial"));
		boolean callable = IntentUtility.isCallable(getContext(), intent);
		showToast(String.valueOf(callable));
	}

	private void performPermissionRequest() {
		getViewModel().permissionManager.request(
				this,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				requestable -> requestable.showToast("Granted"),
				requestable -> requestable.showToast("Denied"),
				requestable -> requestable.showToast("Blocked"));
	}

	private void performPermissionsRequest() {
		String[] permissions = new String[]{
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.ACCESS_FINE_LOCATION};

		getViewModel().permissionManager.request(
				this,
				permissions,
				(requestable, permissionsResult) -> requestable.showToast(String.format("Granted: %b", permissionsResult.isGranted())));
	}
}
