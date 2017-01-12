package org.alfonz.rest.call;

import org.alfonz.rest.HttpException;

import retrofit2.Call;
import retrofit2.Response;


public abstract class Callback<T> implements retrofit2.Callback<T>
{
	private CallManager mCallManager;


	public Callback(CallManager callManager)
	{
		mCallManager = callManager;
	}


	public abstract void onSuccess(Call<T> call, Response<T> response);


	public abstract void onError(Call<T> call, HttpException exception);


	public abstract void onFail(Call<T> call, Throwable throwable);


	@Override
	public void onResponse(Call<T> call, Response<T> response)
	{
		if(mCallManager.getResponseHandler().isSuccess(response))
		{
			// log
			logSuccess(response, mCallManager.getCallType(this));

			// callback
			onSuccess(call, response);
		}
		else
		{
			// exception
			HttpException exception = mCallManager.getResponseHandler().createHttpException(response);

			// log
			logError(exception, mCallManager.getCallType(this));

			// callback
			onError(call, exception);
		}

		// finish call
		mCallManager.finishCall(this);
	}


	@Override
	public void onFailure(Call<T> call, Throwable throwable)
	{
		// log
		logFail(throwable, mCallManager.getCallType(this));

		// callback
		onFail(call, throwable);

		// finish call
		mCallManager.finishCall(this);
	}


	private void logSuccess(Response<T> response, String callType)
	{
		if(mCallManager.getHttpLogger() != null)
		{
			String status = response.code() + " " + response.message();
			String result = response.body() != null ? response.body().getClass().getSimpleName() : "empty body";
			String message = String.format("%s call succeed with %s: %s", callType, status, result);
			mCallManager.getHttpLogger().logSuccess(message);
		}
	}


	private void logError(HttpException exception, String callType)
	{
		if(mCallManager.getHttpLogger() != null)
		{
			String status = exception.code() + " " + exception.message();
			String result = mCallManager.getResponseHandler().getErrorMessage(exception);
			String message = String.format("%s call err with %s: %s", callType, status, result);
			mCallManager.getHttpLogger().logError(message);
		}
	}


	private void logFail(Throwable throwable, String callType)
	{
		if(mCallManager.getHttpLogger() != null)
		{
			String exceptionName = throwable.getClass().getSimpleName();
			String exceptionMessage = throwable.getMessage();
			String message = String.format("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
			mCallManager.getHttpLogger().logFail(message);
		}
	}
}
