package ru.ming13.moon.util;

import java.text.Format;
import java.text.NumberFormat;

public final class Formatter
{
	private final Format distanceFormatter;
	private final Format percentageFormatter;

	public static Formatter get() {
		return new Formatter();
	}

	private Formatter() {
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
		return distanceFormatter.format((int) distance);
	}

	public String formatPercentage(double percentage) {
		return percentageFormatter.format(percentage);
	}
}
