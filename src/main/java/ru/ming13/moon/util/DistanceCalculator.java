package ru.ming13.moon.util;

public final class DistanceCalculator
{
	private static final class Distances
	{
		private Distances() {
		}

		public static final int EARTH_TO_MOON = 384_400 * 1000;
	}

	private DistanceCalculator() {
	}

	public static double calculateMoonDistancePercentage(double distance) {
		return distance / Distances.EARTH_TO_MOON;
	}
}
