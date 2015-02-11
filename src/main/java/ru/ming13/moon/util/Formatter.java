package ru.ming13.moon.util;

import java.text.NumberFormat;

public final class Formatter
{
	private Formatter() {
	}

	public static String formatDistance(double distance) {
		return String.format("%.0f", distance);
	}

	public static String formatPercent(double percent) {
		NumberFormat formatter = NumberFormat.getPercentInstance();

		formatter.setMaximumFractionDigits(3);

		return formatter.format(percent);
	}
}
