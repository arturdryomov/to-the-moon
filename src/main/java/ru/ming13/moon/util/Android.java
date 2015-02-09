package ru.ming13.moon.util;

import ru.ming13.moon.BuildConfig;

public final class Android
{
	private Android() {
	}

	public static boolean isDebugging() {
		return BuildConfig.DEBUG;
	}

	public static String getApplicationId() {
		return BuildConfig.APPLICATION_ID;
	}
}
