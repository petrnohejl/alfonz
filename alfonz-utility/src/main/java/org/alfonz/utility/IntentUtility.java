package org.alfonz.utility;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.List;
import java.util.Locale;

public final class IntentUtility {
	private IntentUtility() {}

	public static void startWebActivity(@NonNull Context context, @NonNull String uri) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startStoreActivity(@NonNull Context context) {
		String uri = String.format("https://play.google.com/store/apps/details?id=%s", context.getPackageName());
		startWebActivity(context, uri);
	}

	public static void startShareActivity(@NonNull Context context, @NonNull String subject, @NonNull String text) {
		startShareActivity(context, subject, text, null);
	}

	public static void startShareActivity(@NonNull Context context, @NonNull String subject, @NonNull String text, @Nullable String chooserTitle) {
		try {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.setType("text/plain");
			intent.putExtra(Intent.EXTRA_SUBJECT, subject);
			intent.putExtra(Intent.EXTRA_TEXT, text);

			if (chooserTitle == null) {
				context.startActivity(intent);
			} else {
				context.startActivity(Intent.createChooser(intent, chooserTitle));
			}
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startEmailActivity(@NonNull Context context, @NonNull String email) {
		startEmailActivity(context, email, null, null);
	}

	public static void startEmailActivity(@NonNull Context context, @NonNull String email, @Nullable String subject) {
		startEmailActivity(context, email, subject, null);
	}

	public static void startEmailActivity(@NonNull Context context, @NonNull String email, @Nullable String subject, @Nullable String text) {
		try {
			String uri = String.format("mailto:%s", email);
			Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse(uri));
			if (subject != null) intent.putExtra(Intent.EXTRA_SUBJECT, subject);
			if (text != null) intent.putExtra(Intent.EXTRA_TEXT, text);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startSmsActivity(@NonNull Context context, @NonNull String phoneNumber) {
		startSmsActivity(context, phoneNumber, null);
	}

	public static void startSmsActivity(@NonNull Context context, @NonNull String phoneNumber, @Nullable String text) {
		try {
			Intent intent = new Intent(Intent.ACTION_VIEW);
			intent.setType("vnd.android-dir/mms-sms");
			intent.putExtra("address", phoneNumber);
			if (text != null) intent.putExtra("sms_body", text);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startCallActivity(@NonNull Context context, @NonNull String phoneNumber) {
		try {
			String uri = String.format("tel:%s", phoneNumber);
			Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startMapCoordinatesActivity(@NonNull Context context, double lat, double lon, String label) {
		startMapCoordinatesActivity(context, lat, lon, 16, label);
	}

	public static void startMapCoordinatesActivity(@NonNull Context context, double lat, double lon, int zoom, String label) {
		try {
			String query = Uri.encode(lat + "," + lon + "(" + label + ")"); // query allows to show pin
			String uri = String.format(Locale.US, "geo:%f,%f?z=%d&q=%s", lat, lon, zoom, query); // zoom value: 2..23
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startMapSearchActivity(@NonNull Context context, @NonNull String query) {
		try {
			String uri = String.format("geo:0,0?q=%s", Uri.encode(query));
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startNavigationActivity(@NonNull Context context, double lat, double lon) {
		try {
			String uri = String.format(Locale.US, "http://maps.google.com/maps?daddr=%f,%f", lat, lon);
			Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static void startCalendarActivity(@NonNull Context context, @NonNull String title) {
		startCalendarActivity(context, title, null, -1, -1);
	}

	public static void startCalendarActivity(@NonNull Context context, @NonNull String title, long beginTime, long endTime) {
		startCalendarActivity(context, title, null, beginTime, endTime);
	}

	public static void startCalendarActivity(@NonNull Context context, @NonNull String title, @Nullable String description, long beginTime, long endTime) {
		try {
			Intent intent = new Intent(Intent.ACTION_EDIT);
			intent.setType("vnd.android.cursor.item/event");
			intent.putExtra("title", title);
			if (description != null) intent.putExtra("description", description);
			if (beginTime > -1) intent.putExtra("beginTime", beginTime); // time in milliseconds
			if (endTime > -1) intent.putExtra("endTime", endTime);
			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	@RequiresApi(Build.VERSION_CODES.KITKAT)
	public static void startNotificationSettingsActivity(@NonNull Context context) {
		try {
			Intent intent;

			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
				intent = new Intent(Settings.ACTION_APP_NOTIFICATION_SETTINGS);
				intent.putExtra(Settings.EXTRA_APP_PACKAGE, context.getPackageName());
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
				intent = new Intent("android.settings.APP_NOTIFICATION_SETTINGS");
				intent.putExtra("app_package", context.getPackageName());
				intent.putExtra("app_uid", context.getApplicationInfo().uid);
			} else if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
				intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
				intent.addCategory(Intent.CATEGORY_DEFAULT);
				intent.setData(Uri.parse("package:" + context.getPackageName()));
			} else {
				throw new UnsupportedOperationException("Notification settings not supported on API version " + Build.VERSION.SDK_INT);
			}

			context.startActivity(intent);
		} catch (ActivityNotFoundException e) {
			e.printStackTrace();
		}
	}

	public static boolean isCallable(@NonNull Context context, @NonNull Intent intent) {
		List<ResolveInfo> list = context.getApplicationContext().getPackageManager().queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
		return list.size() > 0;
	}
}
