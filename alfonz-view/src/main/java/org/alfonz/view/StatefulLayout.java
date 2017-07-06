package org.alfonz.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;


// inspired by: https://github.com/jakubkinst/Android-StatefulView
public class StatefulLayout extends FrameLayout
{
	public static final int CONTENT = 0;
	public static final int PROGRESS = 1;
	public static final int OFFLINE = 2;
	public static final int EMPTY = 3;

	private static final String SAVED_STATE = "stateful_layout_state";

	@State private int mInitialState;
	private int mProgressLayoutId;
	private int mOfflineLayoutId;
	private int mEmptyLayoutId;
	private boolean mInvisibleWhenHidden;
	private List<View> mContentLayoutList;
	private View mProgressLayout;
	private View mOfflineLayout;
	private View mEmptyLayout;
	@State private int mState;
	private OnStateChangeListener mOnStateChangeListener;


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({CONTENT, PROGRESS, OFFLINE, EMPTY})
	public @interface State {}


	public interface OnStateChangeListener
	{
		void onStateChange(View v, @State int state);
	}


	public StatefulLayout(Context context)
	{
		this(context, null);
	}


	public StatefulLayout(Context context, AttributeSet attrs)
	{
		this(context, attrs, 0);
	}


	public StatefulLayout(Context context, AttributeSet attrs, int defStyleAttr)
	{
		super(context, attrs, defStyleAttr);

		TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.StatefulLayout);

		if(typedArray.hasValue(R.styleable.StatefulLayout_state))
		{
			// noinspection ResourceType
			mInitialState = typedArray.getInt(R.styleable.StatefulLayout_state, CONTENT);
		}

		if(typedArray.hasValue(R.styleable.StatefulLayout_progressLayout) &&
				typedArray.hasValue(R.styleable.StatefulLayout_offlineLayout) &&
				typedArray.hasValue(R.styleable.StatefulLayout_emptyLayout))
		{
			mProgressLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_progressLayout, 0);
			mOfflineLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_offlineLayout, 0);
			mEmptyLayoutId = typedArray.getResourceId(R.styleable.StatefulLayout_emptyLayout, 0);
		}
		else
		{
			throw new IllegalArgumentException("Attributes progressLayout, offlineLayout and emptyLayout are mandatory");
		}

		mInvisibleWhenHidden = typedArray.getBoolean(R.styleable.StatefulLayout_invisibleWhenHidden, false);

		typedArray.recycle();
	}


	@Override
	protected void onFinishInflate()
	{
		super.onFinishInflate();
		setupView();
	}


	public void showContent()
	{
		setState(CONTENT);
	}


	public void showProgress()
	{
		setState(PROGRESS);
	}


	public void showOffline()
	{
		setState(OFFLINE);
	}


	public void showEmpty()
	{
		setState(EMPTY);
	}


	@State
	public int getState()
	{
		return mState;
	}


	@SuppressWarnings("ResourceType")
	public void setState(@State int state)
	{
		mState = state;

		for(int i = 0; i < mContentLayoutList.size(); i++)
		{
			mContentLayoutList.get(i).setVisibility(determineVisibility(state == CONTENT));
		}

		mProgressLayout.setVisibility(determineVisibility(state == PROGRESS));
		mOfflineLayout.setVisibility(determineVisibility(state == OFFLINE));
		mEmptyLayout.setVisibility(determineVisibility(state == EMPTY));

		if(mOnStateChangeListener != null) mOnStateChangeListener.onStateChange(this, state);
	}


	public void setOnStateChangeListener(OnStateChangeListener l)
	{
		mOnStateChangeListener = l;
	}


	public void saveInstanceState(Bundle outState)
	{
		outState.putInt(SAVED_STATE, mState);
	}


	@State
	public int restoreInstanceState(Bundle savedInstanceState)
	{
		@State int state = CONTENT;
		if(savedInstanceState != null && savedInstanceState.containsKey(SAVED_STATE))
		{
			// noinspection ResourceType
			state = savedInstanceState.getInt(SAVED_STATE);
			setState(state);
		}
		return state;
	}


	private void setupView()
	{
		if(mContentLayoutList == null && !isInEditMode())
		{
			mContentLayoutList = new ArrayList<>();
			for(int i = 0; i < getChildCount(); i++)
			{
				mContentLayoutList.add(getChildAt(i));
			}

			mProgressLayout = LayoutInflater.from(getContext()).inflate(mProgressLayoutId, this, false);
			mOfflineLayout = LayoutInflater.from(getContext()).inflate(mOfflineLayoutId, this, false);
			mEmptyLayout = LayoutInflater.from(getContext()).inflate(mEmptyLayoutId, this, false);

			addView(mProgressLayout);
			addView(mOfflineLayout);
			addView(mEmptyLayout);

			setState(mInitialState);
		}
	}


	private int determineVisibility(boolean visible)
	{
		if(visible)
		{
			return View.VISIBLE;
		}
		else
		{
			if(mInvisibleWhenHidden)
			{
				return View.INVISIBLE;
			}
			else
			{
				return View.GONE;
			}
		}
	}
}
