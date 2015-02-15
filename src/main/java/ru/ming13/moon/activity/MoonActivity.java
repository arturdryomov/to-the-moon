package ru.ming13.moon.activity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.Fitness;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import icepick.Icepick;
import icepick.Icicle;
import ru.ming13.moon.R;
import ru.ming13.moon.bus.FitnessActivityDistancesLoadedEvent;
import ru.ming13.moon.bus.BusProvider;
import ru.ming13.moon.model.FitnessActivityDistances;
import ru.ming13.moon.storage.FitnessActivityDistancesStorage;
import ru.ming13.moon.model.FitnessActivity;
import ru.ming13.moon.model.FitnessActivityDistance;
import ru.ming13.moon.util.Animations;
import ru.ming13.moon.util.DistanceCalculator;
import ru.ming13.moon.util.Formatter;
import ru.ming13.moon.util.GoogleServices;
import ru.ming13.moon.util.Intents;

public class MoonActivity extends ActionBarActivity implements
	GoogleApiClient.ConnectionCallbacks,
	GoogleApiClient.OnConnectionFailedListener
{
	static final class FitnessActivityViewHolder
	{
		@InjectView(R.id.image_activity)
		ImageView activityIcon;

		@InjectView(R.id.text_activity_title)
		TextView activityTitle;

		@InjectView(R.id.text_activity_description)
		TextView activityDescription;

		public FitnessActivityViewHolder(View activityView) {
			ButterKnife.inject(this, activityView);
		}
	}

	@InjectView(R.id.toolbar)
	Toolbar toolbar;

	@InjectView(R.id.progress)
	ProgressBar progressBar;

	@InjectView(R.id.button_share)
	ImageButton shareButton;

	@InjectView(R.id.layout_activities)
	ViewGroup activitiesLayout;

	@InjectView(R.id.layout_connection)
	ViewGroup connectionLayout;

	private FitnessActivityViewHolder walkingViewHolder;
	private FitnessActivityViewHolder runningViewHolder;
	private FitnessActivityViewHolder bikingViewHolder;

	private GoogleApiClient googleApiClient;

	private boolean isGoogleApiRepeatingConnection;

	@Icicle
	FitnessActivityDistances activityDistances;

	@Override
	protected void onCreate(Bundle state) {
		super.onCreate(state);
		setContentView(R.layout.activity_moon);

		setUpInjections();

		setUpState(state);

		setUpToolbar();

		setUpActivityStats();
	}

	private void setUpInjections() {
		ButterKnife.inject(this);

		walkingViewHolder = new FitnessActivityViewHolder(findViewById(R.id.layout_activity_walking));
		runningViewHolder = new FitnessActivityViewHolder(findViewById(R.id.layout_activity_running));
		bikingViewHolder = new FitnessActivityViewHolder(findViewById(R.id.layout_activity_biking));
	}

	private void setUpState(Bundle state) {
		Icepick.restoreInstanceState(this, state);
	}

	private void setUpToolbar() {
		setSupportActionBar(toolbar);

		getSupportActionBar().setDisplayShowTitleEnabled(false);
	}

	private void setUpActivityStats() {
		if (activityDistances == null) {
			setUpGoogleApiClient();
			setUpGoogleApiConnection();
		} else {
			setUpFitnessActivities();
		}
	}

	private void setUpGoogleApiClient() {
		googleApiClient = new GoogleApiClient.Builder(this)
			.addApi(Fitness.API)
			.addScope(Fitness.SCOPE_ACTIVITY_READ)
			.addScope(Fitness.SCOPE_LOCATION_READ)
			.addConnectionCallbacks(this)
			.addOnConnectionFailedListener(this)
			.build();
	}

	private void setUpGoogleApiConnection() {
		googleApiClient.connect();
	}

	@Override
	public void onConnected(Bundle connectionHint) {
		FitnessActivityDistancesStorage.with(googleApiClient).readActivityDistances();
	}

	@Subscribe
	public void onActivityDistancesLoaded(FitnessActivityDistancesLoadedEvent event) {
		this.activityDistances = event.getActivityDistances();

		setUpFitnessActivities();
	}

	private void setUpFitnessActivities() {
		setUpFitnessActivity(walkingViewHolder, activityDistances.getWalkingDistance());
		setUpFitnessActivity(runningViewHolder, activityDistances.getRunningDistance());
		setUpFitnessActivity(bikingViewHolder, activityDistances.getBikingDistance());

		Animations.exchange(progressBar, activitiesLayout);
		Animations.scaleUp(shareButton);
	}

	private void setUpFitnessActivity(FitnessActivityViewHolder activityViewHolder, FitnessActivityDistance activityDistance) {
		activityViewHolder.activityIcon.setImageResource(
			getFitnessActivityIcon(activityDistance.getActivity()));

		activityViewHolder.activityTitle.setText(
			Formatter.with(this).formatDistance(activityDistance.getDistance()));

		activityViewHolder.activityDescription.setText(
			getString(R.string.mask_distance_moon,
			Formatter.with(this).formatPercentage(DistanceCalculator.calculateMoonDistancePercentage(activityDistance.getDistance()))));
	}

	@DrawableRes
	private int getFitnessActivityIcon(FitnessActivity activity) {
		switch (activity) {
			case WALKING:
				return R.drawable.ic_description_walking;

			case RUNNING:
				return R.drawable.ic_description_running;

			case BIKING:
				return R.drawable.ic_description_biking;

			default:
				return android.R.color.transparent;
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (resultCode != RESULT_OK) {
			tearDownGoogleApiConnection();
			return;
		}

		if (requestCode == Intents.Requests.GOOGLE_CONNECTION) {
			setUpGoogleApiConnection();
		}
	}

	private void tearDownGoogleApiConnection() {
		if (isGoogleApiClientConnected()) {
			googleApiClient.disconnect();
		}

		Animations.exchange(progressBar, connectionLayout);
	}

	private boolean isGoogleApiClientConnected() {
		return (googleApiClient != null) && (googleApiClient.isConnecting() || googleApiClient.isConnected());
	}

	@Override
	public void onConnectionSuspended(int cause) {
	}

	@Override
	public void onConnectionFailed(ConnectionResult connectionResult) {
		if (!isGoogleApiRepeatingConnection) {
			isGoogleApiRepeatingConnection = true;

			Animations.exchange(progressBar, connectionLayout);

			return;
		}

		GoogleServices googleServices = GoogleServices.with(this);

		if (googleServices.canResolve(connectionResult)) {
			googleServices.showResolutionAction(connectionResult);
		} else {
			googleServices.showResolutionError(connectionResult);

			Animations.exchange(progressBar, connectionLayout);
		}
	}

	@OnClick(R.id.button_connect)
	public void setUpGoogleApiConnection(Button connectionButton) {
		Animations.exchange(connectionLayout, progressBar);

		googleApiClient.connect();
	}

	@OnClick(R.id.button_share)
	public void startStatsSharing() {
		String sharingMessage = getString(R.string.mask_sharing,
			Formatter.with(this).formatPercentage(
				DistanceCalculator.calculateMoonDistancePercentage(activityDistances.getWalkingDistance().getDistance())),
			Formatter.with(this).formatPercentage(
				DistanceCalculator.calculateMoonDistancePercentage(activityDistances.getRunningDistance().getDistance())),
			Formatter.with(this).formatPercentage(
				DistanceCalculator.calculateMoonDistancePercentage(activityDistances.getBikingDistance().getDistance())));

		Intent intent = Intents.Builder.with(this).buildShareIntent(sharingMessage);
		startActivity(intent);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.action_moon, menu);

		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem menuItem) {
		switch (menuItem.getItemId()) {
			case R.id.menu_rate_application:
				startApplicationRating();
				return true;

			case R.id.menu_send_feedback:
				startFeedbackSending();
				return true;

			default:
				return super.onOptionsItemSelected(menuItem);
		}
	}

	private void startApplicationRating() {
		try {
			Intent intent = Intents.Builder.with(this).buildGooglePlayAppIntent();
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			Intent intent = Intents.Builder.with(this).buildGooglePlayWebIntent();
			startActivity(intent);
		}
	}

	private void startFeedbackSending() {
		Intent intent = Intents.Builder.with(this).buildFeedbackIntent();

		try {
			startActivity(intent);
		} catch (ActivityNotFoundException e) {
			startActivity(Intent.createChooser(intent, null));
		}
	}

	@Override
	protected void onResume() {
		super.onResume();

		BusProvider.getBus().register(this);
	}

	@Override
	protected void onPause() {
		super.onPause();

		BusProvider.getBus().unregister(this);
	}

	@Override
	protected void onSaveInstanceState(Bundle state) {
		super.onSaveInstanceState(state);

		tearDownState(state);
	}

	private void tearDownState(Bundle state) {
		Icepick.saveInstanceState(this, state);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		tearDownGoogleApiConnection();
	}
}
