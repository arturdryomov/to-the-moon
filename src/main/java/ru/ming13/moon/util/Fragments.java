package ru.ming13.moon.util;

import android.app.DialogFragment;

import com.f2prateek.bundler.Bundler;

import ru.ming13.moon.fragment.GoogleServicesErrorDialog;

public final class Fragments
{
	private Fragments() {
	}

	public static final class Arguments
	{
		private Arguments() {
		}

		public static final String ERROR_CODE = "error_code";
		public static final String REQUEST_CODE = "request_code";
	}

	public static final class Builder
	{
		private Builder() {
		}

		public static DialogFragment buildGoogleServicesErrorDialog(int errorCode, int requestCode) {
			DialogFragment fragment = new GoogleServicesErrorDialog();

			fragment.setArguments(Bundler.create()
				.put(Arguments.ERROR_CODE, errorCode)
				.put(Arguments.REQUEST_CODE, requestCode)
				.get());

			return fragment;
		}
	}
}
