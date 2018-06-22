package org.alfonz.view;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.widget.ScrollView;

// source: http://stackoverflow.com/questions/3948934/synchronise-scrollview-scroll-positions-android
public class ObservableScrollView extends ScrollView {
	private OnScrollViewListener mOnScrollViewListener = null;

	public interface OnScrollViewListener {
		void onScrollChanged(@NonNull ObservableScrollView scrollView, int x, int y, int oldx, int oldy);
	}

	public ObservableScrollView(@NonNull Context context) {
		super(context);
	}

	public ObservableScrollView(@NonNull Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public ObservableScrollView(@NonNull Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onScrollChanged(int x, int y, int oldx, int oldy) {
		super.onScrollChanged(x, y, oldx, oldy);
		if (mOnScrollViewListener != null) {
			mOnScrollViewListener.onScrollChanged(this, x, y, oldx, oldy);
		}
	}

	public void setOnScrollViewListener(@NonNull OnScrollViewListener onScrollViewListener) {
		mOnScrollViewListener = onScrollViewListener;
	}
}
