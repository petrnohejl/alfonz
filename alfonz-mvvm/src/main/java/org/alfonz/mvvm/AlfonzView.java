package org.alfonz.mvvm;

import android.os.Bundle;

import eu.inloop.viewmodel.IView;


@Deprecated
public interface AlfonzView extends IView
{
	Bundle getExtras();
}
