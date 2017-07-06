Alfonz - Media Module
=====================

Utilities for working with images, sounds and videos.


How to use ImagePicker
----------------------

Create a new instance of `ImagePicker`. Specify the name of the album directory.

```java
private ImagePicker mImagePicker = new ImagePicker(getContext(), getString(R.string.app_name));
```

Pick an image from camera or gallery when user clicks on a button. Note that it requires `READ_EXTERNAL_STORAGE` permission.

```java
mImagePicker.pickImageFromCamera(this);
mImagePicker.pickImageFromGallery(this);
```

Handle Activity result and get the bitmap.

```java
@Override
public void onActivityResult(final int requestCode, final int resultCode, final Intent data)
{
	if(resultCode == Activity.RESULT_OK)
	{
		switch(requestCode)
		{
			case ImagePicker.ACTION_PICK_IMAGE_FROM_CAMERA:
			{
				Bitmap bitmap = mImagePicker.handleImageFromCamera();
				if(bitmap != null)
				{
					// do something with the bitmap
				}
				break;
			}

			case ImagePicker.ACTION_PICK_IMAGE_FROM_GALLERY:
			{
				Bitmap bitmap = mImagePicker.handleImageFromGallery(data);
				if(bitmap != null)
				{
					// do something with the bitmap
				}
				break;
			}
		}
	}
	else if(resultCode == Activity.RESULT_CANCELED)
	{
		// canceled
	}
}
```


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
public void onStop()
{
	mSoundManager.stopAll();
}
```


Dependencies
------------

* Alfonz Graphics Module
* Android Support Library


Samples and download
--------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
