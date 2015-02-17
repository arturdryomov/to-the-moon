/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

	public boolean canResolve(@NonNull ConnectionResult connectionResult) {
		return connectionResult.hasResolution();
	}

	public void showResolutionAction(@NonNull ConnectionResult connectionResult) {
		try {
			connectionResult.startResolutionForResult(activity, Intents.Requests.GOOGLE_CONNECTION);
		} catch (IntentSender.SendIntentException e) {
			throw new RuntimeException(e);
		}
	}

	public void showResolutionError(@NonNull ConnectionResult connectionResult) {
		int errorCode = connectionResult.getErrorCode();
		int requestCode = Intents.Requests.GOOGLE_CONNECTION;

		DialogFragment errorDialog = Fragments.Builder.buildGoogleServicesErrorDialog(errorCode, requestCode);
		errorDialog.show(activity.getFragmentManager(), GoogleServicesErrorDialog.TAG);
	}
}
