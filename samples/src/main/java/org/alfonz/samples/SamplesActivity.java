package org.alfonz.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.alfonz.samples.alfonzadapter.AdapterSampleActivity;
import org.alfonz.samples.alfonzgraphics.GraphicsSampleActivity;
import org.alfonz.samples.alfonzmedia.MediaSampleActivity;
import org.alfonz.samples.alfonzmvvm.MvvmSampleActivity;
import org.alfonz.samples.alfonzrest.RestSampleActivity;
import org.alfonz.samples.alfonzrx.RxSampleActivity;
import org.alfonz.samples.alfonzutility.UtilitySampleActivity;
import org.alfonz.samples.alfonzview.ViewSampleActivity;


public class SamplesActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_samples);
		setupButtons();
	}


	private void setupButtons()
	{
		Button adapterButton = (Button) findViewById(R.id.activity_samples_adapter);
		Button graphicsButton = (Button) findViewById(R.id.activity_samples_graphics);
		Button mediaButton = (Button) findViewById(R.id.activity_samples_media);
		Button mvvmButton = (Button) findViewById(R.id.activity_samples_mvvm);
		Button restButton = (Button) findViewById(R.id.activity_samples_rest);
		Button rxButton = (Button) findViewById(R.id.activity_samples_rx);
		Button utilityButton = (Button) findViewById(R.id.activity_samples_utility);
		Button viewButton = (Button) findViewById(R.id.activity_samples_view);

		adapterButton.setOnClickListener(view -> startActivity(AdapterSampleActivity.newIntent(SamplesActivity.this)));
		graphicsButton.setOnClickListener(view -> startActivity(GraphicsSampleActivity.newIntent(SamplesActivity.this)));
		mediaButton.setOnClickListener(view -> startActivity(MediaSampleActivity.newIntent(SamplesActivity.this)));
		mvvmButton.setOnClickListener(view -> startActivity(MvvmSampleActivity.newIntent(SamplesActivity.this)));
		restButton.setOnClickListener(view -> startActivity(RestSampleActivity.newIntent(SamplesActivity.this)));
		rxButton.setOnClickListener(view -> startActivity(RxSampleActivity.newIntent(SamplesActivity.this)));
		utilityButton.setOnClickListener(view -> startActivity(UtilitySampleActivity.newIntent(SamplesActivity.this)));
		viewButton.setOnClickListener(view -> startActivity(ViewSampleActivity.newIntent(SamplesActivity.this)));
	}
}
