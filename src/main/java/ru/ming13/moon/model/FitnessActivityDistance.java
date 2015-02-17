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

package ru.ming13.moon.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FitnessActivityDistance implements Parcelable
{
	private final FitnessActivity activity;

	private double distance;

	public FitnessActivityDistance(FitnessActivity activity) {
		this.activity = activity;

		this.distance = 0;
	}

	public void plus(float distance) {
		this.distance += distance;
	}

	public FitnessActivity getActivity() {
		return activity;
	}

	public double getDistance() {
		return distance;
	}

	public static Creator<FitnessActivityDistance> CREATOR = new Creator<FitnessActivityDistance>() {
		@Override
		public FitnessActivityDistance createFromParcel(Parcel parcel) {
			return new FitnessActivityDistance(parcel);
		}

		@Override
		public FitnessActivityDistance[] newArray(int size) {
			return new FitnessActivityDistance[size];
		}
	};

	private FitnessActivityDistance(Parcel parcel) {
		this.activity = parcel.readParcelable(FitnessActivity.class.getClassLoader());
		this.distance = parcel.readDouble();
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeParcelable(activity, flags);
		parcel.writeDouble(distance);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
