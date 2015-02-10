package ru.ming13.moon.model;

import android.os.Parcel;
import android.os.Parcelable;

public class FitnessActivityDistances implements Parcelable
{
	private final FitnessActivityDistance walkingDistance;
	private final FitnessActivityDistance runningDistance;
	private final FitnessActivityDistance bikingDistance;

	public FitnessActivityDistances() {
		this.walkingDistance = new FitnessActivityDistance(FitnessActivity.WALKING);
		this.runningDistance = new FitnessActivityDistance(FitnessActivity.RUNNING);
		this.bikingDistance = new FitnessActivityDistance(FitnessActivity.BIKING);
	}

	public FitnessActivityDistance getWalkingDistance() {
		return walkingDistance;
	}

	public FitnessActivityDistance getRunningDistance() {
		return runningDistance;
	}

	public FitnessActivityDistance getBikingDistance() {
		return bikingDistance;
	}

	public static Creator<FitnessActivityDistances> CREATOR = new Creator<FitnessActivityDistances>() {
		@Override
		public FitnessActivityDistances createFromParcel(Parcel parcel) {
			return new FitnessActivityDistances(parcel);
		}

		@Override
		public FitnessActivityDistances[] newArray(int size) {
			return new FitnessActivityDistances[size];
		}
	};

	private FitnessActivityDistances(Parcel parcel) {
		this.walkingDistance = parcel.readParcelable(FitnessActivityDistance.class.getClassLoader());
		this.runningDistance = parcel.readParcelable(FitnessActivityDistance.class.getClassLoader());
		this.bikingDistance = parcel.readParcelable(FitnessActivityDistance.class.getClassLoader());
	}

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeParcelable(walkingDistance, flags);
		parcel.writeParcelable(runningDistance, flags);
		parcel.writeParcelable(bikingDistance, flags);
	}

	@Override
	public int describeContents() {
		return 0;
	}
}
