package ru.ming13.moon.model;

import android.os.Parcel;
import android.os.Parcelable;

public enum FitnessActivity implements Parcelable
{
	WALKING, RUNNING, BIKING;

	public static final Creator<FitnessActivity> CREATOR = new Creator<FitnessActivity>() {
		@Override
		public FitnessActivity createFromParcel(Parcel parcel) {
			return FitnessActivity.valueOf(parcel.readString());
		}

		@Override
		public FitnessActivity[] newArray(int size) {
			return new FitnessActivity[size];
		}
	};

	@Override
	public void writeToParcel(Parcel parcel, int flags) {
		parcel.writeString(this.name());
	}

	@Override
	public int describeContents() {
		return 0;
	}
}