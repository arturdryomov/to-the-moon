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
