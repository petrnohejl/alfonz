package org.alfonz.rest;


public interface HttpLogger
{
	void logSuccess(String message);
	void logError(String message);
	void logFail(String message);
}
