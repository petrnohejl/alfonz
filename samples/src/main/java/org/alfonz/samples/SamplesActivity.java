package org.alfonz.samples;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.alfonz.samples.alfonzadapter.AlfonzAdapterActivity;
import org.alfonz.samples.alfonzgraphics.AlfonzGraphicsActivity;
import org.alfonz.samples.alfonzmedia.AlfonzMediaActivity;
import org.alfonz.samples.alfonzmvvm.AlfonzMvvmActivity;
import org.alfonz.samples.alfonzrest.AlfonzRestActivity;
import org.alfonz.samples.alfonzrx.AlfonzRxActivity;
import org.alfonz.samples.alfonzutility.AlfonzUtilityActivity;
import org.alfonz.samples.alfonzview.AlfonzViewActivity;


public class SamplesActivity extends AppCompatActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_samples);
		bindData();
	}


	private void bindData()
	{
		Button adapterButton = (Button) findViewById(R.id.activity_samples_adapter);
		Button graphicsButton = (Button) findViewById(R.id.activity_samples_graphics);
		Button mediaButton = (Button) findViewById(R.id.activity_samples_media);
		Button mvvmButton = (Button) findViewById(R.id.activity_samples_mvvm);
		Button restButton = (Button) findViewById(R.id.activity_samples_rest);
		Button rxButton = (Button) findViewById(R.id.activity_samples_rx);
		Button utilityButton = (Button) findViewById(R.id.activity_samples_utility);
		Button viewButton = (Button) findViewById(R.id.activity_samples_view);

		adapterButton.setOnClickListener(view -> startActivity(AlfonzAdapterActivity.newIntent(SamplesActivity.this)));
		graphicsButton.setOnClickListener(view -> startActivity(AlfonzGraphicsActivity.newIntent(SamplesActivity.this)));
		mediaButton.setOnClickListener(view -> startActivity(AlfonzMediaActivity.newIntent(SamplesActivity.this)));
		mvvmButton.setOnClickListener(view -> startActivity(AlfonzMvvmActivity.newIntent(SamplesActivity.this)));
		restButton.setOnClickListener(view -> startActivity(AlfonzRestActivity.newIntent(SamplesActivity.this)));
		rxButton.setOnClickListener(view -> startActivity(AlfonzRxActivity.newIntent(SamplesActivity.this)));
		utilityButton.setOnClickListener(view -> startActivity(AlfonzUtilityActivity.newIntent(SamplesActivity.this)));
		viewButton.setOnClickListener(view -> startActivity(AlfonzViewActivity.newIntent(SamplesActivity.this)));
	}
}
