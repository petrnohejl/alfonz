package org.alfonz.mvvm.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;

import org.alfonz.mvvm.R;


@Deprecated
public class ToolbarIndicator
{
	public static final ToolbarIndicator NONE = new ToolbarIndicator(0, false, false);
	public static final ToolbarIndicator BACK = new ToolbarIndicator(0, true, true);
	public static final ToolbarIndicator CLOSE = new ToolbarIndicator(R.drawable.ic_close, true, true);

	@DrawableRes
	private final int mDrawableRes;
	private final boolean mIsHomeEnabled;
	private final boolean mIsHomeAsUpEnabled;


	public ToolbarIndicator(@DrawableRes int drawableRes, boolean isHomeEnabled, boolean isHomeAsUpEnabled)
	{
		mDrawableRes = drawableRes;
		mIsHomeEnabled = isHomeEnabled;
		mIsHomeAsUpEnabled = isHomeAsUpEnabled;
	}


	private static int getThemeTintColor(@NonNull Context context)
	{
		int attr;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			attr = android.R.attr.colorControlNormal;
		}
		else
		{
			attr = context.getResources().getIdentifier("colorControlNormal", "attr", context.getPackageName());
		}

		return getColorValueOfAttribute(context, attr);
	}


	private static int getColorValueOfAttribute(@NonNull Context context, int attr)
	{
		TypedArray typedArray = context.obtainStyledAttributes(null, new int[]{attr}, 0, 0);
		int value = typedArray.getColor(0, 0);
		typedArray.recycle();
		return value;
	}


	public Drawable getTintedDrawable(@NonNull Toolbar toolbar)
	{
		int color = getThemeTintColor(toolbar.getContext());
		Drawable drawable = ContextCompat.getDrawable(toolbar.getContext(), mDrawableRes);
		drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.SRC_IN));
		return drawable;
	}


	public int getDrawableRes()
	{
		return mDrawableRes;
	}


	public boolean isHomeEnabled()
	{
		return mIsHomeEnabled;
	}


	public boolean isHomeAsUpEnabled()
	{
		return mIsHomeAsUpEnabled;
	}
}
