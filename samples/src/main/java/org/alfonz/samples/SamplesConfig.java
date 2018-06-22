package org.alfonz.samples;

public class SamplesConfig {
	public static final boolean LOGS = BuildConfig.LOGS;
	public static final boolean DEV_ENVIRONMENT = BuildConfig.DEV_ENVIRONMENT;

	public static final String REST_BASE_URL_PROD = "https://api.github.com/";
	public static final String REST_BASE_URL_DEV = "https://api.github.com/";
	public static final String REST_BASE_URL = SamplesConfig.DEV_ENVIRONMENT ? SamplesConfig.REST_BASE_URL_DEV : SamplesConfig.REST_BASE_URL_PROD;
}
