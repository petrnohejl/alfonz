package org.alfonz.adapter.widget;

import android.graphics.Rect;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;


public class GridSpacingItemDecoration extends RecyclerView.ItemDecoration
{
	private int mMargin;
	private int mBoundaryMargin;


	public GridSpacingItemDecoration(int margin)
	{
		mMargin = margin / 2;
		mBoundaryMargin = margin;
	}


	public GridSpacingItemDecoration(int margin, int boundaryMargin)
	{
		mMargin = margin / 2;
		mBoundaryMargin = boundaryMargin;
	}


	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView recyclerView, RecyclerView.State state)
	{
		super.getItemOffsets(outRect, view, recyclerView, state);

		int position = recyclerView.getChildLayoutPosition(view);
		int itemCount = recyclerView.getAdapter().getItemCount();
		int spanCount = getSpanCount(recyclerView);
		boolean vertical = isVertical(recyclerView);
		boolean reverse = isReverse(recyclerView);

		calculateOutRect(outRect, position, itemCount, spanCount, vertical, reverse);
	}


	private void calculateOutRect(Rect outRect, int position, int itemCount, int spanCount, boolean vertical, boolean reverse)
	{
		// start offset
		if(position < spanCount)
		{
			if(vertical && !reverse) outRect.top = mBoundaryMargin;
			else if(vertical && reverse) outRect.bottom = mBoundaryMargin;
			else if(!vertical && !reverse) outRect.left = mBoundaryMargin;
			else if(!vertical && reverse) outRect.right = mBoundaryMargin;
		}
		else
		{
			if(vertical && !reverse) outRect.top = mMargin;
			else if(vertical && reverse) outRect.bottom = mMargin;
			else if(!vertical && !reverse) outRect.left = mMargin;
			else if(!vertical && reverse) outRect.right = mMargin;
		}

		// end offset
		if(itemCount % spanCount == 0 && position >= itemCount - spanCount)
		{
			if(vertical && !reverse) outRect.bottom = mBoundaryMargin;
			else if(vertical && reverse) outRect.top = mBoundaryMargin;
			else if(!vertical && !reverse) outRect.right = mBoundaryMargin;
			else if(!vertical && reverse) outRect.left = mBoundaryMargin;
		}
		else if(itemCount % spanCount != 0 && position >= itemCount - itemCount % spanCount)
		{
			if(vertical && !reverse) outRect.bottom = mBoundaryMargin;
			else if(vertical && reverse) outRect.top = mBoundaryMargin;
			else if(!vertical && !reverse) outRect.right = mBoundaryMargin;
			else if(!vertical && reverse) outRect.left = mBoundaryMargin;
		}
		else
		{
			if(vertical && !reverse) outRect.bottom = mMargin;
			else if(vertical && reverse) outRect.top = mMargin;
			else if(!vertical && !reverse) outRect.right = mMargin;
			else if(!vertical && reverse) outRect.left = mMargin;
		}

		// start side offset
		if(position % spanCount == 0)
		{
			if(vertical) outRect.left = mBoundaryMargin;
			else outRect.top = mBoundaryMargin;
		}
		else
		{
			if(vertical) outRect.left = mMargin;
			else outRect.top = mMargin;
		}

		// end side offset
		if(position % spanCount == spanCount - 1)
		{
			if(vertical) outRect.right = mBoundaryMargin;
			else outRect.bottom = mBoundaryMargin;
		}
		else
		{
			if(vertical) outRect.right = mMargin;
			else outRect.bottom = mMargin;
		}
	}


	private boolean isVertical(RecyclerView recyclerView)
	{
		if(recyclerView.getLayoutManager() instanceof GridLayoutManager)
		{
			GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			return gridLayoutManager.getOrientation() == GridLayoutManager.VERTICAL;
		}
		else if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager)
		{
			StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			return staggeredGridLayoutManager.getOrientation() == GridLayoutManager.VERTICAL;
		}
		else
		{
			throw new IllegalStateException(getIllegalStateExceptionMessage());
		}
	}


	private boolean isReverse(RecyclerView recyclerView)
	{
		if(recyclerView.getLayoutManager() instanceof GridLayoutManager)
		{
			GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			return gridLayoutManager.getReverseLayout();
		}
		else if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager)
		{
			StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			return staggeredGridLayoutManager.getReverseLayout();
		}
		else
		{
			throw new IllegalStateException(getIllegalStateExceptionMessage());
		}
	}


	private int getSpanCount(RecyclerView recyclerView)
	{
		if(recyclerView.getLayoutManager() instanceof GridLayoutManager)
		{
			GridLayoutManager gridLayoutManager = (GridLayoutManager) recyclerView.getLayoutManager();
			return gridLayoutManager.getSpanCount();
		}
		else if(recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager)
		{
			StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) recyclerView.getLayoutManager();
			return staggeredGridLayoutManager.getSpanCount();
		}
		else
		{
			throw new IllegalStateException(getIllegalStateExceptionMessage());
		}
	}


	private String getIllegalStateExceptionMessage()
	{
		return String.format("%s can only be used with a %s or %s",
				this.getClass().getSimpleName(),
				GridLayoutManager.class.getSimpleName(),
				StaggeredGridLayoutManager.class.getSimpleName());
	}
}
