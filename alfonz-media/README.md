Alfonz - Media Module
=====================

Utilities for working with images, sounds and videos.


How to use ImagePicker
----------------------

Create a new instance of `ImagePicker`. Specify the name of the album directory.

```java
private ImagePicker mImagePicker = new ImagePicker(getContext(), getString(R.string.app_name));
```

Override `onActivityResult()` as follows.

```java
@Override
public void onActivityResult(int requestCode, int resultCode, Intent data) {
	mImagePicker.onActivityResult(this, requestCode, resultCode, data);
}
```

Pick an image from camera or gallery when user clicks on a button. Note that it requires `READ_EXTERNAL_STORAGE` permission.

```java
mImagePicker.pickImageFromCamera(
		this,
		(pickable, bitmap, path) -> pickable.handleImagePicked(bitmap, path),
		pickable -> pickable.handleImageCanceled());
```

```java
mImagePicker.pickImageFromGallery(
		this,
		(pickable, bitmap, path) -> pickable.handleImagePicked(bitmap, path),
		pickable -> pickable.handleImageCanceled());
```

Pickable variable in the lambda expression represents current instance of Fragment or Activity which has been passed in `onActivityResult()` method.


How to use SoundManager
-----------------------

Create a new instance of `SoundManager`. You can choose which playback mode you prefer to use.

```java
private SoundManager mSoundManager = new SoundManager(getContext(), SoundManager.PLAY_SINGLE);
```

Play a sound from storage or assets.

```java
mSoundManager.play(path);
mSoundManager.playAsset(filename);
```

Stop all playing sounds and release resources in `Activity.onStop()`.

```java
@Override
public void onStop() {
	mSoundManager.stopAll();
}
```


Dependencies
------------

* Alfonz Graphics Module
* AndroidX


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
