package org.alfonz.samples;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import org.alfonz.samples.alfonzadapter.AdapterSampleActivity;
import org.alfonz.samples.alfonzarch.ArchSampleActivity;
import org.alfonz.samples.alfonzgraphics.GraphicsSampleActivity;
import org.alfonz.samples.alfonzmedia.MediaSampleActivity;
import org.alfonz.samples.alfonzrest.RestSampleActivity;
import org.alfonz.samples.alfonzrx.RxSampleActivity;
import org.alfonz.samples.alfonzutility.UtilitySampleActivity;
import org.alfonz.samples.alfonzview.ViewSampleActivity;

public class SamplesActivity extends AppCompatActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_samples);
		setupButtons();
	}

	private void setupButtons() {
		Button adapterButton = findViewById(R.id.samples_adapter);
		Button archButton = findViewById(R.id.samples_arch);
		Button graphicsButton = findViewById(R.id.samples_graphics);
		Button mediaButton = findViewById(R.id.samples_media);
		Button restButton = findViewById(R.id.samples_rest);
		Button rxButton = findViewById(R.id.samples_rx);
		Button utilityButton = findViewById(R.id.samples_utility);
		Button viewButton = findViewById(R.id.samples_view);

		adapterButton.setOnClickListener(view -> startActivity(AdapterSampleActivity.newIntent(SamplesActivity.this)));
		archButton.setOnClickListener(view -> startActivity(ArchSampleActivity.newIntent(SamplesActivity.this)));
		graphicsButton.setOnClickListener(view -> startActivity(GraphicsSampleActivity.newIntent(SamplesActivity.this)));
		mediaButton.setOnClickListener(view -> startActivity(MediaSampleActivity.newIntent(SamplesActivity.this)));
		restButton.setOnClickListener(view -> startActivity(RestSampleActivity.newIntent(SamplesActivity.this)));
		rxButton.setOnClickListener(view -> startActivity(RxSampleActivity.newIntent(SamplesActivity.this)));
		utilityButton.setOnClickListener(view -> startActivity(UtilitySampleActivity.newIntent(SamplesActivity.this)));
		viewButton.setOnClickListener(view -> startActivity(ViewSampleActivity.newIntent(SamplesActivity.this)));
	}
}
