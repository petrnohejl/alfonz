Alfonz - View Module
====================

Collection of custom views and layouts.


How to use custom views
-----------------------

`AspectRatioImageView` stretches a drawable width and respects aspect ratio.

```xml
<org.alfonz.view.AspectRatioImageView
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:adjustViewBounds="true"
	android:scaleType="fitCenter"
	android:src="@drawable/photo" />
```

`EllipsizingTextView` ellipsizes a multi-line text.

```xml
<org.alfonz.view.EllipsizingTextView
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:maxLines="4"
	android:ellipsize="marquee"
	android:text="@string/text" />
```

`ObservableScrollView` provides `OnScrollViewListener` for watching scroll events.

```xml
<org.alfonz.view.ObservableScrollView
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:fillViewport="true">

</org.alfonz.view.ObservableScrollView>
```

`RotatableImageView` rotates a drawable to a specific angle.

```xml
<org.alfonz.view.RotatableImageView
	android:layout_width="wrap_content"
	android:layout_height="wrap_content"
	android:adjustViewBounds="true"
	android:scaleType="fitCenter"
	android:src="@drawable/photo"
	app:angle="180" />
```


How to use custom layouts
-------------------------

`MaxWidthLinearLayout` is extended LinearLayout with limited maximum width.

```xml
<org.alfonz.view.MaxWidthLinearLayout
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:layout_gravity="center_horizontal"
	android:orientation="vertical"
	app:maxWidth="@dimen/global_max_width">

</org.alfonz.view.MaxWidthLinearLayout>
```

`SelectorFrameLayout`, `SelectorLinearLayout`, `SelectorRelativeLayout` layouts draw a selector on top of the layout in the foreground. It also works with ripple drawables.

```xml
<org.alfonz.view.SelectorFrameLayout
	android:layout_width="match_parent"
	android:layout_height="wrap_content"
	android:clickable="true"
	android:listSelector="@drawable/selector_clickable_item_bg">

</org.alfonz.view.SelectorFrameLayout>
```

`StatefulLayout` is a lightweight version of [StatefulLayout library](https://github.com/jakubkinst/Android-StatefulLayout). It displays and switches between different states of screen. Note that this layout changes a visibility of all direct child views.

```xml
<org.alfonz.view.StatefulLayout
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	app:progressLayout="@layout/placeholder_progress"
	app:offlineLayout="@layout/placeholder_offline"
	app:emptyLayout="@layout/placeholder_empty"
	app:state="@{viewModel.state}"
	app:invisibleWhenHidden="false">

</org.alfonz.view.StatefulLayout>
```


Dependencies
------------

* Android Support Library


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
