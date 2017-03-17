package org.alfonz.samples.alfonzrest.rest;

import org.alfonz.rest.HttpLogger;
import org.alfonz.utility.Logcat;


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
		Logcat.d(message);
	}
}
