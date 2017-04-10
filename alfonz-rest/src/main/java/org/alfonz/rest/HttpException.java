package org.alfonz.rest;

import retrofit2.Response;


public abstract class HttpException extends retrofit2.HttpException
{
	private final Object mError;


	public HttpException(Response<?> response)
	{
		super(response);
		mError = parseError(response);
	}


	public abstract Object parseError(Response<?> response);


	public Object error()
	{
		return mError;
	}
}
