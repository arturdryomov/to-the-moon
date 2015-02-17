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
