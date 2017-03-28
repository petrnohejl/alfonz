package org.alfonz.samples.alfonzadapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseActivity;


public class AdapterSampleActivity extends BaseActivity
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, AdapterSampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adapter_sample);
		setupActionBar(INDICATOR_BACK);
		setupFragment(savedInstanceState);
	}


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// action bar menu
		MenuInflater menuInflater = getMenuInflater();
		menuInflater.inflate(R.menu.activity_adapter_sample, menu);
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// action bar menu behavior
		switch(item.getItemId())
		{
			case R.id.menu_activity_adapter_sample_list_simple:
				Fragment listSimpleFragment = AdapterSampleListSimpleFragment.newInstance();
				replaceFragment(listSimpleFragment);
				return true;

			case R.id.menu_activity_adapter_sample_list_multi:
				Fragment listMultiFragment = AdapterSampleListMultiFragment.newInstance();
				replaceFragment(listMultiFragment);
				return true;

			case R.id.menu_activity_adapter_sample_pager:
				Fragment pagerFragment = AdapterSamplePagerFragment.newInstance();
				replaceFragment(pagerFragment);
				return true;

			default:
				return super.onOptionsItemSelected(item);
		}
	}


	private void setupFragment(Bundle savedInstanceState)
	{
		if(savedInstanceState != null) return;
		Fragment fragment = AdapterSampleListSimpleFragment.newInstance();
		replaceFragment(fragment);
	}
}
