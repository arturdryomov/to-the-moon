package ru.ming13.moon.bus;

import android.support.annotation.NonNull;

import ru.ming13.moon.model.FitnessActivityDistance;

public class ActivityDistancesLoadedEvent implements BusEvent
{
	private final FitnessActivityDistance walkingActivityDistance;
	private final FitnessActivityDistance runningActivityDistance;
	private final FitnessActivityDistance bikingActivityDistance;

	public ActivityDistancesLoadedEvent(
		@NonNull FitnessActivityDistance walkingActivityDistance,
		@NonNull FitnessActivityDistance runningActivityDistance,
		@NonNull FitnessActivityDistance bikingActivityDistance) {

		this.walkingActivityDistance = walkingActivityDistance;
		this.runningActivityDistance = runningActivityDistance;
		this.bikingActivityDistance = bikingActivityDistance;
	}

	public FitnessActivityDistance getWalkingActivityDistance() {
		return walkingActivityDistance;
	}

	public FitnessActivityDistance getRunningActivityDistance() {
		return runningActivityDistance;
	}

	public FitnessActivityDistance getBikingActivityDistance() {
		return bikingActivityDistance;
	}
}
