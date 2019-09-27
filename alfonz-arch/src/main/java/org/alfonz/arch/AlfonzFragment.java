package org.alfonz.arch;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public abstract class AlfonzFragment<T extends AlfonzViewModel> extends Fragment implements AlfonzView {
	private T mViewModel;

	public abstract T setupViewModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = setupViewModel();
	}

	public boolean onBackPressed() {
		// return true if back press was handled
		return false;
	}

	public T getViewModel() {
		return mViewModel;
	}

	@Nullable
	public AlfonzActivity getAlfonzActivity() {
		return (AlfonzActivity) getActivity();
	}
}
