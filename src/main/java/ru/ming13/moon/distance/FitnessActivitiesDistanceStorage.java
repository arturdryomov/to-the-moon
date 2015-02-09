package ru.ming13.moon.distance;

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

import ru.ming13.moon.model.FitnessActivity;
import ru.ming13.moon.model.FitnessActivityDistance;

public class FitnessActivitiesDistanceStorage implements ResultCallback<DataReadResult>
{
	private final GoogleApiClient apiClient;

	public static FitnessActivitiesDistanceStorage with(@NonNull GoogleApiClient apiClient) {
		return new FitnessActivitiesDistanceStorage(apiClient);
	}

	private FitnessActivitiesDistanceStorage(GoogleApiClient apiClient) {
		this.apiClient = apiClient;
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
			.enableServerQueries()
			.build();
	}

	@Override
	public void onResult(DataReadResult activitiesResponse) {
		FitnessActivityDistance walkingActivityDistance = new FitnessActivityDistance(FitnessActivity.WALKING);
		FitnessActivityDistance runningActivityDistance = new FitnessActivityDistance(FitnessActivity.RUNNING);
		FitnessActivityDistance bikingActivityDistance = new FitnessActivityDistance(FitnessActivity.BIKING);

		for (Bucket activityBucket : activitiesResponse.getBuckets()) {
			String activity = activityBucket.getActivity();

			for (DataSet activityDataSet : activityBucket.getDataSets()) {
				for (DataPoint activityDataPoint : activityDataSet.getDataPoints()) {
					float activityDistance = activityDataPoint.getValue(Field.FIELD_DISTANCE).asFloat();

					if (isWalkingActivity(activity)) {
						walkingActivityDistance.plus(activityDistance);
					}

					if (isRunningActivity(activity)) {
						runningActivityDistance.plus(activityDistance);
					}

					if (isBikingActivity(activity)) {
						bikingActivityDistance.plus(activityDistance);
					}
				}
			}
		}
	}

	public boolean isWalkingActivity(String activity) {
		List<String> walkingActivities = Arrays.asList(
			FitnessActivities.WALKING,
			FitnessActivities.WALKING_NORDIC,
			FitnessActivities.WALKING_FITNESS,
			FitnessActivities.WALKING_TREADMILL);

		return walkingActivities.contains(activity);
	}

	public boolean isRunningActivity(String activity) {
		List<String> runningActivities = Arrays.asList(
			FitnessActivities.RUNNING,
			FitnessActivities.RUNNING_JOGGING,
			FitnessActivities.RUNNING_SAND,
			FitnessActivities.RUNNING_TREADMILL);

		return runningActivities.contains(activity);
	}

	public boolean isBikingActivity(String activity) {
		List<String> bikingActivities = Arrays.asList(
			FitnessActivities.BIKING,
			FitnessActivities.BIKING_HAND,
			FitnessActivities.BIKING_MOUNTAIN,
			FitnessActivities.BIKING_ROAD,
			FitnessActivities.BIKING_SPINNING,
			FitnessActivities.BIKING_STATIONARY,
			FitnessActivities.BIKING_UTILITY);

		return bikingActivities.contains(activity);
	}
}