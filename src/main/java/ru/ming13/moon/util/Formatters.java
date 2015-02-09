package ru.ming13.moon.util;

import java.text.NumberFormat;

public final class Formatters
{
	private Formatters() {
	}

	public static String formatPercent(double percent) {
		NumberFormat percentFormat = NumberFormat.getPercentInstance();

		percentFormat.setMaximumFractionDigits(3);

		return percentFormat.format(percent);
	}
}
