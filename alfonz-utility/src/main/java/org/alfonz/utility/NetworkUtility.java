package org.alfonz.utility;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.annotation.NonNull;

// requires android.permission.ACCESS_NETWORK_STATE
public final class NetworkUtility {
	private NetworkUtility() {}

	public static boolean isOnline(@NonNull Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
		return (networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnected());
	}

	public static int getType(@NonNull Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			// returns ConnectivityManager.TYPE_WIFI, ConnectivityManager.TYPE_MOBILE etc.
			return networkInfo.getType();
		} else {
			return -1;
		}
	}

	public static String getTypeName(@NonNull Context context) {
		ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

		if (networkInfo != null) {
			return networkInfo.getTypeName();
		} else {
			return null;
		}
	}
}
