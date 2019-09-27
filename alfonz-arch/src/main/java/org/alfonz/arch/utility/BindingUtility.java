package org.alfonz.arch.utility;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.BindingAdapter;
import androidx.databinding.BindingConversion;

public final class BindingUtility {
	private BindingUtility() {}

	@BindingAdapter({"onClick"})
	public static void setOnClick(@NonNull View view, View.OnClickListener listener) {
		view.setOnClickListener(listener);
	}

	@BindingAdapter({"onLongClick"})
	public static void setOnLongClick(@NonNull View view, View.OnLongClickListener listener) {
		view.setOnLongClickListener(listener);
	}

	@BindingAdapter({"visible"})
	public static void setVisible(@NonNull View view, boolean visible) {
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
	}

	@BindingAdapter({"invisible"})
	public static void setInvisible(@NonNull View view, boolean invisible) {
		view.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
	}

	@BindingAdapter({"gone"})
	public static void setGone(@NonNull View view, boolean gone) {
		view.setVisibility(gone ? View.GONE : View.VISIBLE);
	}

	@BindingAdapter({"imageBitmap"})
	public static void setImageBitmap(@NonNull ImageView imageView, Bitmap bitmap) {
		imageView.setImageBitmap(bitmap);
	}

	@BindingConversion
	public static int convertBooleanToVisibility(boolean visible) {
		return visible ? View.VISIBLE : View.GONE;
	}
}
