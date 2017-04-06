package org.alfonz.mvvm;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;


public final class BindingUtility
{
	private BindingUtility() {}


	@BindingAdapter({"onClick"})
	public static void setOnClick(View view, View.OnClickListener listener)
	{
		view.setOnClickListener(listener);
	}


	@BindingAdapter({"onLongClick"})
	public static void setOnLongClick(View view, View.OnLongClickListener listener)
	{
		view.setOnLongClickListener(listener);
	}


	@BindingAdapter({"visible"})
	public static void setVisible(View view, boolean visible)
	{
		view.setVisibility(visible ? View.VISIBLE : View.GONE);
	}


	@BindingAdapter({"invisible"})
	public static void setInvisible(View view, boolean invisible)
	{
		view.setVisibility(invisible ? View.INVISIBLE : View.VISIBLE);
	}


	@BindingAdapter({"gone"})
	public static void setGone(View view, boolean gone)
	{
		view.setVisibility(gone ? View.GONE : View.VISIBLE);
	}


	@BindingAdapter({"imageBitmap"})
	public static void setImageBitmap(ImageView imageView, Bitmap bitmap)
	{
		imageView.setImageBitmap(bitmap);
	}


	@BindingConversion
	public static int convertBooleanToVisibility(boolean visible)
	{
		return visible ? View.VISIBLE : View.GONE;
	}
}
