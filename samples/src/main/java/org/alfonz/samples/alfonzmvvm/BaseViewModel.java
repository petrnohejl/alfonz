package org.alfonz.samples.alfonzmvvm;

import org.alfonz.mvvm.AlfonzViewModel;


public abstract class BaseViewModel<T extends BaseView> extends AlfonzViewModel<T>
{
	public void handleError(String message)
	{
		if(getView() != null)
		{
			getView().showToast(message);
		}
	}
}
