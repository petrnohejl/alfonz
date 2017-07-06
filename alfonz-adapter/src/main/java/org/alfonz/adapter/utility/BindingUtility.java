package org.alfonz.adapter.utility;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;

import org.alfonz.adapter.widget.GridDividerItemDecoration;
import org.alfonz.adapter.widget.GridSpacingItemDecoration;


public final class BindingUtility
{
	private static final byte RECYCLER_LAYOUT_VERTICAL_MASK = 0b00000001;
	private static final byte RECYCLER_LAYOUT_REVERSE_MASK = 0b00000010;
	private static final byte RECYCLER_LAYOUT_LINEAR_MASK = 0b00000100;
	private static final byte RECYCLER_LAYOUT_GRID_MASK = 0b00001000;
	private static final byte RECYCLER_LAYOUT_STAGGERED_GRID_MASK = 0b00010000;


	public enum RecyclerLayout
	{
		LINEAR_VERTICAL(0b00000101),
		LINEAR_HORIZONTAL(0b00000100),
		GRID_VERTICAL(0b00001001),
		GRID_HORIZONTAL(0b00001000),
		STAGGERED_GRID_VERTICAL(0b00010001),
		STAGGERED_GRID_HORIZONTAL(0b00010000),
		LINEAR_VERTICAL_REVERSE(0b00000111),
		LINEAR_HORIZONTAL_REVERSE(0b00000110),
		GRID_VERTICAL_REVERSE(0b00001011),
		GRID_HORIZONTAL_REVERSE(0b00001010),
		STAGGERED_GRID_VERTICAL_REVERSE(0b00010011),
		STAGGERED_GRID_HORIZONTAL_REVERSE(0b00010010);

		private final byte mValue;


		RecyclerLayout(int value)
		{
			mValue = (byte) value;
		}


		public byte getValue()
		{
			return mValue;
		}
	}


	public enum RecyclerDecoration
	{
		LINEAR_DIVIDER_VERTICAL,
		LINEAR_DIVIDER_HORIZONTAL,
		GRID_DIVIDER,
		GRID_SPACING
	}


	public enum RecyclerAnimator
	{
		DEFAULT
	}


	private BindingUtility() {}


	@BindingAdapter(value = {"recyclerLayout", "recyclerLayoutSpanCount", "recyclerLayoutSpanSize"}, requireAll = false)
	public static void setRecyclerLayout(RecyclerView recyclerView, RecyclerLayout recyclerLayout, int spanCount, float spanSize)
	{
		if(recyclerLayout == null)
		{
			throw new IllegalArgumentException("Attribute recyclerLayout is mandatory");
		}

		if(spanCount > 0 && spanSize > 0)
		{
			throw new IllegalArgumentException("Cannot use attributes recyclerLayoutSpanCount and recyclerLayoutSpanSize at the same time");
		}

		RecyclerView.LayoutManager layoutManager;
		boolean vertical = (recyclerLayout.getValue() & RECYCLER_LAYOUT_VERTICAL_MASK) != 0;
		boolean reverse = (recyclerLayout.getValue() & RECYCLER_LAYOUT_REVERSE_MASK) != 0;
		boolean linear = (recyclerLayout.getValue() & RECYCLER_LAYOUT_LINEAR_MASK) != 0;
		boolean grid = (recyclerLayout.getValue() & RECYCLER_LAYOUT_GRID_MASK) != 0;
		boolean staggeredGrid = (recyclerLayout.getValue() & RECYCLER_LAYOUT_STAGGERED_GRID_MASK) != 0;

		if(linear)
		{
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
			linearLayoutManager.setOrientation(vertical ? LinearLayoutManager.VERTICAL : LinearLayoutManager.HORIZONTAL);
			linearLayoutManager.setReverseLayout(reverse);
			layoutManager = linearLayoutManager;
		}
		else if(grid)
		{
			GridLayoutManager gridLayoutManager = new GridLayoutManager(
					recyclerView.getContext(),
					calculateGridSpanCount(recyclerView, spanCount, spanSize, vertical));
			gridLayoutManager.setOrientation(vertical ? GridLayoutManager.VERTICAL : GridLayoutManager.HORIZONTAL);
			gridLayoutManager.setReverseLayout(reverse);
			layoutManager = gridLayoutManager;
		}
		else if(staggeredGrid)
		{
			StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(
					calculateGridSpanCount(recyclerView, spanCount, spanSize, vertical),
					vertical ? StaggeredGridLayoutManager.VERTICAL : StaggeredGridLayoutManager.HORIZONTAL);
			staggeredGridLayoutManager.setReverseLayout(reverse);
			layoutManager = staggeredGridLayoutManager;
		}
		else
		{
			throw new IllegalArgumentException("Unknown layout manager type");
		}

		recyclerView.setLayoutManager(layoutManager);
	}


	@BindingAdapter(value = {"recyclerDecoration", "recyclerDecorationMargin", "recyclerDecorationBoundaryMargin"}, requireAll = false)
	public static void setRecyclerDecoration(RecyclerView recyclerView, RecyclerDecoration recyclerDecoration, float margin, float boundaryMargin)
	{
		if(recyclerDecoration == null)
		{
			throw new IllegalArgumentException("Attribute recyclerDecoration is mandatory");
		}

		RecyclerView.ItemDecoration itemDecoration;

		if(recyclerDecoration == RecyclerDecoration.LINEAR_DIVIDER_VERTICAL)
		{
			itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		}
		else if(recyclerDecoration == RecyclerDecoration.LINEAR_DIVIDER_HORIZONTAL)
		{
			itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
		}
		else if(recyclerDecoration == RecyclerDecoration.GRID_DIVIDER)
		{
			itemDecoration = new GridDividerItemDecoration(recyclerView.getContext(), (int) margin);
		}
		else if(recyclerDecoration == RecyclerDecoration.GRID_SPACING)
		{
			itemDecoration = new GridSpacingItemDecoration((int) margin, (int) boundaryMargin);
		}
		else
		{
			throw new IllegalArgumentException("Unknown item decoration type");
		}

		recyclerView.addItemDecoration(itemDecoration);
	}


	@BindingAdapter({"recyclerAnimator"})
	public static void setRecyclerAnimator(RecyclerView recyclerView, RecyclerAnimator recyclerAnimator)
	{
		RecyclerView.ItemAnimator itemAnimator;

		if(recyclerAnimator == RecyclerAnimator.DEFAULT)
		{
			itemAnimator = new DefaultItemAnimator();
		}
		else
		{
			throw new IllegalArgumentException("Unknown item animator type");
		}

		recyclerView.setItemAnimator(itemAnimator);
	}


	private static int calculateGridSpanCount(RecyclerView recyclerView, int spanCount, float spanSize, boolean vertical)
	{
		if(spanCount == 0 && spanSize == 0)
		{
			throw new IllegalArgumentException("Missing recyclerLayoutSpanCount or recyclerLayoutSpanSize attribute");
		}

		if(spanCount > 0)
		{
			return spanCount;
		}
		else
		{
			DisplayMetrics displayMetrics = recyclerView.getContext().getResources().getDisplayMetrics();
			float screenSize = vertical ? displayMetrics.widthPixels : displayMetrics.heightPixels;
			return Math.round(screenSize / spanSize);
		}
	}
}
