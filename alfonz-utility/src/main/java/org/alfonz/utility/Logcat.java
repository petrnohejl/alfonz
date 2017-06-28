package org.alfonz.utility;

import android.util.Log;


public final class Logcat
{
	private static Config sConfig;


	private Logcat() {}


	public static void init(Config config)
	{
		sConfig = config;
	}


	public static void init(boolean enabled, String tag)
	{
		sConfig = new Config.Builder().setEnabled(enabled).setTag(tag).build();
	}


	public static void d(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.d(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void e(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.e(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void e(Throwable throwable, String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.e(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args), throwable);
	}


	public static void i(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.i(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void v(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.v(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void w(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.w(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void wtf(String msg, Object... args)
	{
		if(sConfig.isEnabled()) Log.wtf(sConfig.getTag(), getCodeLocation().toString() + formatMessage(msg, args));
	}


	public static void printStackTrace(Throwable throwable)
	{
		if(sConfig.isEnabled()) Log.e(sConfig.getTag(), getCodeLocation().toString(), throwable);
	}


	private static String formatMessage(String msg, Object... args)
	{
		return args.length == 0 ? msg : String.format(msg, args);
	}


	private static CodeLocation getCodeLocation()
	{
		return getCodeLocation(3);
	}


	private static CodeLocation getCodeLocation(int depth)
	{
		StackTraceElement[] stackTrace = new Throwable().getStackTrace();
		StackTraceElement[] filteredStackTrace = new StackTraceElement[stackTrace.length - depth];
		System.arraycopy(stackTrace, depth, filteredStackTrace, 0, filteredStackTrace.length);
		return new CodeLocation(filteredStackTrace);
	}


	public static class Config
	{
		private boolean mEnabled;
		private String mTag;
		private boolean mShowCodeLocation;
		private boolean mShowCodeLocationThread;
		private boolean mShowCodeLocationLine;


		private Config(boolean enabled, String tag, boolean showCodeLocation, boolean showCodeLocationThread, boolean showCodeLocationLine)
		{
			mEnabled = enabled;
			mTag = tag;
			mShowCodeLocation = showCodeLocation;
			mShowCodeLocationThread = showCodeLocationThread;
			mShowCodeLocationLine = showCodeLocationLine;
		}


		public boolean isEnabled()
		{
			return mEnabled;
		}


		public String getTag()
		{
			return mTag;
		}


		public boolean isShowCodeLocation()
		{
			return mShowCodeLocation;
		}


		public boolean isShowCodeLocationThread()
		{
			return mShowCodeLocationThread;
		}


		public boolean isShowCodeLocationLine()
		{
			return mShowCodeLocationLine;
		}


		public static class Builder
		{
			private boolean mEnabled = false;
			private String mTag = "LOGCAT";
			private boolean mShowCodeLocation = true;
			private boolean mShowCodeLocationThread = false;
			private boolean mShowCodeLocationLine = false;


			public Builder setEnabled(boolean enabled)
			{
				mEnabled = enabled;
				return this;
			}


			public Builder setTag(String tag)
			{
				mTag = tag;
				return this;
			}


			public Builder setShowCodeLocation(boolean showCodeLocation)
			{
				mShowCodeLocation = showCodeLocation;
				return this;
			}


			public Builder setShowCodeLocationThread(boolean showCodeLocationThread)
			{
				mShowCodeLocationThread = showCodeLocationThread;
				return this;
			}


			public Builder setShowCodeLocationLine(boolean showCodeLocationLine)
			{
				mShowCodeLocationLine = showCodeLocationLine;
				return this;
			}


			public Config build()
			{
				return new Config(mEnabled, mTag, mShowCodeLocation, mShowCodeLocationThread, mShowCodeLocationLine);
			}
		}
	}


	private static class CodeLocation
	{
		private final String mThread;
		private final String mFileName;
		private final String mClassName;
		private final String mMethod;
		private final int mLineNumber;


		CodeLocation(StackTraceElement[] stackTrace)
		{
			StackTraceElement root = stackTrace[0];
			mThread = Thread.currentThread().getName();
			mFileName = root.getFileName();
			String className = root.getClassName();
			mClassName = className.substring(className.lastIndexOf('.') + 1);
			mMethod = root.getMethodName();
			mLineNumber = root.getLineNumber();
		}


		@Override
		public String toString()
		{
			StringBuilder builder = new StringBuilder();
			if(sConfig.isShowCodeLocation())
			{
				builder.append('[');
				if(sConfig.isShowCodeLocationThread())
				{
					builder.append(mThread);
					builder.append('.');
				}
				builder.append(mClassName);
				builder.append('.');
				builder.append(mMethod);
				if(sConfig.isShowCodeLocationLine())
				{
					builder.append('(');
					builder.append(mFileName);
					builder.append(':');
					builder.append(mLineNumber);
					builder.append(')');
				}
				builder.append("] ");
			}
			return builder.toString();
		}
	}
}
