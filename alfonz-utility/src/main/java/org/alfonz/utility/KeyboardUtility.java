package org.alfonz.utility;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;


public final class KeyboardUtility
{
	private KeyboardUtility() {}


	public static void showKeyboard(@NonNull View view)
	{
		view.requestFocus();
		InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.showSoftInput(view, 0);
	}


	public static void hideKeyboard(@NonNull View view)
	{
		InputMethodManager inputMethodManager = (InputMethodManager) view.getContext().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
		inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
	}
}
