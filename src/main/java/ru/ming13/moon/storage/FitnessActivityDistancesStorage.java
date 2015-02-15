package ru.ming13.moon.storage;

import android.support.annotation.NonNull;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.fitness.Fitness;
import com.google.android.gms.fitness.FitnessActivities;
import com.google.android.gms.fitness.data.Bucket;
import com.google.android.gms.fitness.data.DataPoint;
import com.google.android.gms.fitness.data.DataSet;
import com.google.android.gms.fitness.data.DataType;
import com.google.android.gms.fitness.data.Field;
import com.google.android.gms.fitness.request.DataReadRequest;
import com.google.android.gms.fitness.result.DataReadResult;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import ru.ming13.moon.bus.FitnessActivityDistancesLoadedEvent;
import ru.ming13.moon.bus.BusProvider;
import ru.ming13.moon.model.FitnessActivityDistances;

public class FitnessActivityDistancesStorage implements ResultCallback<DataReadResult>
{
	private final GoogleApiClient apiClient;

	private final List<String> walkingActivities;
	private final List<String> runningActivities;
	private final List<String> bikingActivities;

	public static FitnessActivityDistancesStorage with(@NonNull GoogleApiClient apiClient) {
		return new FitnessActivityDistancesStorage(apiClient);
	}

	private FitnessActivityDistancesStorage(GoogleApiClient apiClient) {
		this.apiClient = apiClient;

		this.walkingActivities = getWalkingActivities();
		this.runningActivities = getRunningActivities();
		this.bikingActivities = getBikingActivities();
	}

	private List<String> getWalkingActivities() {
		return Arrays.asList(
			FitnessActivities.WALKING,
			FitnessActivities.WALKING_FITNESS,
			FitnessActivities.WALKING_NORDIC,
			FitnessActivities.WALKING_TREADMILL);
	}

	private List<String> getRunningActivities() {
		return Arrays.asList(
			FitnessActivities.RUNNING,
			FitnessActivities.RUNNING_JOGGING,
			FitnessActivities.RUNNING_SAND,
			FitnessActivities.RUNNING_TREADMILL);
	}

	private List<String> getBikingActivities() {
		return Arrays.asList(
			FitnessActivities.BIKING,
			FitnessActivities.BIKING_HAND,
			FitnessActivities.BIKING_MOUNTAIN,
			FitnessActivities.BIKING_ROAD,
			FitnessActivities.BIKING_SPINNING,
			FitnessActivities.BIKING_STATIONARY,
			FitnessActivities.BIKING_UTILITY);
	}

	public void readActivityDistances() {
		Fitness.HistoryApi.readData(apiClient, getActivityDistancesRequest()).setResultCallback(this);
	}

	private DataReadRequest getActivityDistancesRequest() {
		Date pastDate = new Date(1);
		Date presentDate = new Date();

		return new DataReadRequest.Builder()
			.aggregate(DataType.TYPE_DISTANCE_DELTA, DataType.AGGREGATE_DISTANCE_DELTA)
			.bucketByActivityType(1, TimeUnit.SECONDS)
			.setTimeRange(pastDate.getTime(), presentDate.getTime(), TimeUnit.MILLISECONDS)
			.build();
	}

	@Override
	public void onResult(DataReadResult activitiesResponse) {
		FitnessActivityDistances activityDistances = new FitnessActivityDistances();

		for (Bucket activityBucket : activitiesResponse.getBuckets()) {
			String activity = activityBucket.getActivity();

			for (DataSet activityDataSet : activityBucket.getDataSets()) {

				for (DataPoint activityDataPoint : activityDataSet.getDataPoints()) {
					float activityDistance = activityDataPoint.getValue(Field.FIELD_DISTANCE).asFloat();

					if (walkingActivities.contains(activity)) {
						activityDistances.getWalkingDistance().plus(activityDistance);
						continue;
					}

					if (runningActivities.contains(activity)) {
						activityDistances.getRunningDistance().plus(activityDistance);
						continue;
					}

					if (bikingActivities.contains(activity)) {
						activityDistances.getBikingDistance().plus(activityDistance);
						continue;
					}
				}
			}
		}

		BusProvider.getBus().post(new FitnessActivityDistancesLoadedEvent(activityDistances));
	}
}