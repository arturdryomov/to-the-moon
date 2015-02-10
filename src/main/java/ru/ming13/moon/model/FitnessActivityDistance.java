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
