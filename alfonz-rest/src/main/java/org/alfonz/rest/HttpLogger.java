package org.alfonz.rest;

import androidx.annotation.NonNull;

public interface HttpLogger {
	void logSuccess(@NonNull String message);
	void logError(@NonNull String message);
	void logFail(@NonNull String message);
}
