package org.alfonz.arch.event;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;

@SuppressWarnings("NullableProblems")
public interface EventObserver<T> extends Observer<T> {
	void onChanged(@NonNull T event);
}
