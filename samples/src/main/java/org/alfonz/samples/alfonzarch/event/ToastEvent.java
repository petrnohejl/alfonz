package org.alfonz.samples.alfonzarch.event;

import org.alfonz.arch.event.Event;

public class ToastEvent extends Event {
	public final String message;

	public ToastEvent(String message) {
		this.message = message;
	}
}
