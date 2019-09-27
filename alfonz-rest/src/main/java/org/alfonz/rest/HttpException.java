package org.alfonz.rest;

import androidx.annotation.NonNull;
import retrofit2.Response;

public abstract class HttpException extends retrofit2.HttpException {
	private final Object mError;

	public HttpException(@NonNull Response<?> response) {
		super(response);
		mError = parseError(response);
	}

	public abstract Object parseError(@NonNull Response<?> response);

	public Object error() {
		return mError;
	}
}
