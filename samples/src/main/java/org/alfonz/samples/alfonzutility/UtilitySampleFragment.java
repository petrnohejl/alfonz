package org.alfonz.samples.alfonzutility;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.alfonzutility.utility.PermissionRationaleHandler;
import org.alfonz.samples.databinding.FragmentUtilitySampleBinding;
import org.alfonz.utility.IntentUtility;
import org.alfonz.utility.KeyboardUtility;
import org.alfonz.utility.PermissionManager;


public class UtilitySampleFragment extends BaseFragment<UtilitySampleView, UtilitySampleViewModel, FragmentUtilitySampleBinding> implements UtilitySampleView
{
	private PermissionManager mPermissionManager = new PermissionManager(this, new PermissionRationaleHandler());


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
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
	public void onButtonStartWebClick()
	{
		performStartWebActivity();
	}


	@Override
	public void onButtonStartStoreClick()
	{
		performStartStoreActivity();
	}


	@Override
	public void onButtonStartShareClick()
	{
		performStartShareActivity();
	}


	@Override
	public void onButtonStartEmailClick()
	{
		performStartEmailActivity();
	}


	@Override
	public void onButtonStartSmsClick()
	{
		performStartSmsActivity();
	}


	@Override
	public void onButtonStartCallClick()
	{
		performStartCallActivity();
	}


	@Override
	public void onButtonStartMapCoordinatesClick()
	{
		performStartMapCoordinatesActivity();
	}


	@Override
	public void onButtonStartMapSearchClick()
	{
		performStartMapSearchActivity();
	}


	@Override
	public void onButtonStartNavigationClick()
	{
		performStartNavigationActivity();
	}


	@Override
	public void onButtonStartCalendarClick()
	{
		performStartCalendarActivity();
	}


	@Override
	public void onButtonIsCallableClick()
	{
		performIsCallable();
	}


	@Override
	public void onButtonPermissionClick()
	{
		performPermissionRequest();
	}


	@Override
	public void onButtonPermissionsClick()
	{
		performPermissionsRequest();
	}


	@Override
	public void onButtonLogcatClick()
	{
		getViewModel().performLogcat();
	}


	@Override
	public void onButtonDownloadClick()
	{
		mPermissionManager.request(
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				() -> getViewModel().performDownloadUtility());
	}


	@Override
	public void onButtonZipClick()
	{
		mPermissionManager.request(
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				() -> getViewModel().performZipUtility());
	}


	private void performShowKeyboard()
	{
		KeyboardUtility.showKeyboard(getBinding().fragmentUtilitySampleEdittext);
	}


	private void performHideKeyboard()
	{
		KeyboardUtility.hideKeyboard(getBinding().fragmentUtilitySampleEdittext);
	}


	private void performStartWebActivity()
	{
		IntentUtility.startWebActivity(getContext(), "http://alfonz.org");
	}


	private void performStartStoreActivity()
	{
		IntentUtility.startStoreActivity(getContext());
	}


	private void performStartShareActivity()
	{
		IntentUtility.startShareActivity(getContext(), "Alfonz", "Hello world!", "Share Alfonz");
	}


	private void performStartEmailActivity()
	{
		IntentUtility.startEmailActivity(getContext(), "hello@world.com", "Alfonz", "Hello world!");
	}


	private void performStartSmsActivity()
	{
		IntentUtility.startSmsActivity(getContext(), "123456789", "Hello world!");
	}


	private void performStartCallActivity()
	{
		IntentUtility.startCallActivity(getContext(), "123456789");
	}


	private void performStartMapCoordinatesActivity()
	{
		IntentUtility.startMapCoordinatesActivity(getContext(), 49.194793, 16.608594, 18, "Brno");
	}


	private void performStartMapSearchActivity()
	{
		IntentUtility.startMapSearchActivity(getContext(), "Brno");
	}


	private void performStartNavigationActivity()
	{
		IntentUtility.startNavigationActivity(getContext(), 49.194793, 16.608594);
	}


	private void performStartCalendarActivity()
	{
		IntentUtility.startCalendarActivity(getContext(), "Alfonz", "Hello world!", 1956567600000L, 1956571200000L);
	}


	private void performIsCallable()
	{
		Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/AndroidOfficial"));
		boolean callable = IntentUtility.isCallable(getContext(), intent);
		showToast(String.valueOf(callable));
	}


	private void performPermissionRequest()
	{
		mPermissionManager.request(
				Manifest.permission.READ_EXTERNAL_STORAGE,
				() -> showToast("Granted"),
				() -> showToast("Denied"),
				() -> showToast("Blocked"));
	}


	private void performPermissionsRequest()
	{
		String[] permissions = new String[]{
				Manifest.permission.READ_EXTERNAL_STORAGE,
				Manifest.permission.WRITE_EXTERNAL_STORAGE,
				Manifest.permission.ACCESS_COARSE_LOCATION,
				Manifest.permission.ACCESS_FINE_LOCATION};

		mPermissionManager.request(
				permissions,
				permissionsResult -> showToast(String.format("Granted: %b", permissionsResult.isGranted())));
	}
}
