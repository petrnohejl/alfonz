package org.alfonz.mvvm;

import android.os.Bundle;

import eu.inloop.viewmodel.IView;


public interface AlfonzView extends IView
{
	Bundle getExtras();
}
