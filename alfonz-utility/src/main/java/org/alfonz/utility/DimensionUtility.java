package org.alfonz.utility;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public final class DimensionUtility {
	private DimensionUtility() {}

	public static float dp2px(@NonNull final Context context, final float dp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
	}

	public static float sp2px(@NonNull final Context context, final float sp) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
	}

	public static float px2dp(@NonNull final Context context, final float px) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return px / ((float) displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
	}

	public static float px2sp(@NonNull final Context context, final float px) {
		DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
		return px / displayMetrics.scaledDensity;
	}
}
