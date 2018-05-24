package org.alfonz.graphics.drawable;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;


public class PlaceholderDrawable extends Drawable
{
	public static final int DEFAULT_TEXT_SIZE_PERCENTAGE = 50;
	public static final String DEFAULT_TEXT = "?";
	public static final String DEFAULT_COLOR = "#808080";

	private static final String COLOR_FORMAT = "#FF%06X";
	private static final float DARKNESS_THRESHOLD = 0.2F;

	private String mSymbol;
	private int mTextSizePercentage;
	private boolean mCircular;
	private Paint mTextPaint;
	private Paint mBackgroundPaint;
	private RectF mBounds;
	private float mTextStartXPoint;
	private float mTextStartYPoint;


	public PlaceholderDrawable(String text)
	{
		this(text, DEFAULT_TEXT, DEFAULT_TEXT_SIZE_PERCENTAGE, false);
	}


	public PlaceholderDrawable(String text, @IntRange(from = 0, to = 100) int textSizePercentage)
	{
		this(text, DEFAULT_TEXT, textSizePercentage, false, 0, 0);
	}


	public PlaceholderDrawable(String text, @NonNull String defaultText)
	{
		this(text, defaultText, DEFAULT_TEXT_SIZE_PERCENTAGE, false, 0, 0);
	}


	public PlaceholderDrawable(String text, @NonNull String defaultText, @IntRange(from = 0, to = 100) int textSizePercentage)
	{
		this(text, defaultText, textSizePercentage, false, 0, 0);
	}


	public PlaceholderDrawable(String text, @NonNull String defaultText, @IntRange(from = 0, to = 100) int textSizePercentage, boolean circular)
	{
		this(text, defaultText, textSizePercentage, circular, 0, 0);
	}


	public PlaceholderDrawable(String text, @NonNull String defaultText, @IntRange(from = 0, to = 100) int textSizePercentage, boolean circular, int textColor, int backgroundColor)
	{
		this.mSymbol = resolveSymbol(text, defaultText);
		this.mTextSizePercentage = textSizePercentage;
		this.mCircular = circular;

		if(textColor == 0) textColor = Color.parseColor("white");
		if(backgroundColor == 0) backgroundColor = handleColorDarkness(convertStringToColor(text));

		mTextPaint = new Paint();
		mTextPaint.setAntiAlias(true);
		mTextPaint.setColor(textColor);
		mTextPaint.setTypeface(Typeface.create("sans-serif", Typeface.NORMAL));

		mBackgroundPaint = new Paint();
		mBackgroundPaint.setAntiAlias(true);
		mBackgroundPaint.setStyle(Paint.Style.FILL);
		mBackgroundPaint.setColor(backgroundColor);
	}


	@Override
	public void draw(@NonNull Canvas canvas)
	{
		if(mBounds == null)
		{
			mBounds = new RectF(0, 0, canvas.getWidth(), canvas.getHeight());
			setupTextValues();
		}

		if(mCircular)
		{
			canvas.drawOval(mBounds, mBackgroundPaint);
		}
		else
		{
			canvas.drawRect(mBounds, mBackgroundPaint);
		}

		canvas.drawText(mSymbol, mTextStartXPoint, mTextStartYPoint, mTextPaint);
	}


	@Override
	public void setAlpha(int alpha)
	{
		mTextPaint.setAlpha(alpha);
		mBackgroundPaint.setAlpha(alpha);
	}


	@Override
	public void setColorFilter(ColorFilter colorFilter)
	{
		mTextPaint.setColorFilter(colorFilter);
		mBackgroundPaint.setColorFilter(colorFilter);
	}


	@Override
	public int getOpacity()
	{
		return PixelFormat.TRANSLUCENT;
	}


	private String resolveSymbol(String text, String defaultText)
	{
		if(isNotNullOrEmpty(text))
		{
			return text.substring(0, 1).toUpperCase();
		}
		else
		{
			if(isNotNullOrEmpty(defaultText))
			{
				return defaultText;
			}
			else
			{
				return DEFAULT_TEXT;
			}
		}
	}


	private int convertStringToColor(String text)
	{
		String color = isNullOrEmpty(text) ? DEFAULT_COLOR : String.format(COLOR_FORMAT, (0xFFFFFF & text.hashCode()));
		return Color.parseColor(color);
	}


	private int handleColorDarkness(int color)
	{
		if(isColorLight(color))
		{
			return darkerColor(color, 1 - DARKNESS_THRESHOLD);
		}
		else
		{
			return color;
		}
	}


	private boolean isColorLight(int color)
	{
		double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
		return darkness < DARKNESS_THRESHOLD;
	}


	private int darkerColor(int color, float factor)
	{
		int a = Color.alpha(color);
		int r = Color.red(color);
		int g = Color.green(color);
		int b = Color.blue(color);

		return Color.argb(a,
				Math.max((int) (r * factor), 0),
				Math.max((int) (g * factor), 0),
				Math.max((int) (b * factor), 0));
	}


	private void setupTextValues()
	{
		mTextPaint.setTextSize(calculateTextSize());
		mTextStartXPoint = calculateTextStartXPoint();
		mTextStartYPoint = calculateTextStartYPoint();
	}


	private float calculateTextSize()
	{
		if(mTextSizePercentage < 0 || mTextSizePercentage > 100)
		{
			mTextSizePercentage = DEFAULT_TEXT_SIZE_PERCENTAGE;
		}
		return getBounds().height() * (float) mTextSizePercentage / 100;
	}


	private float calculateTextStartXPoint()
	{
		float stringWidth = mTextPaint.measureText(mSymbol);
		return (getBounds().width() / 2f) - (stringWidth / 2f);
	}


	private float calculateTextStartYPoint()
	{
		return (getBounds().height() / 2f) - ((mTextPaint.ascent() + mTextPaint.descent()) / 2f);
	}


	private boolean isNotNullOrEmpty(String string)
	{
		return string != null && !string.isEmpty();
	}


	private boolean isNullOrEmpty(String string)
	{
		return string == null || string.isEmpty();
	}
}
