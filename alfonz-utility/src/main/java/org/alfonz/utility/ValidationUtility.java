package org.alfonz.utility;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Patterns;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Locale;


public final class ValidationUtility
{
	private ValidationUtility() {}


	public static boolean isEmailValid(@Nullable CharSequence email)
	{
		return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
	}


	public static boolean isDateValid(@Nullable String date, @NonNull String format)
	{
		if(date == null)
		{
			return false;
		}

		SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.US);
		simpleDateFormat.setLenient(false);

		try
		{
			simpleDateFormat.parse(date);
		}
		catch(ParseException e)
		{
			e.printStackTrace();
			return false;
		}

		return true;
	}
}
