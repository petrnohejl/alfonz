package org.alfonz.samples.alfonzutility;

import android.content.Context;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseViewModel;
import org.alfonz.utility.ContentUtility;
import org.alfonz.utility.DateConvertor;
import org.alfonz.utility.DeviceUuidFactory;
import org.alfonz.utility.DimensionUtility;
import org.alfonz.utility.DownloadUtility;
import org.alfonz.utility.HashUtility;
import org.alfonz.utility.Logcat;
import org.alfonz.utility.NetworkUtility;
import org.alfonz.utility.ResourcesUtility;
import org.alfonz.utility.ServiceUtility;
import org.alfonz.utility.StorageUtility;
import org.alfonz.utility.StringConvertor;
import org.alfonz.utility.ValidationUtility;
import org.alfonz.utility.VersionUtility;
import org.alfonz.utility.ZipUtility;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Pattern;


public class UtilitySampleViewModel extends BaseViewModel<UtilitySampleView>
{
	private static final String LOG_MESSAGE_CONTENT_UTILITY = "[ContentUtility] uri to path: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_D2S = "[DateConvertor] date to str: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_S2D = "[DateConvertor] str to date: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_D2C = "[DateConvertor] date to cal: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_C2D = "[DateConvertor] cal to date: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_S2C = "[DateConvertor] str to cal: %s";
	private static final String LOG_MESSAGE_DATE_CONVERTOR_C2S = "[DateConvertor] cal to str: %s";
	private static final String LOG_MESSAGE_DEVICE_UUID_FACTORY = "[DeviceUuidFactory] uuid: %s";
	private static final String LOG_MESSAGE_DIMENSION_UTILITY_DP2PX = "[DimensionUtility] dp to px: %s";
	private static final String LOG_MESSAGE_DIMENSION_UTILITY_SP2PX = "[DimensionUtility] sp to px: %s";
	private static final String LOG_MESSAGE_DIMENSION_UTILITY_PX2DP = "[DimensionUtility] px to dp: %s";
	private static final String LOG_MESSAGE_DIMENSION_UTILITY_PX2SP = "[DimensionUtility] px to sp: %s";
	private static final String LOG_MESSAGE_HASH_UTILITY_MD5 = "[HashUtility] md5: %s";
	private static final String LOG_MESSAGE_HASH_UTILITY_SHA1 = "[HashUtility] sha1: %s";
	private static final String LOG_MESSAGE_NETWORK_UTILITY_ONLINE = "[NetworkUtility] online: %s";
	private static final String LOG_MESSAGE_NETWORK_UTILITY_TYPE = "[NetworkUtility] type: %s";
	private static final String LOG_MESSAGE_RESOURCES_UTILITY_VALUE = "[ResourcesUtility] value: %s";
	private static final String LOG_MESSAGE_RESOURCES_UTILITY_COLOR = "[ResourcesUtility] color: %s";
	private static final String LOG_MESSAGE_RESOURCES_UTILITY_DIMEN = "[ResourcesUtility] dimen: %s";
	private static final String LOG_MESSAGE_RESOURCES_UTILITY_DIMEN_PIXEL = "[ResourcesUtility] dimen pixel: %s";
	private static final String LOG_MESSAGE_RESOURCES_UTILITY_DRAWABLE = "[ResourcesUtility] drawable: %s";
	private static final String LOG_MESSAGE_SERVICE_UTILITY = "[ServiceUtility] running: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_AVAILABLE = "[StorageUtility] available: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_WRITABLE = "[StorageUtility] writable: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_STORAGE_DIR = "[StorageUtility] storage dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_PIC_DIR = "[StorageUtility] pic dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_SEC_DIR = "[StorageUtility] sec dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_PIC_DEC_DIR = "[StorageUtility] pic sec dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_CACHE_DIR = "[StorageUtility] cache dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_PIC_FILES_DIR = "[StorageUtility] pic files dir: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_FILES = "[StorageUtility] files: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_IMAGES = "[StorageUtility] images: %s";
	private static final String LOG_MESSAGE_STORAGE_UTILITY_MOUNTS = "[StorageUtility] mounts: %s";
	private static final String LOG_MESSAGE_STRING_CONVERTOR = "[StringConvertor] capitalize: %s";
	private static final String LOG_MESSAGE_VALIDATION_UTILITY_EMAIL = "[ValidationUtility] email: %s";
	private static final String LOG_MESSAGE_VALIDATION_UTILITY_DATE = "[ValidationUtility] date: %s";
	private static final String LOG_MESSAGE_VERSION_UTILITY_NAME = "[VersionUtility] name: %s";
	private static final String LOG_MESSAGE_VERSION_UTILITY_CODE = "[VersionUtility] code: %s";
	private static final String LOG_MESSAGE_VERSION_UTILITY_GLES2 = "[VersionUtility] gles2: %s";
	private static final String LOG_MESSAGE_VERSION_UTILITY_COMPARISON = "[VersionUtility] comparison: %s";

	public final ObservableField<String> log = new ObservableField<>();


	@Override
	public void onCreate(@Nullable Bundle arguments, @Nullable Bundle savedInstanceState)
	{
		log.set("");
	}


	@Override
	public void onStart()
	{
		super.onStart();

		// perform utilities
		if(log.get().isEmpty()) performUtilities();
	}


	public void performLogcat()
	{
		Logcat.d("Hello");
		Logcat.e("Hello");
		Logcat.i("Hello");
		Logcat.v("Hello");
		Logcat.w("Hello");
		Logcat.wtf("Hello");
		Logcat.printStackTrace(new RuntimeException());
	}


	public void performDownloadUtility()
	{
		DownloadUtility.downloadFile(getApplicationContext(), "http://github.com/petrnohejl/Alfonz/zipball/master/", "alfonz.zip");
	}


	public void performZipUtility()
	{
		String path = StorageUtility.getStorageDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/";
		String zipname = "alfonz.zip";

		boolean success = ZipUtility.unpackZip(path, zipname);
		getView().showToast("success: " + success);
	}


	private void performUtilities()
	{
		performContentUtility();
		performDateConvertor();
		performDeviceUuidFactory();
		performDimensionUtility();
		performHashUtility();
		performNetworkUtility();
		performResourcesUtility();
		performServiceUtility();
		performStorageUtility();
		performStringConvertor();
		performValidationUtility();
		performVersionUtility();
	}


	private void performContentUtility()
	{
		Uri uri = Uri.fromFile(Environment.getExternalStorageDirectory());
		String path = ContentUtility.getPath(getApplicationContext(), uri);

		log(LOG_MESSAGE_CONTENT_UTILITY, path);
	}


	private void performDateConvertor()
	{
		Date date = new Date();
		Calendar calendar = Calendar.getInstance();
		String string = "2016-12-02 12:00:00";

		String string1 = DateConvertor.dateToString(date, "yyyy-MM-dd HH:mm:ss");
		Date date1 = DateConvertor.stringToDate(string, "yyyy-MM-dd HH:mm:ss");
		Calendar calendar1 = DateConvertor.dateToCalendar(date);
		Date date2 = DateConvertor.calendarToDate(calendar);
		Calendar calendar2 = DateConvertor.stringToCalendar(string, "yyyy-MM-dd HH:mm:ss");
		String string2 = DateConvertor.calendarToString(calendar, "yyyy-MM-dd HH:mm:ss");

		log(LOG_MESSAGE_DATE_CONVERTOR_D2S, string1);
		log(LOG_MESSAGE_DATE_CONVERTOR_S2D, Long.toString(date1.getTime()));
		log(LOG_MESSAGE_DATE_CONVERTOR_D2C, Long.toString(calendar1.getTimeInMillis()));
		log(LOG_MESSAGE_DATE_CONVERTOR_C2D, Long.toString(date2.getTime()));
		log(LOG_MESSAGE_DATE_CONVERTOR_S2C, Long.toString(calendar2.getTimeInMillis()));
		log(LOG_MESSAGE_DATE_CONVERTOR_C2S, string2);
	}


	private void performDeviceUuidFactory()
	{
		DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(getApplicationContext());
		UUID uuid = deviceUuidFactory.getDeviceUUID();

		log(LOG_MESSAGE_DEVICE_UUID_FACTORY, uuid.toString());
	}


	private void performDimensionUtility()
	{
		float px1 = DimensionUtility.dp2px(getApplicationContext(), 100);
		float px2 = DimensionUtility.sp2px(getApplicationContext(), 100);
		float dp = DimensionUtility.px2dp(getApplicationContext(), 100);
		float sp = DimensionUtility.px2sp(getApplicationContext(), 100);

		log(LOG_MESSAGE_DIMENSION_UTILITY_DP2PX, Float.toString(px1));
		log(LOG_MESSAGE_DIMENSION_UTILITY_SP2PX, Float.toString(px2));
		log(LOG_MESSAGE_DIMENSION_UTILITY_PX2DP, Float.toString(dp));
		log(LOG_MESSAGE_DIMENSION_UTILITY_PX2SP, Float.toString(sp));
	}


	private void performHashUtility()
	{
		String md5 = HashUtility.getMd5("Hello world!");
		String sha1 = HashUtility.getSha1("Hello world!");

		log(LOG_MESSAGE_HASH_UTILITY_MD5, md5);
		log(LOG_MESSAGE_HASH_UTILITY_SHA1, sha1);
	}


	private void performNetworkUtility()
	{
		boolean online = NetworkUtility.isOnline(getApplicationContext());
		int networkType = NetworkUtility.getType(getApplicationContext());
		String networkName = NetworkUtility.getTypeName(getApplicationContext());

		log(LOG_MESSAGE_NETWORK_UTILITY_ONLINE, Boolean.toString(online));
		log(LOG_MESSAGE_NETWORK_UTILITY_TYPE, networkName + " / " + networkType);
	}


	private void performResourcesUtility()
	{
		// don't access the activity context from view model, it is dangerous and dirty
		// I do it just to make this sample code simpler and more clear
		Context context = ((UtilitySampleFragment) getView()).getActivity();

		int value = ResourcesUtility.getValueOfAttribute(context, R.attr.colorPrimary);
		int color = ResourcesUtility.getColorValueOfAttribute(context, R.attr.colorPrimary);
		float dimen1 = ResourcesUtility.getDimensionValueOfAttribute(context, R.attr.actionBarSize);
		int dimen2 = ResourcesUtility.getDimensionPixelSizeValueOfAttribute(context, R.attr.actionBarSize);
		Drawable drawable = ResourcesUtility.getDrawableValueOfAttribute(context, R.attr.icon);

		log(LOG_MESSAGE_RESOURCES_UTILITY_VALUE, Integer.toString(value));
		log(LOG_MESSAGE_RESOURCES_UTILITY_COLOR, Integer.toString(color));
		log(LOG_MESSAGE_RESOURCES_UTILITY_DIMEN, Float.toString(dimen1));
		log(LOG_MESSAGE_RESOURCES_UTILITY_DIMEN_PIXEL, Integer.toString(dimen2));
		log(LOG_MESSAGE_RESOURCES_UTILITY_DRAWABLE, drawable == null ? "null" : drawable.toString());
	}


	private void performServiceUtility()
	{
		boolean running = ServiceUtility.isRunning(getApplicationContext(), UtilitySampleService.class);

		log(LOG_MESSAGE_SERVICE_UTILITY, Boolean.toString(running));
	}


	private void performStorageUtility()
	{
		boolean available = StorageUtility.isAvailable();
		boolean writable = StorageUtility.isWritable();
		File storageDir = StorageUtility.getStorageDirectory();
		File picturesStorageDir = StorageUtility.getStorageDirectory(Environment.DIRECTORY_PICTURES);
		File secondaryStorageDir = StorageUtility.getSecondaryStorageDirectory();
		File picturesSecondaryStorageDir = StorageUtility.getSecondaryStorageDirectory(Environment.DIRECTORY_PICTURES);
		File cacheDir = StorageUtility.getApplicationCacheDirectory(getApplicationContext());
		File picturesFilesDir = StorageUtility.getApplicationFilesDirectory(getApplicationContext(), Environment.DIRECTORY_PICTURES);
		List<File> files = StorageUtility.getFiles(picturesStorageDir, true);
		List<File> images = StorageUtility.getFiles(picturesStorageDir, true, Pattern.compile("(.+(\\.(?i)(jpg|jpeg))$)"), null);
		Set<String> mounts = StorageUtility.getExternalMounts();

		log(LOG_MESSAGE_STORAGE_UTILITY_AVAILABLE, Boolean.toString(available));
		log(LOG_MESSAGE_STORAGE_UTILITY_WRITABLE, Boolean.toString(writable));
		log(LOG_MESSAGE_STORAGE_UTILITY_STORAGE_DIR, storageDir == null ? "null" : storageDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_PIC_DIR, picturesStorageDir == null ? "null" : picturesStorageDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_SEC_DIR, secondaryStorageDir == null ? "null" : secondaryStorageDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_PIC_DEC_DIR, picturesSecondaryStorageDir == null ? "null" : picturesSecondaryStorageDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_CACHE_DIR, cacheDir == null ? "null" : cacheDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_PIC_FILES_DIR, picturesFilesDir == null ? "null" : picturesFilesDir.toString());
		log(LOG_MESSAGE_STORAGE_UTILITY_FILES, files == null ? "null" : Integer.toString(files.size()));
		log(LOG_MESSAGE_STORAGE_UTILITY_IMAGES, images == null ? "null" : Integer.toString(images.size()));
		log(LOG_MESSAGE_STORAGE_UTILITY_MOUNTS, mounts == null ? "null" : Integer.toString(mounts.size()));
	}


	private void performStringConvertor()
	{
		String capitalized = StringConvertor.capitalize("Hello world!");

		log(LOG_MESSAGE_STRING_CONVERTOR, capitalized);
	}


	private void performValidationUtility()
	{
		boolean valid1 = ValidationUtility.isEmailValid("hello@alfonz.org");
		boolean valid2 = ValidationUtility.isDateValid("2016-12-02 12:00:00", "yyyy-MM-dd HH:mm:ss");

		log(LOG_MESSAGE_VALIDATION_UTILITY_EMAIL, Boolean.toString(valid1));
		log(LOG_MESSAGE_VALIDATION_UTILITY_DATE, Boolean.toString(valid2));
	}


	private void performVersionUtility()
	{
		String name = VersionUtility.getVersionName(getApplicationContext());
		int code = VersionUtility.getVersionCode(getApplicationContext());
		boolean glEs2 = VersionUtility.isSupportedOpenGlEs2(getApplicationContext());
		int comparison = VersionUtility.compareVersions("1.0.0", "1.1.0");

		log(LOG_MESSAGE_VERSION_UTILITY_NAME, name);
		log(LOG_MESSAGE_VERSION_UTILITY_CODE, Integer.toString(code));
		log(LOG_MESSAGE_VERSION_UTILITY_GLES2, Boolean.toString(glEs2));
		log(LOG_MESSAGE_VERSION_UTILITY_COMPARISON, Integer.toString(comparison));
	}


	private void log(String message, String arg)
	{
		String line = String.format(message, arg);
		String currentLog = log.get().isEmpty() ? log.get() : log.get() + "\n";
		log.set(currentLog + line);
	}
}
