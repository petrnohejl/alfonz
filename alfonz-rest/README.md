Alfonz - REST Module
====================

Helper classes for managing REST API calls.

The purpose of this module is to simplify work with REST API using [Retrofit](https://github.com/square/retrofit) library. This module provides helpers and utilities for handling responses, errors, exceptions and logging results. There are 2 ways how you can manage REST API calls:

* `RestRxManager` which uses `io.reactivex.Observable`
* `CallManager` which uses `retrofit2.Call`

This document will cover only the first option which is recommended. See the samples or the source code for more info about the second way. `RestRxManager` extends `RxManager` from the Alfonz Rx Module and it uses all the features of this helper class.


How to use
----------

First of all, create a class for building and storing Retrofit instance. I recommend to implement it as a singleton. Then implement Retrofit services and interfaces which will represent your REST API calls. See samples or Retrofit documentation for more info. Methods in the interface have to return `Observable<Response<T>>` or `Single<Response<T>>` or other specialized reactive base type. Also define an identifier for each call.

```java
public static final String MESSAGE_CALL_TYPE = "message";

public interface ChatService
{
	@GET("message/{id}")
	Single<Response<MessageEntity>> message(@Path("id") String id, @Query("lang") String lang);
}
```

Now you have to implement two classes which will be used by the `RestRxManager` and which will define how to handle responses and errors.

Let's start with `HttpException`. You have to implement how to parse an error body when a response is not successful.

```java
public class RestHttpException extends HttpException
{
	public RestHttpException(Response<?> response)
	{
		super(response);
	}

	@Override
	public Object parseError(Response<?> response)
	{
		try
		{
			Converter<ResponseBody, ErrorEntity> converter =
					RetrofitClient.getRetrofit().responseBodyConverter(ErrorEntity.class, new Annotation[0]);
			return converter.convert(response.errorBody());
		}
		catch(IOException | NullPointerException e)
		{
			e.printStackTrace();
			ErrorEntity error = new ErrorEntity();
			error.setMessage("Unknown error");
			return error;
		}
	}
}
```

Now implement `ResponseHandler`. Define a successful response, error message, fail message and factory method for the `HttpException`.

```java
public class RestResponseHandler implements ResponseHandler
{
	@Override
	public boolean isSuccess(Response<?> response)
	{
		return response.isSuccessful();
	}

	@Override
	public String getErrorMessage(HttpException exception)
	{
		ErrorEntity error = (ErrorEntity) exception.error();
		return error.getMessage();
	}

	@Override
	public String getFailMessage(Throwable throwable)
	{
		return throwable.getMessage();
	}

	@Override
	public HttpException createHttpException(Response<?> response)
	{
		return new RestHttpException(response);
	}
}
```

You can also implement `HttpLogger` for logging success, error and fail states of REST API calls, but it is optional.

```java
public class RestHttpLogger implements HttpLogger
{
	@Override
	public void logSuccess(String message)
	{
		Logcat.d(message);
	}

	@Override
	public void logError(String message)
	{
		Logcat.d(message);
	}

	@Override
	public void logFail(String message)
	{
		Logcat.e(message);
	}
}
```

That's it. Now you can use the `RestRxManager` in your ViewModel to manage REST API calls. It works similarly to `RxManager`.

```java
private RestRxManager mRestRxManager = new RestRxManager(new RestResponseHandler(), new RestHttpLogger());
```

Create a new instance of `Observer`.

```java
private DisposableSingleObserver<Response<MessageEntity>> createMessageObserver()
{
	return AlfonzDisposableSingleObserver.newInstance(
			data ->
			{
				// show data
			},
			throwable ->
			{
				// error
				handleError(mRestRxManager.getHttpErrorMessage(throwable));
			}
	);
}
```

Run RxJava REST API call. In the following example, we will check if a call of the specific type is already running. If not, we will execute it, otherwise we will do nothing. We will create a `Single`, set it up with Schedulers and subscribe with the `Observer`. The call is automatically registered on subcribe so we can check which specific calls are currently running. After the RxJava task terminates, the call is automatically unregistered. Disposables are automatically stored in `CompositeDisposable` container. Schedulers should be applied at the end of the stream, right before the `subscribeWith()` is called. `RestRxManager` takes care of handling responses, errors, exceptions and logging results.

```java
private void runMessageCall()
{
	String callType = ChatProvider.MESSAGE_CALL_TYPE;
	if(!mRestRxManager.isRunning(callType))
	{
		Single<Response<MessageEntity>> rawSingle = ChatProvider.getService().message("42", "en");
		Single<Response<MessageEntity>> single =
				mRestRxManager.setupRestSingleWithSchedulers(rawSingle, callType);
		single.subscribeWith(createMessageObserver());
	}
}
```

Note that setup methods in `RestRxManager` which work with `Completable` reactive base type don't use `ResponseHandler` for managing errors and exceptions. It's because `Completable` doesn't provide `Response` object. HTTP errors are handled with `retrofit2.HttpException` in this case.

Don't forget to dispose all the Disposables when you don't need them anymore.

```java
@Override
public void onDestroy()
{
	super.onDestroy();
	mRestRxManager.disposeAll();
}
```


Dependencies
------------

* Alfonz Rx Module
* Android Support Library
* [OkHttp](https://github.com/square/okhttp)
* [Retrofit](https://github.com/square/retrofit)
* [RxJava](https://github.com/ReactiveX/RxJava)


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
