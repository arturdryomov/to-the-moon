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
import android.support.annotation.NonNull;

import java.text.Format;
import java.text.NumberFormat;

import ru.ming13.moon.R;

public final class Formatter
{
	private final Context context;

	private final Format distanceFormatter;
	private final Format percentageFormatter;

	public static Formatter with(@NonNull Context context) {
		return new Formatter(context);
	}

	private Formatter(Context context) {
		this.context = context.getApplicationContext();

		this.distanceFormatter = getDistanceFormatter();
		this.percentageFormatter = getPercentageFormatter();
	}

	private Format getDistanceFormatter() {
		return NumberFormat.getNumberInstance();
	}

	private Format getPercentageFormatter() {
		NumberFormat formatter = NumberFormat.getPercentInstance();

		formatter.setMaximumFractionDigits(3);

		return formatter;
	}

	public String formatDistance(double distance) {
		return context.getString(R.string.mask_distance, distanceFormatter.format(distance));
	}

	public String formatPercentage(double percentage) {
		return percentageFormatter.format(percentage);
	}
}
