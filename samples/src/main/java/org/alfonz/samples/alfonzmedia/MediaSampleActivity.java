package org.alfonz.samples.alfonzmedia;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.alfonz.samples.R;


public class MediaSampleActivity extends AppCompatActivity
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
	}
}
