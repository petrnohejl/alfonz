package org.alfonz.media;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;

import org.alfonz.graphics.bitmap.BitmapScaler;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.util.Pair;
import androidx.exifinterface.media.ExifInterface;
import androidx.fragment.app.Fragment;

public class ImagePicker {
	private static final int REQUEST_CODE_CAMERA = 1;
	private static final int REQUEST_CODE_GALLERY = 2;
	private static final String TAG = "ALFONZ";

	private final Context mContext;
	private final String mAlbumDirectoryName;
	private final int mImageSize;
	private ImagePickerCallback mImagePickerCallback;
	private String mImageFromCameraPath;

	public interface PickedAction<T> {
		void run(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path);
	}

	public interface CanceledAction<T> {
		void run(@NonNull T pickable);
	}

	public interface ImagePickerCallback<T> {
		void onImagePicked(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path);
		void onImageCanceled(@NonNull T pickable);
	}

	private interface ImagePickable<T> {
		T getPickable();
		void startActivityForResult(Intent intent, int requestCode);
	}

	public ImagePicker(@NonNull Context context, @NonNull String albumDirectoryName) {
		this(context, albumDirectoryName, 640);
	}

	public ImagePicker(@NonNull Context context, @NonNull String albumDirectoryName, int imageSize) {
		mContext = context.getApplicationContext();
		mAlbumDirectoryName = albumDirectoryName;
		mImageSize = imageSize;
	}

	public <T extends Activity> void pickImageFromCamera(@NonNull T activity, @NonNull PickedAction<T> pickedAction) {
		pickImageFromCamera(new ActivityPickable<>(activity), pickedAction);
	}

	public <T extends Fragment> void pickImageFromCamera(@NonNull T fragment, @NonNull PickedAction<T> pickedAction) {
		pickImageFromCamera(new FragmentPickable<>(fragment), pickedAction);
	}

	public <T extends Activity> void pickImageFromCamera(@NonNull T activity, @NonNull PickedAction<T> pickedAction, @NonNull CanceledAction<T> canceledAction) {
		pickImageFromCamera(new ActivityPickable<>(activity), pickedAction, canceledAction);
	}

	public <T extends Fragment> void pickImageFromCamera(@NonNull T fragment, @NonNull PickedAction<T> pickedAction, @NonNull CanceledAction<T> canceledAction) {
		pickImageFromCamera(new FragmentPickable<>(fragment), pickedAction, canceledAction);
	}

	public <T extends Activity> void pickImageFromCamera(@NonNull T activity, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		pickImageFromCamera(new ActivityPickable<>(activity), imagePickerCallback);
	}

	public <T extends Fragment> void pickImageFromCamera(@NonNull T fragment, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		pickImageFromCamera(new FragmentPickable<>(fragment), imagePickerCallback);
	}

	public <T extends Activity> void pickImageFromGallery(@NonNull T activity, @NonNull PickedAction<T> pickedAction) {
		pickImageFromGallery(new ActivityPickable<>(activity), pickedAction);
	}

	public <T extends Fragment> void pickImageFromGallery(@NonNull T fragment, @NonNull PickedAction<T> pickedAction) {
		pickImageFromGallery(new FragmentPickable<>(fragment), pickedAction);
	}

	public <T extends Activity> void pickImageFromGallery(@NonNull T activity, @NonNull PickedAction<T> pickedAction, @NonNull CanceledAction<T> canceledAction) {
		pickImageFromGallery(new ActivityPickable<>(activity), pickedAction, canceledAction);
	}

	public <T extends Fragment> void pickImageFromGallery(@NonNull T fragment, @NonNull PickedAction<T> pickedAction, @NonNull CanceledAction<T> canceledAction) {
		pickImageFromGallery(new FragmentPickable<>(fragment), pickedAction, canceledAction);
	}

	public <T extends Activity> void pickImageFromGallery(@NonNull T activity, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		pickImageFromGallery(new ActivityPickable<>(activity), imagePickerCallback);
	}

	public <T extends Fragment> void pickImageFromGallery(@NonNull T fragment, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		pickImageFromGallery(new FragmentPickable<>(fragment), imagePickerCallback);
	}

	public <T extends Activity> void onActivityResult(@NonNull T activity, int requestCode, int resultCode, @Nullable Intent data) {
		onActivityResult(new ActivityPickable<>(activity), requestCode, resultCode, data);
	}

	public <T extends Fragment> void onActivityResult(@NonNull T fragment, int requestCode, int resultCode, @Nullable Intent data) {
		onActivityResult(new FragmentPickable<>(fragment), requestCode, resultCode, data);
	}

	private <T> void pickImageFromCamera(@NonNull ImagePickable<T> imagePickable, @NonNull final PickedAction<T> pickedAction) {
		pickImageFromCamera(imagePickable, new ImagePickerCallback<T>() {
			@Override
			public void onImagePicked(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path) {
				pickedAction.run(pickable, bitmap, path);
			}

			@Override
			public void onImageCanceled(@NonNull T pickable) {}
		});
	}

	private <T> void pickImageFromGallery(@NonNull ImagePickable<T> imagePickable, @NonNull final PickedAction<T> pickedAction) {
		pickImageFromGallery(imagePickable, new ImagePickerCallback<T>() {
			@Override
			public void onImagePicked(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path) {
				pickedAction.run(pickable, bitmap, path);
			}

			@Override
			public void onImageCanceled(@NonNull T pickable) {}
		});
	}

	private <T> void pickImageFromCamera(@NonNull ImagePickable<T> imagePickable, @NonNull final PickedAction<T> pickedAction, @NonNull final CanceledAction<T> canceledAction) {
		pickImageFromCamera(imagePickable, new ImagePickerCallback<T>() {
			@Override
			public void onImagePicked(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path) {
				pickedAction.run(pickable, bitmap, path);
			}

			@Override
			public void onImageCanceled(@NonNull T pickable) {
				canceledAction.run(pickable);
			}
		});
	}

	private <T> void pickImageFromGallery(@NonNull ImagePickable<T> imagePickable, @NonNull final PickedAction<T> pickedAction, @NonNull final CanceledAction<T> canceledAction) {
		pickImageFromGallery(imagePickable, new ImagePickerCallback<T>() {
			@Override
			public void onImagePicked(@NonNull T pickable, @NonNull Bitmap bitmap, @NonNull String path) {
				pickedAction.run(pickable, bitmap, path);
			}

			@Override
			public void onImageCanceled(@NonNull T pickable) {
				canceledAction.run(pickable);
			}
		});
	}

	private <T> void pickImageFromCamera(@NonNull ImagePickable<T> imagePickable, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

		try {
			File file = createImageFile(mAlbumDirectoryName);
			mImageFromCameraPath = file.getAbsolutePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
		} catch (IOException e) {
			e.printStackTrace();
			mImageFromCameraPath = null;
		}

		mImagePickerCallback = imagePickerCallback;
		imagePickable.startActivityForResult(intent, REQUEST_CODE_CAMERA);
	}

	private <T> void pickImageFromGallery(@NonNull ImagePickable<T> imagePickable, @NonNull ImagePickerCallback<T> imagePickerCallback) {
		Intent intent = new Intent(Intent.ACTION_PICK);
		intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/jpeg");

		mImagePickerCallback = imagePickerCallback;
		imagePickable.startActivityForResult(intent, REQUEST_CODE_GALLERY);
	}

	@SuppressWarnings("unchecked")
	private <T> void onActivityResult(@NonNull ImagePickable<T> imagePickable, int requestCode, int resultCode, @Nullable Intent data) {
		if (mImagePickerCallback != null) {
			if (resultCode == Activity.RESULT_OK) {
				switch (requestCode) {
					case REQUEST_CODE_CAMERA: {
						Pair<Bitmap, String> result = handleImageFromCamera();
						Bitmap bitmap = result.first;
						String path = result.second;
						if (bitmap != null && path != null) {
							mImagePickerCallback.onImagePicked(imagePickable.getPickable(), bitmap, path);
						}
						break;
					}

					case REQUEST_CODE_GALLERY: {
						Pair<Bitmap, String> result = handleImageFromGallery(data);
						Bitmap bitmap = result.first;
						String path = result.second;
						if (bitmap != null && path != null) {
							mImagePickerCallback.onImagePicked(imagePickable.getPickable(), bitmap, path);
						}
						break;
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				mImagePickerCallback.onImageCanceled(imagePickable.getPickable());
			}
			mImagePickerCallback = null;
		}
	}

	@NonNull
	private Pair<Bitmap, String> handleImageFromCamera() {
		Bitmap bitmap = null;
		String imageFromCameraPath = mImageFromCameraPath;

		if (imageFromCameraPath != null) {
			addImageToGallery(imageFromCameraPath);
			bitmap = handleImage(imageFromCameraPath);
			mImageFromCameraPath = null;
		}

		return new Pair<>(bitmap, imageFromCameraPath);
	}

	@NonNull
	private Pair<Bitmap, String> handleImageFromGallery(@Nullable Intent data) {
		Bitmap bitmap = null;
		String imageFromGalleryPath = null;

		if (data != null && data.getData() != null) {
			Uri imageFromGalleryUri = data.getData();
			imageFromGalleryPath = getPathFromUri(imageFromGalleryUri);

			if (imageFromGalleryPath != null) {
				bitmap = handleImage(imageFromGalleryPath);
			}
		}

		return new Pair<>(bitmap, imageFromGalleryPath);
	}

	private File createImageFile(@NonNull String albumDirectoryName) throws IOException {
		String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss", Locale.US).format(new Date());
		String imageFileName = "IMG_" + timestamp + "_";
		File albumFile = getAlbumDirectory(albumDirectoryName);
		return File.createTempFile(imageFileName, ".jpg", albumFile);
	}

	private File getAlbumDirectory(@NonNull String albumDirectoryName) {
		File storageDirectory = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
			storageDirectory = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albumDirectoryName);
			if (!storageDirectory.mkdirs()) {
				if (!storageDirectory.exists()) {
					Log.e(TAG, "failed to create album directory");
					return null;
				}
			}
		} else {
			Log.e(TAG, "external storage is not mounted");
		}
		return storageDirectory;
	}

	@Nullable
	private String getPathFromUri(@NonNull Uri uri) {
		String path = null;
		if (mContext != null) {
			String[] projection = {MediaStore.Images.Media.DATA};
			Cursor cursor = mContext.getContentResolver().query(uri, projection, null, null, null);
			if (cursor != null) {
				cursor.moveToFirst();
				int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				path = cursor.getString(columnIndex);
				cursor.close();
			}
		}
		return path;
	}

	private void addImageToGallery(@NonNull String imageFromCameraPath) {
		Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
		File file = new File(imageFromCameraPath);
		Uri uri = Uri.fromFile(file);
		intent.setData(uri);
		if (mContext != null) {
			mContext.sendBroadcast(intent);
		}
	}

	private Bitmap handleImage(String imagePath) {
		BitmapFactory.Options bitmapOptions = new BitmapFactory.Options();
		bitmapOptions.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(imagePath, bitmapOptions);

		// set bitmap options to scale the image decode target
		bitmapOptions.inJustDecodeBounds = false;
		bitmapOptions.inSampleSize = 4;

		// decode the JPEG file into a bitmap
		Bitmap originalBitmap = null;
		Bitmap bitmap = null;
		try {
			originalBitmap = BitmapFactory.decodeFile(imagePath, bitmapOptions);
			bitmap = BitmapScaler.scaleToFill(originalBitmap, mImageSize, mImageSize);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		} finally {
			if (originalBitmap != null) {
				originalBitmap.recycle();
			}
		}

		// handle bitmap rotation
		int rotation = getBitmapRotation(imagePath);
		if (rotation > 0) {
			Matrix matrix = new Matrix();
			matrix.setRotate(rotation, (float) bitmap.getWidth() / 2, (float) bitmap.getHeight() / 2);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}

		return bitmap;
	}

	private int getBitmapRotation(String imagePath) {
		int rotation;
		switch (getExifOrientation(imagePath)) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				rotation = 90;
				break;

			case ExifInterface.ORIENTATION_ROTATE_180:
				rotation = 180;
				break;

			case ExifInterface.ORIENTATION_ROTATE_270:
				rotation = 270;
				break;

			default:
				rotation = 0;
				break;
		}
		return rotation;
	}

	private int getExifOrientation(String imagePath) {
		int orientation = 0;
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, -1);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return orientation;
	}

	private static class ActivityPickable<T extends Activity> implements ImagePickable<T> {
		private T mActivity;

		public ActivityPickable(@NonNull T activity) {
			mActivity = activity;
		}

		@Override
		public T getPickable() {
			return mActivity;
		}

		@Override
		public void startActivityForResult(Intent intent, int requestCode) {
			mActivity.startActivityForResult(intent, requestCode);
		}
	}

	private static class FragmentPickable<T extends Fragment> implements ImagePickable<T> {
		private T mFragment;

		public FragmentPickable(@NonNull T fragment) {
			mFragment = fragment;
		}

		@Override
		public T getPickable() {
			return mFragment;
		}

		@Override
		public void startActivityForResult(Intent intent, int requestCode) {
			mFragment.startActivityForResult(intent, requestCode);
		}
	}
}
