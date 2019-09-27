package org.alfonz.graphics.bitmap;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;

public final class BitmapScaler {
	private BitmapScaler() {}

	// scale and keep aspect ratio
	public static Bitmap scaleToFitWidth(@NonNull Bitmap bitmap, int width) {
		float factor = width / (float) bitmap.getWidth();
		return Bitmap.createScaledBitmap(bitmap, width, (int) (bitmap.getHeight() * factor), true);
	}

	// scale and keep aspect ratio
	public static Bitmap scaleToFitHeight(@NonNull Bitmap bitmap, int height) {
		float factor = height / (float) bitmap.getHeight();
		return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * factor), height, true);
	}

	// scale and keep aspect ratio
	public static Bitmap scaleToFill(@NonNull Bitmap bitmap, int width, int height) {
		float factorH = height / (float) bitmap.getWidth();
		float factorW = width / (float) bitmap.getWidth();
		float factorToUse = (factorH > factorW) ? factorW : factorH;
		return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * factorToUse), (int) (bitmap.getHeight() * factorToUse), true);
	}

	// scale and don't keep aspect ratio
	public static Bitmap stretchToFill(@NonNull Bitmap bitmap, int width, int height) {
		float factorH = height / (float) bitmap.getHeight();
		float factorW = width / (float) bitmap.getWidth();
		return Bitmap.createScaledBitmap(bitmap, (int) (bitmap.getWidth() * factorW), (int) (bitmap.getHeight() * factorH), true);
	}
}
