package org.alfonz.rest.call;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.HttpLogger;
import org.alfonz.rest.ResponseHandler;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CallManager extends BaseCallManager {
	private ResponseHandler mResponseHandler;
	private HttpLogger mHttpLogger;

	public CallManager(@NonNull ResponseHandler responseHandler) {
		mResponseHandler = responseHandler;
	}

	public CallManager(@NonNull ResponseHandler responseHandler, @Nullable HttpLogger httpLogger) {
		mResponseHandler = responseHandler;
		mHttpLogger = httpLogger;
	}

	public String getHttpErrorMessage(@NonNull Throwable throwable) {
		if (throwable instanceof HttpException) {
			return mResponseHandler.getErrorMessage((HttpException) throwable);
		} else {
			throwable.printStackTrace();
			return mResponseHandler.getFailMessage(throwable);
		}
	}

	@NonNull
	ResponseHandler getResponseHandler() {
		return mResponseHandler;
	}

	@Nullable
	HttpLogger getHttpLogger() {
		return mHttpLogger;
	}
}
