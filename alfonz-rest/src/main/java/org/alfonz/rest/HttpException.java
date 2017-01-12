package org.alfonz.rest;

import retrofit2.Response;


// inspired by: retrofit2.adapter.rxjava.HttpException
public abstract class HttpException extends Exception
{
	private final int mCode;
	private final String mMessage;
	private final transient Response<?> mResponse;
	private final Object mError;


	public HttpException(Response<?> response)
	{
		super("HTTP " + response.code() + " " + response.message());
		mCode = response.code();
		mMessage = response.message();
		mResponse = response;
		mError = parseError(response);
	}


	public abstract Object parseError(Response<?> response);


	public int code()
	{
		return mCode;
	}


	public String message()
	{
		return mMessage;
	}


	public Response<?> response()
	{
		return mResponse;
	}


	public Object error()
	{
		return mError;
	}
}
