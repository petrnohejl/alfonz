package org.alfonz.samples.alfonzrx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.alfonz.mvvm.utility.ToolbarIndicator;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseActivity;


public class RxSampleActivity extends BaseActivity
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, RxSampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_rx_sample);
		setupActionBar(ToolbarIndicator.INDICATOR_BACK);
	}
}
