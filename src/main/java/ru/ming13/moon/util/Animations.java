package ru.ming13.moon.util;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.support.annotation.NonNull;
import android.view.View;

public final class Animations
{
	private static final class Durations
	{
		private Durations() {
		}

		public static final int MEDIUM = 700;
	}

	private Animations() {
	}

	public static void scale(@NonNull View view) {
		Animator animator = ObjectAnimator.ofPropertyValuesHolder(view,
			PropertyValuesHolder.ofFloat(View.SCALE_X, 0, 1),
			PropertyValuesHolder.ofFloat(View.SCALE_Y, 0, 1));

		animator.setDuration(Durations.MEDIUM);

		animator.start();
	}

	public static void slide(@NonNull View view) {
		Animator animator = ObjectAnimator.ofPropertyValuesHolder(view,
			PropertyValuesHolder.ofFloat(View.TRANSLATION_Y, 1000, 1),
			PropertyValuesHolder.ofFloat(View.ALPHA, 0, 1));

		animator.setDuration(Durations.MEDIUM);

		animator.start();
	}
}
