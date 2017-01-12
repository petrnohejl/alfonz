package org.alfonz.rest.call;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.HttpLogger;
import org.alfonz.rest.ResponseHandler;


public class CallManager extends BaseCallManager
{
	private ResponseHandler mResponseHandler;
	private HttpLogger mHttpLogger;


	public CallManager(@NonNull ResponseHandler responseHandler)
	{
		mResponseHandler = responseHandler;
	}


	public CallManager(@NonNull ResponseHandler responseHandler, @Nullable HttpLogger httpLogger)
	{
		mResponseHandler = responseHandler;
		mHttpLogger = httpLogger;
	}


	public String getHttpErrorMessage(Throwable throwable)
	{
		if(throwable instanceof HttpException)
		{
			return mResponseHandler.getErrorMessage((HttpException) throwable);
		}
		else
		{
			throwable.printStackTrace();
			return mResponseHandler.getFailMessage(throwable);
		}
	}


	@NonNull
	ResponseHandler getResponseHandler()
	{
		return mResponseHandler;
	}


	@Nullable
	HttpLogger getHttpLogger()
	{
		return mHttpLogger;
	}
}
