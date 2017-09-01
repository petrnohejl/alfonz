Alfonz - Utility Module
=======================

Bunch of miscellaneous utilities.


How to use ContentUtility
-------------------------

`ContentUtility` is for getting a file path from a Uri which identifies data in the `ContentProvider`.

```java
Uri uri = Uri.parse(getActivity().getIntent().getDataString());
String path = ContentUtility.getPath(getContext(), uri);
```


How to use DateConvertor
------------------------

`DateConvertor` is for converting between different date formats.

```java
String string = DateConvertor.dateToString(date, "yyyy-MM-dd HH:mm:ss");
Date date = DateConvertor.stringToDate(string, "yyyy-MM-dd HH:mm:ss");
Calendar calendar = DateConvertor.dateToCalendar(date);
Date date = DateConvertor.calendarToDate(calendar);
Calendar calendar = DateConvertor.stringToCalendar(string, "yyyy-MM-dd HH:mm:ss");
String string = DateConvertor.calendarToString(calendar, "yyyy-MM-dd HH:mm:ss");
```


How to use DeviceUuidFactory
----------------------------

`DeviceUuidFactory` returns a unique UUID for the current Android device.

```java
DeviceUuidFactory deviceUuidFactory = new DeviceUuidFactory(getContext());
UUID uuid = deviceUuidFactory.getDeviceUUID();
```


How to use DimensionUtility
---------------------------

`DimensionUtility` is for converting between different dimension units.

```java
float px = DimensionUtility.dp2px(getContext(), dp);
float px = DimensionUtility.sp2px(getContext(), sp);
float dp = DimensionUtility.px2dp(getContext(), px);
float sp = DimensionUtility.px2sp(getContext(), px);
```


How to use DownloadUtility
--------------------------

`DownloadUtility` downloads a remote file using `DownloadManager`. Note that it requires `WRITE_EXTERNAL_STORAGE` permission.

```java
DownloadUtility.downloadFile(getActivity(), url, fileName);
```


How to use HashUtility
----------------------

`HashUtility` is for generating MD5 and SHA1 hashes.

```java
String hash = HashUtility.getMd5(data);
String hash = HashUtility.getSha1(data);
```


How to use IntentUtility
------------------------

`IntentUtility` simplifies starting implicit intents.

```java
IntentUtility.startWebActivity(getContext(), uri);
IntentUtility.startStoreActivity(getContext());
IntentUtility.startShareActivity(getContext(), subject, text, chooserTitle);
IntentUtility.startEmailActivity(getContext(), email, subject, text);
IntentUtility.startSmsActivity(getContext(), phoneNumber, text);
IntentUtility.startCallActivity(getContext(), phoneNumber);
IntentUtility.startMapCoordinatesActivity(getContext(), lat, lon, zoom, label);
IntentUtility.startMapSearchActivity(getContext(), query);
IntentUtility.startNavigationActivity(getContext(), lat, lon);
IntentUtility.startCalendarActivity(getContext(), title, description, beginTime, endTime);
boolean callable = IntentUtility.isCallable(getContext(), intent);
```


How to use KeyboardUtility
--------------------------

`KeyboardUtility` is for showing/hiding a system keyboard.

```java
KeyboardUtility.showKeyboard(view);
KeyboardUtility.hideKeyboard(view);
```


How to use Logcat
-----------------

`Logcat` is a logging utility. Log message can show a code location - class, method, line and current thread. Logs can be easily disabled which is recommended for release builds. `Logcat` has to be initialized in `Application` class.

```java
public class ExampleApplication extends Application
{
	@Override
	public void onCreate()
	{
		super.onCreate();
		Logcat.init(BuildConfig.LOGS, "EXAMPLE");
	}
}
```

```java
Logcat.d(msg);
Logcat.e(msg);
Logcat.i(msg);
Logcat.v(msg);
Logcat.w(msg);
Logcat.wtf(msg);
Logcat.printStackTrace(throwable);
```


How to use NetworkUtility
-------------------------

`NetworkUtility` provides info about active network. Note that it requires `ACCESS_NETWORK_STATE` permission.

```java
boolean online = NetworkUtility.isOnline(getContext());
int networkType = NetworkUtility.getType(getContext());
String networkName = NetworkUtility.getTypeName(getContext());
```


How to use PermissionManager
----------------------------

`PermissionManager` is for checking permissions and showing rationale in a Fragment or an Activity.

First implement `RationaleHandler`. Define a rationale message and rationale UI.

```java
public class PermissionRationaleHandler implements PermissionManager.RationaleHandler
{
	@Override
	public String getRationaleMessage(@NonNull String permission)
	{
		int resId;
		switch(permission)
		{
			case Manifest.permission.READ_EXTERNAL_STORAGE:
				resId = R.string.permission_read_external_storage;
				break;
			case Manifest.permission.WRITE_EXTERNAL_STORAGE:
				resId = R.string.permission_write_external_storage;
				break;
			default:
				resId = R.string.permission_unknown;
		}
		return HelloWorldApplication.getContext().getString(resId);
	}

	@Override
	public void showRationale(@NonNull View rootView, @NonNull String rationaleMessage, @NonNull PermissionManager.ConfirmAction confirmAction)
	{
		Snackbar.make(rootView, rationaleMessage, Snackbar.LENGTH_INDEFINITE)
				.setAction(android.R.string.ok, view -> confirmAction.run())
				.show();
	}
}
```

Now you can use the `PermissionManager` in your Fragment or Activity to manage permission requests. It is recommended to define it in ViewModel so permission callback can survive orientation changes. You can easily access the manager from Fragment or Activity if you define it as a public final attribute.

```java
public final PermissionManager permissionManager = new PermissionManager(new PermissionRationaleHandler());
```

Override `onRequestPermissionsResult()` as follows.

```java
@Override
public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
{
	getViewModel().permissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
}
```

Call `check` method to check if permission(s) have been granted or denied. Call `request` method to check and eventually request permission(s) to be granted to the app.

```java
boolean granted = getViewModel().permissionManager.check(this, Manifest.permission.READ_EXTERNAL_STORAGE);
```

```java
getViewModel().permissionManager.request(
		this,
		Manifest.permission.READ_EXTERNAL_STORAGE,
		requestable -> requestable.handlePermissionGranted(),
		requestable -> requestable.handlePermissionDenied(),
		requestable -> requestable.handlePermissionBlocked());
```

```java
getViewModel().permissionManager.request(
		this,
		new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
		(requestable, permissionsResult) -> requestable.handlePermissions(permissionsResult));
```

Requestable variable in the lambda expression represents current instance of Fragment or Activity which has been passed in `onRequestPermissionsResult()` method.


How to use ResourcesUtility
---------------------------

`ResourcesUtility` is for retrieving resource values of style attributes.

```java
int value = ResourcesUtility.getValueOfAttribute(getContext(), R.attr.colorPrimary);
int color = ResourcesUtility.getColorValueOfAttribute(getContext(), R.attr.colorPrimary);
float dimen = ResourcesUtility.getDimensionValueOfAttribute(getContext(), R.attr.actionBarSize);
int dimen = ResourcesUtility.getDimensionPixelSizeValueOfAttribute(getContext(), R.attr.actionBarSize);
Drawable drawable = ResourcesUtility.getDrawableValueOfAttribute(getContext(), R.attr.icon);
```


How to use ServiceUtility
-------------------------

`ServiceUtility` provides methods for working with Service.

```java
boolean running = ServiceUtility.isRunning(getContext(), ExampleService.class);
```


How to use StorageUtility
-------------------------

`StorageUtility` provides methods for getting paths to files, system directories or mounted storages. Note that some methods require `READ_EXTERNAL_STORAGE` permission.

```java
boolean available = StorageUtility.isAvailable();
boolean writable = StorageUtility.isWritable();
File storageDir = StorageUtility.getStorageDirectory();
File picturesStorageDir = StorageUtility.getStorageDirectory(Environment.DIRECTORY_PICTURES);
File secondaryStorageDir = StorageUtility.getSecondaryStorageDirectory();
File picturesSecondaryStorageDir = StorageUtility.getSecondaryStorageDirectory(Environment.DIRECTORY_PICTURES);
File cacheDir = StorageUtility.getApplicationCacheDirectory(getContext());
File picturesFilesDir = StorageUtility.getApplicationFilesDirectory(getContext(), Environment.DIRECTORY_PICTURES);
List<File> files = StorageUtility.getFiles(directory, true);
List<File> images = StorageUtility.getFiles(directory, true, Pattern.compile("(.+(\\.(?i)(jpg|jpeg))$)"), null);
Set<String> mounts = StorageUtility.getExternalMounts();
```


How to use StringConvertor
--------------------------

`StringConvertor` provides methods for string operations.

```java
String capitalized = StringConvertor.capitalize(text);
```


How to use ValidationUtility
----------------------------

`ValidationUtility` is for validating different input data.

```java
boolean valid = ValidationUtility.isEmailValid(email);
boolean valid = ValidationUtility.isDateValid(date, format);
```


How to use VersionUtility
-------------------------

`VersionUtility` provides info about app version.

```java
String name = VersionUtility.getVersionName(getContext());
int code = VersionUtility.getVersionCode(getContext());
boolean glEs2 = VersionUtility.isSupportedOpenGlEs2(getContext());
int comparison = VersionUtility.compareVersions(lastVersion, newVersion);
```


How to use ZipUtility
---------------------

`ZipUtility` provides methods for working with ZIP file. Note that it requires `WRITE_EXTERNAL_STORAGE` permission.

```java
boolean success = ZipUtility.unpackZip(path, zipname);
```


Dependencies
------------

* Android Support Library


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
