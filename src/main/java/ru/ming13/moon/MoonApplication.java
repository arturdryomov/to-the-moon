package ru.ming13.moon;

import android.app.Application;
import android.os.StrictMode;

import ru.ming13.moon.util.Android;

public class MoonApplication extends Application
{
	@Override
	public void onCreate() {
		super.onCreate();

		if (Android.isDebugging()) {
			setUpDetecting();
		}
	}

	private void setUpDetecting() {
		StrictMode.enableDefaults();
	}
}
