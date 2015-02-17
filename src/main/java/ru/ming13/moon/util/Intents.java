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

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;

import ru.ming13.moon.R;

public final class Intents
{
	private Intents() {
	}

	public static final class Requests
	{
		private Requests() {
		}

		public static final int GOOGLE_CONNECTION = 1;
	}

	private static final class UriMasks
	{
		private UriMasks() {
		}

		public static final String EMAIL = "mailto:%s?subject=%s";

		public static final String GOOGLE_PLAY_APP = "market://details?id=%s";
		public static final String GOOGLE_PLAY_WEB = "https://play.google.com/store/apps/details?id=%s";
	}

	private static final class Types
	{
		private Types() {
		}

		public static final String TEXT = "text/plain";
	}

	public static final class Builder
	{
		private final Context context;

		public static Builder with(@NonNull Context context) {
			return new Builder(context);
		}

		private Builder(Context context) {
			this.context = context.getApplicationContext();
		}

		public Intent buildShareIntent(@NonNull String message) {
			Intent intent = new Intent(Intent.ACTION_SEND);
			intent.putExtra(Intent.EXTRA_TEXT, message);

			intent.setType(Types.TEXT);

			return Intent.createChooser(intent, null);
		}

		public Intent buildFeedbackIntent() {
			String feedbackAddress = context.getString(R.string.email_feedback_address);
			String feedbackSubject = context.getString(R.string.email_feedback_subject);

			String feedbackUri = String.format(UriMasks.EMAIL, feedbackAddress, feedbackSubject);

			return new Intent(Intent.ACTION_SENDTO, Uri.parse(feedbackUri));
		}

		public Intent buildGooglePlayAppIntent() {
			String googlePlayUri = String.format(UriMasks.GOOGLE_PLAY_APP, Android.getApplicationId());

			return new Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayUri));
		}

		public Intent buildGooglePlayWebIntent() {
			String googlePlayUri = String.format(UriMasks.GOOGLE_PLAY_WEB, Android.getApplicationId());

			return new Intent(Intent.ACTION_VIEW, Uri.parse(googlePlayUri));
		}
	}
}
