package org.alfonz.arch;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

public abstract class AlfonzFragment<T extends AlfonzViewModel> extends Fragment implements AlfonzView {
	private T mViewModel;

	public abstract T setupViewModel();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewModel = setupViewModel();
	}

	/**
	 * @return whether back press was consumed, default = false
	 */
	public boolean onBackPressed() {
		// checks all it's child fragments for handling onBackPressed
		return AlfonzActivity.fragmentHandleOnBackPressed(getChildFragmentManager());
	}

	public T getViewModel() {
		return mViewModel;
	}

	@Nullable
	public AlfonzActivity getAlfonzActivity() {
		return (AlfonzActivity) getActivity();
	}
}
