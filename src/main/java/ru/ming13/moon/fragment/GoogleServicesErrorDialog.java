package ru.ming13.moon.fragment;

import android.app.Dialog;
import android.app.DialogFragment;
import android.os.Bundle;

import com.f2prateek.dart.Dart;
import com.f2prateek.dart.InjectExtra;
import com.google.android.gms.common.GooglePlayServicesUtil;

import ru.ming13.moon.util.Fragments;

public class GoogleServicesErrorDialog extends DialogFragment
{
	public static final String TAG = "google_services_error_dialog";

	@InjectExtra(Fragments.Arguments.ERROR_CODE)
	int errorCode;

	@InjectExtra(Fragments.Arguments.REQUEST_CODE)
	int requestCode;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		setUpInjections();

		return GooglePlayServicesUtil.getErrorDialog(errorCode, getActivity(), requestCode);
	}

	private void setUpInjections() {
		Dart.inject(this);
	}
}
