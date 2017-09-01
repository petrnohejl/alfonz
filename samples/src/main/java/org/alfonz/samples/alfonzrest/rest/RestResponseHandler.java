package org.alfonz.samples.alfonzrest.rest;

import android.net.ParseException;
import android.support.annotation.NonNull;

import com.google.gson.JsonParseException;
import com.google.gson.stream.MalformedJsonException;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.ResponseHandler;
import org.alfonz.samples.R;
import org.alfonz.samples.SamplesApplication;
import org.alfonz.samples.alfonzrest.entity.ErrorEntity;

import java.io.FileNotFoundException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import retrofit2.Response;


public class RestResponseHandler implements ResponseHandler
{
	@Override
	public boolean isSuccess(@NonNull Response<?> response)
	{
		return response.isSuccessful();
	}


	@Override
	public String getErrorMessage(@NonNull HttpException exception)
	{
		ErrorEntity error = (ErrorEntity) exception.error();
		return error.getMessage();
	}


	@Override
	public String getFailMessage(@NonNull Throwable throwable)
	{
		int resId;

		if(throwable instanceof UnknownHostException)
			resId = R.string.global_network_unknown_host;
		else if(throwable instanceof FileNotFoundException)
			resId = R.string.global_network_not_found;
		else if(throwable instanceof SocketTimeoutException)
			resId = R.string.global_network_timeout;
		else if(throwable instanceof JsonParseException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof MalformedJsonException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof ParseException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof NumberFormatException)
			resId = R.string.global_network_parse_fail;
		else if(throwable instanceof ClassCastException)
			resId = R.string.global_network_parse_fail;
		else
			resId = R.string.global_network_fail;
		
		return SamplesApplication.getContext().getString(resId);
	}


	@Override
	public HttpException createHttpException(@NonNull Response<?> response)
	{
		return new RestHttpException(response);
	}
}
