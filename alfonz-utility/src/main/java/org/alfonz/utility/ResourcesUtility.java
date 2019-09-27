package org.alfonz.utility;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.NonNull;

public final class ResourcesUtility {
	private ResourcesUtility() {}

	public static int getValueOfAttribute(@NonNull Context context, int attr) {
		TypedValue typedValue = new TypedValue();
		Resources.Theme theme = context.getTheme();
		theme.resolveAttribute(attr, typedValue, true);
		return typedValue.data;
	}

	public static int getColorValueOfAttribute(@NonNull Context context, int attr) {
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		int value = typedArray.getColor(0, 0);
		typedArray.recycle();
		return value;
	}

	public static float getDimensionValueOfAttribute(@NonNull Context context, int attr) {
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		float value = typedArray.getDimension(0, 0);
		typedArray.recycle();
		return value;
	}

	public static int getDimensionPixelSizeValueOfAttribute(@NonNull Context context, int attr) {
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		int value = typedArray.getDimensionPixelSize(0, 0);
		typedArray.recycle();
		return value;
	}

	public static Drawable getDrawableValueOfAttribute(@NonNull Context context, int attr) {
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		Drawable value = typedArray.getDrawable(0);
		typedArray.recycle();
		return value;
	}
}
