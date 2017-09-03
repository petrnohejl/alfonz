package org.alfonz.samples.alfonzarch.event;

import org.alfonz.arch.event.Event;


public class SnackbarEvent extends Event
{
	public final String message;


	public SnackbarEvent(String message)
	{
		this.message = message;
	}
}
