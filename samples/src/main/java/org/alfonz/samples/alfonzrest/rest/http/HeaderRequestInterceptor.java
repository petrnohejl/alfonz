package org.alfonz.samples.alfonzrest.rest.http;

import androidx.annotation.NonNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HeaderRequestInterceptor implements Interceptor {
	@NonNull
	@Override
	public Response intercept(Chain chain) throws IOException {
		Request.Builder builder = chain.request().newBuilder();
		builder.addHeader("Accept", "application/json");
		builder.addHeader("Accept-Charset", "utf-8");
		builder.addHeader("Content-Type", "application/json");
		Request request = builder.build();
		return chain.proceed(request);
	}
}
