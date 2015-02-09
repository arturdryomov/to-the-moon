package ru.ming13.moon.model;

public class FitnessActivityDistance
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
}
