package org.alfonz.rest;

import android.support.annotation.NonNull;

public interface HttpLogger {
	void logSuccess(@NonNull String message);
	void logError(@NonNull String message);
	void logFail(@NonNull String message);
}
