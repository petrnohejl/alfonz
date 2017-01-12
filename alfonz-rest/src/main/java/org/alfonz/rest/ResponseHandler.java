package org.alfonz.rest;

import retrofit2.Response;


public interface ResponseHandler
{
	boolean isSuccess(Response<?> response);
	String getErrorMessage(HttpException exception);
	String getFailMessage(Throwable throwable);
	HttpException createHttpException(Response<?> response);
}
