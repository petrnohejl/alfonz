Alfonz - MVVM Module
====================

Base classes for MVVM architecture.

The purpose of this module is to simplify implementation of MVVM architecture. MVVM binder is based on Data Binding Library from Google. This module provides 4 abstract base elements (Activity, Fragment, View, ViewModel) which make up view and view-model layers of MVVM architecture.

This MVVM module is basically a wrapper for [AndroidViewModel](https://github.com/inloop/AndroidViewModel) library. It provides some additional features and convenient methods. If you are interested in MVVM architecture, you can check my talk about MVVM which I presented at STRV Android Meetup. See the [video record](https://www.youtube.com/watch?v=vnBmdKkMLZw) or [slides](https://speakerdeck.com/petrnohejl/mvvm-architecture-on-android) for more info. Alfonz MVVM module uses the same approach which I describe in the presentation.

`AlfonzActivity` provides some convenient methods for setting up ActionBar and managing Fragments.

`AlfonzBindingFragment` takes care of data binding initialization and binding view and view-model layers in a layout.

`AlfonzView` represents an interface which serves as a communication bridge from ViewModel to Fragment or XML layout to Fragment. It is usually used for defining:

* Event callbacks, invoked from XML layout using data binding
* UI-related operations which cannot be done via data binding, e.g. show a dialog or a toast
* Android-related operations which have to be performed from view-model layer, e.g. starting an activity

`AlfonzViewModel` implements `android.databinding.Observable` for registering observable callbacks. It also provides a mechanism for caching UI actions when view layer is not temporarily available - for example during device orientation change.

If you want to have Activity with its own ViewModel, use `AlfonzBindingActivity`. This module also contains a few useful binding adapters and conversions. You can find them in `BindingUtility` class.


How to use
----------

First of all, it's good practice to create base classes for Activity, View, Fragment, ViewModel in your project so you can share global methods or fields. Your base classes will extend Alfonz base classes.

```java
public abstract class BaseActivity extends AlfonzActivity
{
	// add whatever you need...
}
```

```java
public interface BaseView extends AlfonzView
{
	void showToast(String message);
}
```

```java
public abstract class BaseFragment<T extends BaseView, R extends BaseViewModel<T>, B extends ViewDataBinding>
		extends AlfonzBindingFragment<T, R, B> implements BaseView
{
	@Override
	public void showToast(String message)
	{
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}
```

```java
public abstract class BaseViewModel<T extends BaseView> extends AlfonzViewModel<T>
{
	public void handleError(String message)
	{
		if(getView() != null)
		{
			getView().showToast(message);
		}
	}
}
```

Now you can implement classes which will represent a screen. I will show you how to use this MVVM module on a very simple Hello World example.

Let's start with Activity. You can choose which action bar indicator you prefer to use.

```java
public class HelloWorldActivity extends BaseActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
		setupActionBar(INDICATOR_BACK);
	}
}
```

Create an Activity layout. Toolbar in the layout must be identified as `R.id.toolbar`. If you are going to switch fragments in your Activity using `replaceFragment()` convenient method, create a layout container with `R.id.container_fragment` identifier for it.

```xml
<LinearLayout
	xmlns:android="http://schemas.android.com/apk/res/android"
	android:layout_width="match_parent"
	android:layout_height="match_parent"
	android:orientation="vertical">

	<include layout="@layout/toolbar" />

	<fragment
		android:id="@+id/fragment_hello_world"
		android:name="com.example.fragment.HelloWorldFragment"
		android:layout_width="match_parent"
		android:layout_height="0dp"
		android:layout_weight="1" />

</LinearLayout>
```

Now let's create a View. It will have just one event callback method.

```java
public interface HelloWorldView extends BaseView
{
	void onClick();
}
```

View interface will be implemented by a Fragment. We have to specify which ViewModel class will represent our view-model layer, we have to inflate our binding layout and implement View methods. You can call `getViewModel()` from the Fragment to access a ViewModel. You can get a binding object via `getBinding()` call. View should stay as dumb as possible and should not have any logic.

```java
public class HelloWorldFragment
		extends BaseFragment<HelloWorldView, HelloWorldViewModel, FragmentHelloWorldBinding>
		implements HelloWorldView
{
	@Nullable
	@Override
	public Class<HelloWorldViewModel> getViewModelClass()
	{
		return HelloWorldViewModel.class;
	}

	@Override
	public FragmentHelloWorldBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentHelloWorldBinding.inflate(inflater);
	}

	@Override
	public void onClick()
	{
		getViewModel().updateMessage("Hello!");
	}
}
```

Create a Fragment layout. View is bound in the layout in `BR.view` variable, ViewModel is bound in `BR.viewModel` variable. You have to define these variables in the layout. Use `view` for invoking event callbacks and `viewModel` for accessing observable properties exposed by the ViewModel. I recommend not to use complex data binding expressions. It is not possible to test it. All the logic should be in the ViewModel.

```xml
<layout
	xmlns:android="http://schemas.android.com/apk/res/android"
	xmlns:app="http://schemas.android.com/apk/res-auto">

	<data>
		<variable name="view" type="com.example.ui.HelloWorldView" />
		<variable name="viewModel" type="com.example.viewmodel.HelloWorldViewModel" />
	</data>

	<org.alfonz.view.StatefulLayout
		android:layout_width="match_parent"
		android:layout_height="match_parent"
		app:progressLayout="@layout/placeholder_progress"
		app:offlineLayout="@layout/placeholder_offline"
		app:emptyLayout="@layout/placeholder_empty"
		app:state="@{viewModel.state}">

		<TextView
			android:layout_width="wrap_content"
			android:layout_height="wrap_content"
			android:text="@{viewModel.message.text}"
			android:onClick="@{() -> view.onClick()}" />
		
	</org.alfonz.view.StatefulLayout>
</layout>
```

Finally let's create a ViewModel. The best thing about the ViewModel is that it is persisted so you don't lose state or data during device configuration changes. You don't have to use retained Fragments anymore. ViewModel instance is automatically removed after a Fragment is completely gone. I recommend to implement the ViewModel as plain Java for better testability. Never ever use Activity Context in the ViewModel. If you need the Context, use Application Context, it is safer. You can call `getView()` from the ViewModel to access a View.

ViewModel has a following lifecycle:

* `onCreate(Bundle arguments, Bundle savedInstanceState)`
* `onBindView(T view)`
* `onStart()`
* `onSaveInstanceState(Bundle bundle)`
* `onStop()`
* `onDestroy()`

```java
public class HelloWorldViewModel extends BaseViewModel<HelloWorldView>
{
	public final ObservableField<Integer> state = new ObservableField<>();
	public final ObservableField<MessageEntity> message = new ObservableField<>();

	@Override
	public void onStart()
	{
		super.onStart();
		if(message.get() == null) loadData();
	}

	public void updateMessage(String text)
	{
		MessageEntity m = message.get();
		m.setText(text);
		message.notifyChange();
	}

	private void loadData()
	{
		// show progress
		state.set(StatefulLayout.PROGRESS);

		// load data from data provider...
	}

	private void onLoadData(MessageEntity m)
	{
		// save data
		message.set(m);

		// show content
		if(message.get() != null)
		{
			state.set(StatefulLayout.CONTENT);
		}
		else
		{
			state.set(StatefulLayout.EMPTY);
		}
	}
}
```

If you run a UI callback inside the ViewModel, it is recommended to encapsulate it in `runViewAction()`. It is a mechanism for caching UI actions when view layer is not temporarily available - for example during a device orientation change. Imagine you have an action which starts a new Activity. When you change the orientation, Activity Context is temporarily not available. If you start a new Activity at that moment, it will throw `NullPointerException`.

```java
@Override
public void onResponse(Call<MessageEntity> call, Response<MessageEntity> response)
{
	runViewAction(new ViewAction<HelloWorldView>()
	{
		@Override
		public void run(@NonNull HelloWorldView view)
		{
			view.startGreetingActivity(response.body().getText());
		}
	});
}
```

There are also a few useful binding adapters. You can use following XML attributes in your views:

* `onClick`
* `onLongClick`
* `visible`
* `invisible`
* `gone`
* `imageBitmap`


Dependencies
------------

* Android Support Library
* Data Binding Library
* [AndroidViewModel](https://github.com/inloop/AndroidViewModel)


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
