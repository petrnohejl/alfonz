package org.alfonz.samples.alfonzrx;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import org.alfonz.samples.R;


public class RxSampleActivity extends AppCompatActivity
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
	}
}
