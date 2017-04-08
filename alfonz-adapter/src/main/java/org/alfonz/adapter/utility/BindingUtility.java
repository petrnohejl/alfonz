package org.alfonz.adapter.utility;

import android.databinding.BindingAdapter;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import org.alfonz.adapter.widget.LinearDividerItemDecoration;


public final class BindingUtility
{
	public enum RecyclerLayout
	{
		LINEAR_VERTICAL
	}


	public enum RecyclerDecoration
	{
		LINEAR_DIVIDER
	}


	public enum RecyclerAnimator
	{
		DEFAULT
	}


	private BindingUtility() {}


	@BindingAdapter({"recyclerLayout"})
	public static void setRecyclerLayout(RecyclerView recyclerView, RecyclerLayout recyclerLayout)
	{
		RecyclerView.LayoutManager layoutManager;

		if(recyclerLayout == RecyclerLayout.LINEAR_VERTICAL)
		{
			LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
			linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
			layoutManager = linearLayoutManager;
		}
		else
		{
			throw new IllegalArgumentException();
		}

		recyclerView.setLayoutManager(layoutManager);
		recyclerView.setHasFixedSize(true);
	}


	@BindingAdapter({"recyclerDecoration"})
	public static void setRecyclerDecoration(RecyclerView recyclerView, RecyclerDecoration recyclerDecoration)
	{
		RecyclerView.ItemDecoration itemDecoration;

		if(recyclerDecoration == RecyclerDecoration.LINEAR_DIVIDER)
		{
			itemDecoration = new LinearDividerItemDecoration(recyclerView.getContext(), null);
		}
		else
		{
			throw new IllegalArgumentException();
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
			throw new IllegalArgumentException();
		}

		recyclerView.setItemAnimator(itemAnimator);
	}
}
