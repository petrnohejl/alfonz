package org.alfonz.samples.alfonzmedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseActivity;


public class MediaSampleActivity extends BaseActivity
{
	public static Intent newIntent(Context context)
	{
		Intent intent = new Intent(context, MediaSampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}


	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_media_sample);
		setupActionBar(INDICATOR_BACK);
	}
}
