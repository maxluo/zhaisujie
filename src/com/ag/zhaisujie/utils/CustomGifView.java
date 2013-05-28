package com.ag.zhaisujie.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Movie;
import android.util.AttributeSet;
import android.view.View;

/**
 * CustomGifView.java
 * 
 * @author max.Luo
 * @email max_null@sina.com 2013-5-28
 */
public class CustomGifView extends View {

	private Movie mMovie;
	private long mMovieStart;
	private int sourceId;

	public CustomGifView(Context context) {
		super(context);
	}

	public void setResource(int resourceId) {
		this.sourceId = resourceId;
		mMovie = Movie.decodeStream(getResources().openRawResource(this.sourceId));
	}

	public CustomGifView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
	}

	public CustomGifView(Context context, AttributeSet attrs) {
		super(context, attrs);
		// TODO Auto-generated constructor stub
	}

	public void onDraw(Canvas canvas) {
		long now = android.os.SystemClock.uptimeMillis();

		if (mMovieStart == 0) { // first time
			mMovieStart = now;
		}
		if (mMovie != null) {

			int dur = mMovie.duration();
			if (dur == 0) {
				dur = 1000;
			}
			int relTime = (int) ((now - mMovieStart) % dur);
			mMovie.setTime(relTime);
			mMovie.draw(canvas, 0, 0);
			invalidate();
		}
	}
}
