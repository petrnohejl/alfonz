package org.alfonz.rest;

import androidx.annotation.NonNull;

import retrofit2.Response;

public interface ResponseHandler {
	boolean isSuccess(@NonNull Response<?> response);
	String getErrorMessage(@NonNull HttpException exception);
	String getFailMessage(@NonNull Throwable throwable);
	HttpException createHttpException(@NonNull Response<?> response);
}
