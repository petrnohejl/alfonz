Alfonz - Graphics Module
========================

Utilities for working with bitmaps and drawables.


How to use bitmap utilities
---------------------------

`BitmapBlur` utility converts bitmap to blurred bitmap.

```java
Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
Bitmap blurredBitmap = BitmapBlur.getBlurredBitmap(getContext(), originalBitmap);
originalBitmap.recycle();
imageView.setImageBitmap(blurredBitmap);
```

`BitmapReflection` utility makes reflection effect.

```java
Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
Bitmap reflectedBitmap = BitmapReflection.getReflectedBitmap(originalBitmap, 0);
originalBitmap.recycle();
imageView.setImageBitmap(reflectedBitmap);
```

`BitmapScaler` utility is for resizing bitmaps. There are more variants for scaling.

```java
Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
Bitmap scaledBitmap = BitmapScaler.scaleToFill(originalBitmap, 512, 512);
originalBitmap.recycle();
imageView.setImageBitmap(scaledBitmap);
```


How to use drawables
--------------------

`CircularDrawable` and `RoundedDrawable` are extended Drawables with circular/rounded shape.

```java
CircularDrawable drawable = new CircularDrawable(bitmap);
imageView.setImageDrawable(drawable);
```

```java
RoundedDrawable drawable = new RoundedDrawable(bitmap, radius);
imageView.setImageDrawable(drawable);
```


Dependencies
------------

* No dependencies


Examples and download
---------------------

See the main [README](https://github.com/petrnohejl/Alfonz/) file.
