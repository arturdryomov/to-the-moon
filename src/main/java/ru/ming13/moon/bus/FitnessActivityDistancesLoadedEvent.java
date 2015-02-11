package ru.ming13.moon.bus;

import android.support.annotation.NonNull;

import ru.ming13.moon.model.FitnessActivityDistances;

public class FitnessActivityDistancesLoadedEvent implements BusEvent
{
	private final FitnessActivityDistances activityDistances;

	public FitnessActivityDistancesLoadedEvent(@NonNull FitnessActivityDistances activityDistances) {
		this.activityDistances = activityDistances;
	}

	public FitnessActivityDistances getActivityDistances() {
		return activityDistances;
	}
}
