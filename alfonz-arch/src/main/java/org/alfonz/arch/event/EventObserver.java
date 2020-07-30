package org.alfonz.arch.event;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

public interface EventObserver<T> extends Observer<T> {
	void onChanged(@NonNull T event);
}
