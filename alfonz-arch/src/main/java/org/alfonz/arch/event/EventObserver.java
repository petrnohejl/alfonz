package org.alfonz.arch.event;

import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;


@SuppressWarnings("NullableProblems")
public interface EventObserver<T> extends Observer<T>
{
	void onChanged(@NonNull T event);
}
