package org.alfonz.mvvm;

import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import eu.inloop.viewmodel.base.ViewModelBaseEmptyActivity;


public abstract class AlfonzActivity extends ViewModelBaseEmptyActivity
{
	public static final int INDICATOR_NONE = 0;
	public static final int INDICATOR_BACK = 1;
	public static final int INDICATOR_CLOSE = 2;


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({INDICATOR_NONE, INDICATOR_BACK, INDICATOR_CLOSE})
	public @interface IndicatorType {}


	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType)
	{
		return setupActionBar(indicatorType, null, null);
	}


	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType, @Nullable CharSequence title)
	{
		return setupActionBar(indicatorType, title, null);
	}


	@Nullable
	public ActionBar setupActionBar(@IndicatorType int indicatorType, @Nullable CharSequence title, @Nullable Toolbar toolbar)
	{
		if(toolbar == null)
		{
			toolbar = (Toolbar) findViewById(R.id.toolbar);
		}
		setSupportActionBar(toolbar);

		ActionBar actionBar = getSupportActionBar();
		if(actionBar != null)
		{
			actionBar.setDisplayUseLogoEnabled(false);
			actionBar.setDisplayShowTitleEnabled(true);
			actionBar.setDisplayShowHomeEnabled(true);

			if(indicatorType == INDICATOR_NONE)
			{
				actionBar.setDisplayHomeAsUpEnabled(false);
				actionBar.setHomeButtonEnabled(false);
			}
			else if(indicatorType == INDICATOR_BACK)
			{
				actionBar.setDisplayHomeAsUpEnabled(true);
				actionBar.setHomeButtonEnabled(true);
			}
			else if(indicatorType == INDICATOR_CLOSE)
			{
				actionBar.setDisplayHomeAsUpEnabled(true);
				actionBar.setHomeButtonEnabled(true);
				actionBar.setHomeAsUpIndicator(R.drawable.ic_close);
			}

			if(title != null)
			{
				actionBar.setTitle(title);
			}
		}
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
