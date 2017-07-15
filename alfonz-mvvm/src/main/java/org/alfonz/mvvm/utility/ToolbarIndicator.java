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

import org.alfonz.mvvm.AlfonzActivity;
import org.alfonz.mvvm.R;


public class ToolbarIndicator
{
	public static final String TAG = ToolbarIndicator.class.getSimpleName();

	public static final ToolbarIndicator INDICATOR_NONE = new ToolbarIndicator(0, false, false);
	public static final ToolbarIndicator INDICATOR_BACK = new ToolbarIndicator(0, true, true);
	public static ToolbarIndicator INDICATOR_CLOSE = new ToolbarIndicator(R.drawable.ic_close, true, true);

	@DrawableRes
	public final int drawableRes;
	public final boolean isHomeEnabled;
	public final boolean isHomeAsUpEnabled;


	public ToolbarIndicator(@DrawableRes int drawableRes, boolean isHomeEnabled, boolean isHomeAsUpEnabled)
	{
		this.drawableRes = drawableRes;
		this.isHomeEnabled = isHomeEnabled;
		this.isHomeAsUpEnabled = isHomeAsUpEnabled;
	}


	/**
	 * Handling compatibilty issues
	 *
	 * @param type older indicator type which is deprecated
	 * @return new toolbar indicator type
	 * @deprecated
	 */
	public static ToolbarIndicator fromIndicatorInt(@AlfonzActivity.IndicatorType int type)
	{
		switch(type)
		{
			case AlfonzActivity.INDICATOR_NONE:
				return INDICATOR_NONE;
			case AlfonzActivity.INDICATOR_BACK:
				return INDICATOR_BACK;
			case AlfonzActivity.INDICATOR_CLOSE:
				return INDICATOR_CLOSE;
		}

		throw new IllegalArgumentException("This indicator type is not supported (type = " + type + ")");
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
			Log.w(TAG, "Toolbar theme resource found, but it's not color (the type = " + outValue.type + "). We don't know how to handle it (yet).");
			wasFound = false;
		}

		return new Pair<>(wasFound, outValue.data);
	}


	/**
	 * @param toolbar from which theme is taken
	 * @return tinted drawable if possible, untinted if not possible
	 */
	public Drawable getTintedDrawable(@NonNull Toolbar toolbar)
	{
		Drawable drawable = ContextCompat.getDrawable(toolbar.getContext(), drawableRes);
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