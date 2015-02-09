package ru.ming13.moon.util;

import android.app.Activity;
import android.app.DialogFragment;
import android.content.IntentSender;
import android.support.annotation.NonNull;

import com.google.android.gms.common.ConnectionResult;

import ru.ming13.moon.fragment.GoogleServicesErrorDialog;

public final class GoogleServices
{
	private final Activity activity;

	public static GoogleServices with(@NonNull Activity activity) {
		return new GoogleServices(activity);
	}

	private GoogleServices(Activity activity) {
		this.activity = activity;
	}

	public void resolve(@NonNull ConnectionResult connectionResult) {
		if (connectionResult.hasResolution()) {
			showResolutionAction(connectionResult);
		} else {
			showResolutionError(connectionResult);
		}
	}

	private void showResolutionAction(ConnectionResult connectionResult) {
		try {
			connectionResult.startResolutionForResult(activity, Intents.Requests.GOOGLE_CONNECTION);
		} catch (IntentSender.SendIntentException e) {
			throw new RuntimeException(e);
		}
	}

	private void showResolutionError(ConnectionResult connectionResult) {
		int errorCode = connectionResult.getErrorCode();
		int requestCode = Intents.Requests.GOOGLE_CONNECTION;

		DialogFragment errorDialog = Fragments.Builder.buildGoogleServicesErrorDialog(errorCode, requestCode);
		errorDialog.show(activity.getFragmentManager(), GoogleServicesErrorDialog.TAG);
	}
}
