package org.alfonz.adapter.utility;

import android.databinding.BindingAdapter;
import android.support.annotation.IntDef;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.DisplayMetrics;

import org.alfonz.adapter.widget.GridDividerItemDecoration;
import org.alfonz.adapter.widget.GridSpacingItemDecoration;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


public final class BindingUtility
{
	public static final byte LAYOUT_LINEAR_VERTICAL = 0b00000101;
	public static final byte LAYOUT_LINEAR_HORIZONTAL = 0b00000100;
	public static final byte LAYOUT_GRID_VERTICAL = 0b00001001;
	public static final byte LAYOUT_GRID_HORIZONTAL = 0b00001000;
	public static final byte LAYOUT_STAGGERED_GRID_VERTICAL = 0b00010001;
	public static final byte LAYOUT_STAGGERED_GRID_HORIZONTAL = 0b00010000;
	public static final byte LAYOUT_LINEAR_VERTICAL_REVERSE = 0b00000111;
	public static final byte LAYOUT_LINEAR_HORIZONTAL_REVERSE = 0b00000110;
	public static final byte LAYOUT_GRID_VERTICAL_REVERSE = 0b00001011;
	public static final byte LAYOUT_GRID_HORIZONTAL_REVERSE = 0b00001010;
	public static final byte LAYOUT_STAGGERED_GRID_VERTICAL_REVERSE = 0b00010011;
	public static final byte LAYOUT_STAGGERED_GRID_HORIZONTAL_REVERSE = 0b00010010;

	public static final int DECORATION_LINEAR_DIVIDER_VERTICAL = 1;
	public static final int DECORATION_LINEAR_DIVIDER_HORIZONTAL = 2;
	public static final int DECORATION_GRID_DIVIDER = 3;
	public static final int DECORATION_GRID_SPACING = 4;

	public static final int ANIMATOR_DEFAULT = 1;

	private static final byte LAYOUT_VERTICAL_MASK = 0b00000001;
	private static final byte LAYOUT_REVERSE_MASK = 0b00000010;
	private static final byte LAYOUT_LINEAR_MASK = 0b00000100;
	private static final byte LAYOUT_GRID_MASK = 0b00001000;
	private static final byte LAYOUT_STAGGERED_GRID_MASK = 0b00010000;


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({LAYOUT_LINEAR_VERTICAL,
			LAYOUT_LINEAR_HORIZONTAL,
			LAYOUT_GRID_VERTICAL,
			LAYOUT_GRID_HORIZONTAL,
			LAYOUT_STAGGERED_GRID_VERTICAL,
			LAYOUT_STAGGERED_GRID_HORIZONTAL,
			LAYOUT_LINEAR_VERTICAL_REVERSE,
			LAYOUT_LINEAR_HORIZONTAL_REVERSE,
			LAYOUT_GRID_VERTICAL_REVERSE,
			LAYOUT_GRID_HORIZONTAL_REVERSE,
			LAYOUT_STAGGERED_GRID_VERTICAL_REVERSE,
			LAYOUT_STAGGERED_GRID_HORIZONTAL_REVERSE})
	public @interface RecyclerLayout {}


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({DECORATION_LINEAR_DIVIDER_VERTICAL,
			DECORATION_LINEAR_DIVIDER_HORIZONTAL,
			DECORATION_GRID_DIVIDER,
			DECORATION_GRID_SPACING})
	public @interface RecyclerDecoration {}


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({ANIMATOR_DEFAULT})
	public @interface RecyclerAnimator {}


	private BindingUtility() {}


	@BindingAdapter(value = {"recyclerLayout", "recyclerLayoutSpanCount", "recyclerLayoutSpanSize"}, requireAll = false)
	public static void setRecyclerLayout(RecyclerView recyclerView, @RecyclerLayout int recyclerLayout, int spanCount, float spanSize)
	{
		// noinspection ResourceType
		if(recyclerLayout == 0)
		{
			throw new IllegalArgumentException("Attribute recyclerLayout is mandatory");
		}

		if(spanCount > 0 && spanSize > 0)
		{
			throw new IllegalArgumentException("Cannot use attributes recyclerLayoutSpanCount and recyclerLayoutSpanSize at the same time");
		}

		RecyclerView.LayoutManager layoutManager;
		boolean vertical = (recyclerLayout & LAYOUT_VERTICAL_MASK) != 0;
		boolean reverse = (recyclerLayout & LAYOUT_REVERSE_MASK) != 0;
		boolean linear = (recyclerLayout & LAYOUT_LINEAR_MASK) != 0;
		boolean grid = (recyclerLayout & LAYOUT_GRID_MASK) != 0;
		boolean staggeredGrid = (recyclerLayout & LAYOUT_STAGGERED_GRID_MASK) != 0;

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
	public static void setRecyclerDecoration(RecyclerView recyclerView, @RecyclerDecoration int recyclerDecoration, float margin, float boundaryMargin)
	{
		// noinspection ResourceType
		if(recyclerDecoration == 0)
		{
			throw new IllegalArgumentException("Attribute recyclerDecoration is mandatory");
		}

		RecyclerView.ItemDecoration itemDecoration;

		if(recyclerDecoration == DECORATION_LINEAR_DIVIDER_VERTICAL)
		{
			itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL);
		}
		else if(recyclerDecoration == DECORATION_LINEAR_DIVIDER_HORIZONTAL)
		{
			itemDecoration = new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.HORIZONTAL);
		}
		else if(recyclerDecoration == DECORATION_GRID_DIVIDER)
		{
			itemDecoration = new GridDividerItemDecoration(recyclerView.getContext(), (int) margin);
		}
		else if(recyclerDecoration == DECORATION_GRID_SPACING)
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
	public static void setRecyclerAnimator(RecyclerView recyclerView, @RecyclerAnimator int recyclerAnimator)
	{
		RecyclerView.ItemAnimator itemAnimator;

		if(recyclerAnimator == ANIMATOR_DEFAULT)
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
