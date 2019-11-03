package org.alfonz.rest.rx;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import org.alfonz.rest.HttpException;
import org.alfonz.rest.HttpLogger;
import org.alfonz.rest.ResponseHandler;
import org.alfonz.rx.RxManager;
import org.alfonz.rx.utility.SchedulersUtility;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.Single;
import retrofit2.Response;

public class RestRxManager extends RxManager {
	private ResponseHandler mResponseHandler;
	private HttpLogger mHttpLogger;

	public RestRxManager(@NonNull ResponseHandler responseHandler) {
		mResponseHandler = responseHandler;
	}

	public RestRxManager(@NonNull ResponseHandler responseHandler, @Nullable HttpLogger httpLogger) {
		mResponseHandler = responseHandler;
		mHttpLogger = httpLogger;
	}

	public <T extends Response<?>> Observable<T> setupRestObservable(@NonNull Observable<T> restObservable, @NonNull String callType) {
		return setupObservable(restObservable, callType)
				.flatMap(this::catchObservableHttpError)
				.doOnNext(response -> logSuccess(response, callType))
				.doOnError(throwable ->
				{
					if (throwable instanceof HttpException) {
						logError((HttpException) throwable, callType);
					} else {
						logFail(throwable, callType);
					}
				});
	}

	public <T extends Response<?>> Observable<T> setupRestObservableWithSchedulers(@NonNull Observable<T> restObservable, @NonNull String callType) {
		return setupRestObservable(restObservable, callType).compose(SchedulersUtility.applyObservableSchedulers());
	}

	public <T extends Response<?>> Single<T> setupRestSingle(@NonNull Single<T> restSingle, @NonNull String callType) {
		return setupSingle(restSingle, callType)
				.flatMap(this::catchSingleHttpError)
				.doOnSuccess(response -> logSuccess(response, callType))
				.doOnError(throwable ->
				{
					if (throwable instanceof HttpException) {
						logError((HttpException) throwable, callType);
					} else {
						logFail(throwable, callType);
					}
				});
	}

	public <T extends Response<?>> Single<T> setupRestSingleWithSchedulers(@NonNull Single<T> restSingle, @NonNull String callType) {
		return setupRestSingle(restSingle, callType).compose(SchedulersUtility.applySingleSchedulers());
	}

	// this method does not use ResponseHandler, because it cannot access Response object
	// http errors are handled with retrofit2.HttpException
	public Completable setupRestCompletable(@NonNull Completable restCompletable, @NonNull String callType) {
		return setupCompletable(restCompletable, callType)
				.doOnComplete(() -> logSuccess(callType))
				.doOnError(throwable ->
				{
					if (throwable instanceof retrofit2.HttpException) {
						logError((retrofit2.HttpException) throwable, callType);
					} else {
						logFail(throwable, callType);
					}
				});
	}

	// this method does not use ResponseHandler, because it cannot access Response object
	// http errors are handled with retrofit2.HttpException
	public Completable setupRestCompletableWithSchedulers(@NonNull Completable restCompletable, @NonNull String callType) {
		return setupRestCompletable(restCompletable, callType).compose(SchedulersUtility.applyCompletableSchedulers());
	}

	public String getHttpErrorMessage(@NonNull Throwable throwable) {
		if (throwable instanceof HttpException) {
			return mResponseHandler.getErrorMessage((HttpException) throwable);
		} else {
			throwable.printStackTrace();
			return mResponseHandler.getFailMessage(throwable);
		}
	}

	public <T extends Response<?>> Observable<T> catchObservableHttpError(@NonNull T response) {
		if (mResponseHandler.isSuccess(response)) {
			return Observable.just(response);
		} else {
			return Observable.error(mResponseHandler.createHttpException(response));
		}
	}

	public <T extends Response<?>> Single<T> catchSingleHttpError(@NonNull T response) {
		if (mResponseHandler.isSuccess(response)) {
			return Single.just(response);
		} else {
			return Single.error(mResponseHandler.createHttpException(response));
		}
	}

	private void logSuccess(@NonNull String callType) {
		if (mHttpLogger != null) {
			String message = String.format("%s call succeed", callType);
			mHttpLogger.logSuccess(message);
		}
	}

	private void logSuccess(@NonNull Response<?> response, @NonNull String callType) {
		if (mHttpLogger != null) {
			String status = response.code() + " " + response.message();
			String result = response.body() != null ? response.body().getClass().getSimpleName() : "empty body";
			String message = String.format("%s call succeed with %s: %s", callType, status, result);
			mHttpLogger.logSuccess(message);
		}
	}

	private void logError(@NonNull HttpException exception, @NonNull String callType) {
		if (mHttpLogger != null) {
			String status = exception.code() + " " + exception.message();
			String result = mResponseHandler.getErrorMessage(exception);
			String message = String.format("%s call err with %s: %s", callType, status, result);
			mHttpLogger.logError(message);
		}
	}

	private void logError(@NonNull retrofit2.HttpException exception, @NonNull String callType) {
		if (mHttpLogger != null) {
			String status = exception.code() + " " + exception.message();
			String message = String.format("%s call err with %s", callType, status);
			mHttpLogger.logError(message);
		}
	}

	private void logFail(@NonNull Throwable throwable, @NonNull String callType) {
		if (mHttpLogger != null) {
			String exceptionName = throwable.getClass().getSimpleName();
			String exceptionMessage = throwable.getMessage();
			String message = String.format("%s call fail with %s exception: %s", callType, exceptionName, exceptionMessage);
			mHttpLogger.logFail(message);
		}
	}
}
