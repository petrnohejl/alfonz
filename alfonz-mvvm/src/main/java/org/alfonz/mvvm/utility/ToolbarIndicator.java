package org.alfonz.mvvm.utility;

import android.content.Context;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.Pair;
import android.util.TypedValue;

import org.alfonz.mvvm.R;


public class ToolbarIndicator
{
	public static final ToolbarIndicator INDICATOR_NONE = new ToolbarIndicator(0, false, false);
	public static final ToolbarIndicator INDICATOR_BACK = new ToolbarIndicator(0, true, true);
	public static final ToolbarIndicator INDICATOR_CLOSE = new ToolbarIndicator(R.drawable.ic_close, true, true);
	private static final String TAG = "ALFONZ";

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


	/**
	 * Gets colorControlNormal attribute from theme.
	 * Can handle only colors, so if colorControlNormal is selector, it's not possible to tint it.
	 *
	 * @param context to tell where theme is taken from (should be view's context)
	 * @return pair with success flag and color value (may be negative int because of overflow)
	 */
	private static Pair<Boolean, Integer> getThemeTintColor(@NonNull Context context)
	{
		int colorAttr;
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
		{
			colorAttr = android.R.attr.colorControlNormal;
		}
		else
		{
			//Get attribute defined for AppCompat
			colorAttr = context.getResources().getIdentifier("colorControlNormal", "attr", context.getPackageName());
		}

		TypedValue outValue = new TypedValue();
		boolean wasFound = context.getTheme().resolveAttribute(colorAttr, outValue, true);
		if(outValue.type < TypedValue.TYPE_FIRST_COLOR_INT || outValue.type > TypedValue.TYPE_LAST_COLOR_INT)
		{
			String codeLocation = "[" + ToolbarIndicator.class.getSimpleName() + ".getThemeTintColor] ";
			Log.w(TAG, codeLocation + "Toolbar theme resource found, but it's not color (the type is " + outValue.type + "). We don't know how to handle it (yet).");
			wasFound = false;
		}

		return new Pair<>(wasFound, outValue.data);
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


	/**
	 * @param toolbar from which theme is taken
	 * @return tinted drawable if possible, untinted if not possible
	 */
	public Drawable getTintedDrawable(@NonNull Toolbar toolbar)
	{
		Drawable drawable = ContextCompat.getDrawable(toolbar.getContext(), mDrawableRes);
		Pair<Boolean, Integer> tintResult = getThemeTintColor(toolbar.getContext());

		if(tintResult.first)
		{
			//noinspection PointlessBitwiseExpression transforms number which could've overflown
			int parsedColor = (int) (0xFFFFFFFF & (long) tintResult.second);
			drawable.setColorFilter(new PorterDuffColorFilter(parsedColor, PorterDuff.Mode.SRC_IN));
		}
		return drawable;
	}
}