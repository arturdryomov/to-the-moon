/*
 * Copyright 2015 Artur Dryomov
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package ru.ming13.moon.util;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

public final class Animations
{
	private static final class Durations
	{
		private Durations() {
		}

		public static final int MEDIUM = 400;
	}

	private Animations() {
	}

	public static void scaleUp(@NonNull final View view) {
		view.setVisibility(View.VISIBLE);

		getScaleAnimator(view, 0, 1).start();
	}

	private static Animator getScaleAnimator(View view, float fromScale, float toScale) {
		Animator animator = ObjectAnimator.ofPropertyValuesHolder(view,
			PropertyValuesHolder.ofFloat(View.SCALE_X, fromScale, toScale),
			PropertyValuesHolder.ofFloat(View.SCALE_Y, fromScale, toScale));

		animator.setDuration(Durations.MEDIUM);

		return animator;
	}

	public static void exchange(@NonNull final View startView, @NonNull final View finishView) {
		startView.setVisibility(View.VISIBLE);
		finishView.setVisibility(View.INVISIBLE);

		final Animator startAnimator = getSlidingDownAnimator(startView);
		final Animator finishAnimator = getSlidingUpAnimator(finishView);

		startAnimator.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animator) {
				startView.setVisibility(View.INVISIBLE);
				finishView.setVisibility(View.VISIBLE);

				finishAnimator.start();
			}
		});

		startAnimator.start();
	}

	private static Animator getSlidingDownAnimator(@NonNull View view) {
		View parentView = (View) view.getParent();

		return getSlidingAnimator(view, 0, parentView.getHeight() - view.getTop(), 1, 0);
	}

	private static Animator getSlidingUpAnimator(@NonNull View view) {
		View parentView = (View) view.getParent();

		return getSlidingAnimator(view, parentView.getHeight() - view.getTop(), 0, 0, 1);
	}

	private static Animator getSlidingAnimator(View view, float fromY, float toY, float fromAlpha, float toAlpha) {
		Animator animator = ObjectAnimator.ofPropertyValuesHolder(view,
			PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, fromY, toY),
			PropertyValuesHolder.ofFloat(View.ALPHA, fromAlpha, toAlpha));

		animator.setDuration(Durations.MEDIUM);
		animator.setInterpolator(new AccelerateDecelerateInterpolator());

		return animator;
	}
}
