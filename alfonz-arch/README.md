Alfonz - Arch Module
====================

Base classes for MVVM architecture (based on Architecture Components).

The purpose of this module is to simplify implementation of MVVM architecture. MVVM binder is based on Data Binding Library from Google. This module provides 4 abstract base elements (Activity, Fragment, View, ViewModel) which make up view and view-model layers of MVVM architecture.

This Arch module is basically a wrapper for [Android Architecture Components](https://developer.android.com/topic/libraries/architecture/) library. It provides some additional features and convenient methods. If you are interested in MVVM architecture, you can check my talk about MVVM which I presented at STRV Android Meetup. See the [video record](https://www.youtube.com/watch?v=vnBmdKkMLZw) or [slides](https://speakerdeck.com/petrnohejl/mvvm-architecture-on-android) for more info. Alfonz Arch module uses a similar approach which I describe in the presentation.

`AlfonzActivity` provides some convenient methods for setting up ActionBar and managing Fragments. It implements `LifecycleOwner` to handle lifecycle changes.

`AlfonzBindingFragment` takes care of view-model and data binding initialization and binding view and view-model layers in a layout. It implements `LifecycleOwner` to handle lifecycle changes.

`AlfonzView` represents an interface which serves as a communication bridge between XML layout and Fragment. It is usually used for:

* Event callbacks, invoked from XML layout using data binding

`AlfonzViewModel` is automatically retained during configuration changes. It implements `android.databinding.Observable` for registering observable callbacks. It also provides LiveData event bus for sending events from view-model layer to view layer. The bus is usually used for:

* UI-related operations which cannot be done via data binding, e.g. show a dialog or a toast
* Android-related operations which have to be performed from view-model layer, e.g. starting an activity

If you want to have Activity with its own ViewModel, use `AlfonzBindingActivity`. This module also contains a few useful binding adapters and conversions. You can find them in `BindingUtility` class.


How to use
----------

First of all, it's good practice to create base classes for Activity, Fragment, ViewModel in your project so you can share global methods or fields. Your base classes will extend Alfonz base classes.

```java
public abstract class BaseActivity extends AlfonzActivity
{
	// add whatever you need...
}
```

```java
public abstract class BaseFragment<T extends BaseViewModel, B extends ViewDataBinding>
		extends AlfonzBindingFragment<T, B>
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		getViewModel().observeEvent(this, ToastEvent.class, toastEvent -> showToast(toastEvent.message));
	}

	public void showToast(String message)
	{
		Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
	}
}
```

```java
public abstract class BaseViewModel extends AlfonzViewModel
{
	public void handleError(String message)
	{
		sendEvent(new ToastEvent(message));
	}
}
```

Now you can implement classes which will represent a screen. I will show you how to use this Arch module on a very simple Hello World example.

Let's start with Activity. You can choose which action bar indicator you prefer to use.

```java
public class HelloWorldActivity extends BaseActivity
{
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_world);
		setupActionBar(ToolbarIndicator.BACK);
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
public interface HelloWorldView extends AlfonzView
{
	void onClick();
}
```

View interface will be implemented by a Fragment. We have to specify which ViewModel class will represent our view-model layer, we have to inflate our binding layout and implement View methods. You can call `getViewModel()` from the Fragment to access a ViewModel instance. You can get a binding object via `getBinding()` call. View should stay as dumb as possible and should not have any logic. `AlfonzFragment` also provides `onBackPressed()` method which can be overridden to handle back button press.

```java
public class HelloWorldFragment
		extends BaseFragment<HelloWorldViewModel, FragmentHelloWorldBinding>
		implements HelloWorldView
{
	@Override
	public HelloWorldViewModel setupViewModel()
	{
		return ViewModelProviders.of(this).get(HelloWorldViewModel.class);
	}

	@Override
	public FragmentHelloWorldBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
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

Finally let's create a ViewModel. The best thing about the ViewModel is that it is persisted so you don't lose state or data during device configuration changes. You don't have to use retained Fragments anymore. ViewModel instance is automatically removed after a Fragment is completely gone. I recommend to implement the ViewModel as plain Java for better testability. Never ever use Activity Context in the ViewModel. If you need the Context, use Application Context, it is safer. You can use `AlfonzBundleViewModelFactory` and `AlfonzBundleViewModel` to pass Application Context and Bundle data to the ViewModel.

```java
public class HelloWorldViewModel extends BaseViewModel
{
	public final MutableLiveData<Integer> state = new MutableLiveData<>();
	public final MutableLiveData<MessageEntity> message = new MutableLiveData<>();

	public void loadData()
	{
		// show progress
		state.setValue(StatefulLayout.PROGRESS);

		// load data from data provider...
	}

	public void updateMessage(String text)
	{
		MessageEntity m = message.getValue();
		m.setText(text);
		message.setValue(m);
	}

	private void onLoadData(MessageEntity m)
	{
		// save data
		message.setValue(m);

		// show content
		if(message.getValue() != null)
		{
			state.setValue(StatefulLayout.CONTENT);
		}
		else
		{
			state.setValue(StatefulLayout.EMPTY);
		}
	}
}
```

Communication from ViewModel to View layer is done via LiveData event bus. LiveBus is a part of `AlfonzViewModel` and it is basically a map with LiveEvent instances for each event type. New instance of LiveEvent is created automatically inside the bus once the specific event is observed or sent for a first time. So event should be delivered even if an observer is not active. ViewModel has no direct reference to Views. LiveData events can be observed by more Fragment instances, but each event will be delivered just once to the first observer. This is intentional, because actions like show a dialog or start an activity should be run just once.

```java
public class SnackbarEvent extends Event
{
	public final String message;

	public SnackbarEvent(String message)
	{
		this.message = message;
	}
}
```

```java
// observe event in Fragment
getViewModel().observeEvent(this, SnackbarEvent.class, snackbarEvent -> showSnackbar(snackbarEvent.message));
```

```java
// send event in ViewModel
sendEvent(new SnackbarEvent(message));
```

`AlfonzViewModel` has just one lifecycle method `onCleared()` which is called when ViewModel is no longer used and will be destroyed. Sometimes it could be useful to have in ViewModel the same lifecycle methods as Activity or Fragment provide. ViewModel can implement `LifecycleObserver` to accomplish this.

```java
public class HelloWorldViewModel extends BaseViewModel implements LifecycleObserver
{
	@OnLifecycleEvent(Lifecycle.Event.ON_START)
	public void onStart()
	{
		// load data
		if(message.getValue() == null) loadData();
	}
}
```

```java
// register observer in Fragment
getLifecycle().addObserver(viewModel);
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

* Android Architecture Components
* Android Support Library
* Data Binding Library


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
