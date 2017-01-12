package org.alfonz.rest.http;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okio.Buffer;
import okio.BufferedSink;
import okio.GzipSink;
import okio.Okio;


public class GzipRequestInterceptor implements Interceptor
{
	@Override
	public Response intercept(Chain chain) throws IOException
	{
		Request originalRequest = chain.request();

		if(originalRequest.body() == null || originalRequest.header("Content-Encoding") != null)
		{
			return chain.proceed(originalRequest);
		}

		Request compressedRequest = originalRequest.newBuilder()
				.header("Content-Encoding", "gzip")
				.method(originalRequest.method(), forceContentLength(gzip(originalRequest.body())))
				.build();

		return chain.proceed(compressedRequest);
	}


	private RequestBody gzip(final RequestBody body)
	{
		return new RequestBody()
		{
			@Override
			public MediaType contentType()
			{
				return body.contentType();
			}


			@Override
			public long contentLength()
			{
				// we don't know the compressed length in advance
				return -1;
			}


			@Override
			public void writeTo(BufferedSink sink) throws IOException
			{
				BufferedSink gzipSink = Okio.buffer(new GzipSink(sink));
				body.writeTo(gzipSink);
				gzipSink.close();
			}
		};
	}


	private RequestBody forceContentLength(final RequestBody requestBody) throws IOException
	{
		final Buffer buffer = new Buffer();
		requestBody.writeTo(buffer);
		return new RequestBody()
		{
			@Override
			public MediaType contentType()
			{
				return requestBody.contentType();
			}


			@Override
			public long contentLength()
			{
				return buffer.size();
			}


			@Override
			public void writeTo(BufferedSink sink) throws IOException
			{
				sink.write(buffer.snapshot());
			}
		};
	}
}
