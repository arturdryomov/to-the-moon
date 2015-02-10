package ru.ming13.moon.bus;

import android.support.annotation.NonNull;

import ru.ming13.moon.model.FitnessActivityDistances;

public class ActivityDistancesLoadedEvent implements BusEvent
{
	private final FitnessActivityDistances activityDistances;

	public ActivityDistancesLoadedEvent(@NonNull FitnessActivityDistances activityDistances) {
		this.activityDistances = activityDistances;
	}

	public FitnessActivityDistances getActivityDistances() {
		return activityDistances;
	}
}
