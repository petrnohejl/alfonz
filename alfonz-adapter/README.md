Alfonz - Adapter Module
=======================

Generic adapters for RecyclerView or ViewPager with data binding support.

The purpose of this module is to simplify usage of adapters. You can extend one of the predefined generic adapters. It depends if you want to use it for `RecyclerView` or `ViewPager`, if you have just one type of data or multiple types, if you prefer to use `ArrayList` or `ArrayMap`. The adapters for `RecyclerView` use `OnRebindCallback` for a better performance and correct handling of animations. Implementation of the base recycler adapter was inspired by [android-ui-toolkit-demos](https://github.com/google/android-ui-toolkit-demos/tree/master/DataBinding/DataBoundRecyclerView).

This module also contains a few useful binding adapters for setting up `RecyclerView`. You can find them in `BindingUtility` class.


How to use
----------

Choose an adapter which suits your needs:

* `SimpleDataBoundPagerAdapter`
* `SimpleDataBoundRecyclerAdapter`
* `SimpleMapDataBoundRecyclerAdapter`
* `MultiDataBoundPagerAdapter`
* `MultiDataBoundRecyclerAdapter`
* `MultiMapDataBoundRecyclerAdapter`

Create a class, extend the generic adapter and call `super` in the constructor. Pass item layout, view (view layer in MVVM) and observable array collection in the `super`. Adapter will inflate the layout for an item and bind the view and the data into `BR.view` and `BR.data` variables.

```java
public class ProductListAdapter extends SimpleDataBoundRecyclerAdapter<FragmentProductListItemBinding>
{
	public ProductListAdapter(ProductListView view, ProductListViewModel viewModel)
	{
		super(R.layout.fragment_product_list_item, view, viewModel.products);
	}
}
```

For multi-type adapters, you also have to specify which layout you want to inflate for a specific item or type. See samples for more info.

Create an XML layout, define `view` and `data` variables, use `view` for invoking event callbacks and `data` for accessing data entity from the observable array collection defined in a view model.

```xml
<layout
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto">

	<data>
		<variable name="view" type="com.example.ui.ProductListView" />
		<variable name="data" type="com.example.entity.ProductEntity" />
	</data>

	<LinearLayout
		android:layout_width="match_parent"
		android:layout_height="wrap_content"
		android:onClick="@{() -> view.onItemClick(data)}">

		<TextView
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:text="@{data.name}" />

	</LinearLayout>
</layout>
```

Create a new instance of the adapter and set it in your RecyclerView. Implement event callbacks in the MVVM view layer - it is usually Fragment.

```java
public interface ProductListView extends BaseView
{
	void onItemClick(ProductEntity product);
}
```

```java
public class ProductListFragment
		extends BaseFragment<ProductListView, ProductListViewModel, FragmentProductListBinding>
		implements ProductListView
{
	private ProductListAdapter mAdapter;

	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupAdapter();
	}

	@Override
	public void onItemClick(ProductEntity product)
	{
		startProductDetailActivity(product.getId());
	}

	private void setupAdapter()
	{
		if(mAdapter == null)
		{
			mAdapter = new ProductListAdapter(this, getViewModel());
			getBinding().fragmentProductListRecycler.setAdapter(mAdapter);
		}
	}
}
```

There are also a few useful binding adapters for setting up recycler layout, decoration and animator. You can use following XML attributes in your `RecyclerView`:

* `recyclerLayout`
* `recyclerLayoutSpanCount`
* `recyclerLayoutSpanSize`
* `recyclerDecoration`
* `recyclerDecorationMargin`
* `recyclerDecorationBoundaryMargin`
* `recyclerAnimator`

`RecyclerLayout` supports these layout variants:

* `LINEAR_VERTICAL`
* `LINEAR_HORIZONTAL`
* `GRID_VERTICAL`
* `GRID_HORIZONTAL`
* `STAGGERED_GRID_VERTICAL`
* `STAGGERED_GRID_HORIZONTAL`
* `LINEAR_VERTICAL_REVERSE`
* `LINEAR_HORIZONTAL_REVERSE`
* `GRID_VERTICAL_REVERSE`
* `GRID_HORIZONTAL_REVERSE`
* `STAGGERED_GRID_VERTICAL_REVERSE`
* `STAGGERED_GRID_HORIZONTAL_REVERSE`

`RecyclerDecoration` provides these decorations:

* `LINEAR_DIVIDER_VERTICAL`
* `LINEAR_DIVIDER_HORIZONTAL`
* `GRID_DIVIDER`
* `GRID_SPACING`


Dependencies
------------

* Alfonz MVVM Module
* Android Support Library
* Data Binding Library


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
