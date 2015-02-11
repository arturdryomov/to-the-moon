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
