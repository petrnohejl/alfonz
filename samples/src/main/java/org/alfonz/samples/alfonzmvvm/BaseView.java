package org.alfonz.samples.alfonzmvvm;

import android.support.annotation.StringRes;

import org.alfonz.mvvm.AlfonzView;


public interface BaseView extends AlfonzView
{
	void showToast(@StringRes int stringRes);
	void showToast(String message);
	void showSnackbar(@StringRes int stringRes);
	void showSnackbar(String message);
}
