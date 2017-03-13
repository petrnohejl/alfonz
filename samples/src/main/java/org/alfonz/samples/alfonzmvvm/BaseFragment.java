package org.alfonz.samples.alfonzmvvm;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.StringRes;
import android.support.design.widget.Snackbar;
import android.widget.Toast;

import org.alfonz.mvvm.AlfonzBindingFragment;


public abstract class BaseFragment<T extends BaseView, R extends BaseViewModel<T>, B extends ViewDataBinding> extends AlfonzBindingFragment<T, R, B> implements BaseView
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}


	@Override
	public void showToast(@StringRes int stringRes)
	{
		Toast.makeText(getActivity(), stringRes, Toast.LENGTH_LONG).show();
	}


	@Override
	public void showToast(String message)
	{
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}


	@Override
	public void showSnackbar(@StringRes int stringRes)
	{
		if(getView() != null)
		{
			Snackbar.make(getView(), stringRes, Snackbar.LENGTH_LONG).show();
		}
	}


	@Override
	public void showSnackbar(String message)
	{
		if(getView() != null)
		{
			Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
		}
	}
}
