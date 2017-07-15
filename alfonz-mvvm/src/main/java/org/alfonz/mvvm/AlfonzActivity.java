package org.alfonz.mvvm;

import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.alfonz.mvvm.utility.ToolbarIndicator;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import eu.inloop.viewmodel.base.ViewModelBaseEmptyActivity;


public abstract class AlfonzActivity extends ViewModelBaseEmptyActivity
{
	/**
	 * @deprecated Use {@link ToolbarIndicator#INDICATOR_NONE} instead
	 */
	public static final int INDICATOR_NONE = 0;
	/**
	 * @deprecated Use {@link ToolbarIndicator#INDICATOR_BACK} instead
	 */
	public static final int INDICATOR_BACK = 1;
	/**
	 * @deprecated Use {@link ToolbarIndicator#INDICATOR_CLOSE} instead
	 */
	public static final int INDICATOR_CLOSE = 2;
	@Nullable private Toolbar mToolbar = null;


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({INDICATOR_NONE, INDICATOR_BACK, INDICATOR_CLOSE})
	public @interface IndicatorType {}


	/**
	 * @deprecated Use {@link #setupActionBar(ToolbarIndicator)} instead
	 */
	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType)
	{
		return setupActionBar(ToolbarIndicator.fromIndicatorInt(indicatorType), null, null);
	}


	/**
	 * @deprecated Use {@link #setupActionBar(ToolbarIndicator, CharSequence)} instead
	 */
	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType, @Nullable CharSequence title)
	{
		return setupActionBar(ToolbarIndicator.fromIndicatorInt(indicatorType), title, null);
	}


	/**
	 * @deprecated Use {@link #setupActionBar(ToolbarIndicator, CharSequence, Toolbar)} instead
	 */
	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType, @Nullable CharSequence title, @Nullable Toolbar toolbar)
	{
		return setupActionBar(ToolbarIndicator.fromIndicatorInt(indicatorType), title, toolbar);
	}


	@Nullable
	public ActionBar setupActionBar(ToolbarIndicator indicator)
	{
		return setupActionBar(indicator, null, null);
	}


	@Nullable
	public ActionBar setupActionBar(ToolbarIndicator indicator, @Nullable CharSequence title)
	{
		return setupActionBar(indicator, title, null);
	}


	/**
	 * Setups main toolbar as ActionBar. Tries to tints navigation icon based on toolbar's theme
	 *
	 * @param indicator navigation icon (NONE, BACK or CLOSE are predefined). Uses toolbar theme color for tinting.
	 * @param title     to be shown as on ActionBar. If null specified, title is not changed! (use empty string to clear)
	 * @param toolbar   may be null, in that case it is searched for R.id.toolbar
	 * @return initilized ActionBar or null
	 */
	@Nullable
	public ActionBar setupActionBar(ToolbarIndicator indicator, @Nullable CharSequence title, @Nullable Toolbar toolbar)
	{
		if(toolbar == null)
		{
			toolbar = (Toolbar) findViewById(R.id.toolbar);
		}

		if(mToolbar != toolbar)
		{
			// this is because if 2 fragments share a toolbar (in activity), it caused bug that back icon was not shown
			setSupportActionBar(toolbar);
		}

		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null)
		{
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);
			actionBar.setDisplayHomeAsUpEnabled(indicator.isHomeAsUpEnabled);
			actionBar.setHomeButtonEnabled(indicator.isHomeEnabled);
			if(indicator.drawableRes <= 0)
			{
				actionBar.setHomeAsUpIndicator(null);
			}
			else
			{
				// tries to tint drawable if found style from toolbar
				Drawable iconDrawable = indicator.getTintedDrawable(toolbar);
				actionBar.setHomeAsUpIndicator(iconDrawable);
			}

			if(title != null)
			{
				actionBar.setTitle(title);
			}
		}

		mToolbar = toolbar;
		return actionBar;
	}


	public void replaceFragment(Fragment fragment)
	{
		replaceFragment(fragment, false, false);
	}


	public void replaceFragment(Fragment fragment, boolean addToBackStack, boolean allowStateLoss)
	{
		FragmentTransaction transaction = getSupportFragmentManager().beginTransaction().replace(R.id.container_fragment, fragment);

		if(addToBackStack)
		{
			transaction.addToBackStack(fragment.getClass().getSimpleName());
		}

		if(allowStateLoss)
		{
			transaction.commitAllowingStateLoss();
		}
		else
		{
			transaction.commit();
		}
	}
}
