package org.alfonz.graphics.bitmap;

import android.content.Context;
import android.graphics.Bitmap;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;

import androidx.annotation.NonNull;

public final class BitmapBlur {
	private static final float BITMAP_SCALE = 0.4F;
	private static final float BLUR_RADIUS = 20F;

	private BitmapBlur() {}

	public static Bitmap getBlurredBitmap(@NonNull Context context, @NonNull Bitmap bitmap) {
		return getBlurredBitmap(context, bitmap, BITMAP_SCALE, BLUR_RADIUS);
	}

	public static Bitmap getBlurredBitmap(@NonNull Context context, @NonNull Bitmap bitmap, float scale, float radius) {
		int width = Math.round(bitmap.getWidth() * scale);
		int height = Math.round(bitmap.getHeight() * scale);

		Bitmap inputBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
		Bitmap outputBitmap = Bitmap.createBitmap(inputBitmap);

		RenderScript rs = RenderScript.create(context);
		ScriptIntrinsicBlur scriptIntrinsicBlur = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
		Allocation tmpIn = Allocation.createFromBitmap(rs, inputBitmap);
		Allocation tmpOut = Allocation.createFromBitmap(rs, outputBitmap);
		scriptIntrinsicBlur.setRadius(radius);
		scriptIntrinsicBlur.setInput(tmpIn);
		scriptIntrinsicBlur.forEach(tmpOut);
		tmpOut.copyTo(outputBitmap);
		rs.destroy();

		return outputBitmap;
	}
}
