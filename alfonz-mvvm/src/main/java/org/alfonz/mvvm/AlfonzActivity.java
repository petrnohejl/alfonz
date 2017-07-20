package org.alfonz.mvvm;

import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import org.alfonz.mvvm.utility.ToolbarIndicator;

import eu.inloop.viewmodel.base.ViewModelBaseEmptyActivity;


public abstract class AlfonzActivity extends ViewModelBaseEmptyActivity
{
	private int mToolbarHashCode = 0;


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

		int toolbarHashCode = toolbar != null ? toolbar.hashCode() : 0;

		if(mToolbarHashCode != toolbarHashCode)
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
			actionBar.setDisplayHomeAsUpEnabled(indicator.isHomeAsUpEnabled());
			actionBar.setHomeButtonEnabled(indicator.isHomeEnabled());
			if(indicator.getDrawableRes() <= 0)
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

		mToolbarHashCode = toolbarHashCode;
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
