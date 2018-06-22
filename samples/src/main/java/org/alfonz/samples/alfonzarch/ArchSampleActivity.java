package org.alfonz.samples.alfonzarch;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import org.alfonz.arch.widget.ToolbarIndicator;
import org.alfonz.samples.R;

public class ArchSampleActivity extends BaseActivity {
	public static Intent newIntent(Context context) {
		Intent intent = new Intent(context, ArchSampleActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		return intent;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_arch_sample);
		setupActionBar(ToolbarIndicator.BACK);
	}
}
