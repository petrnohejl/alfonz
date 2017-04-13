package org.alfonz.samples.alfonzmedia;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.media.ImagePicker;
import org.alfonz.media.SoundManager;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.alfonzutility.utility.PermissionHelper;
import org.alfonz.samples.databinding.FragmentMediaSampleBinding;


public class MediaSampleFragment extends BaseFragment<MediaSampleView, MediaSampleViewModel, FragmentMediaSampleBinding> implements MediaSampleView
{
	private SoundManager mSoundManager;
	private ImagePicker mImagePicker;


	@Nullable
	@Override
	public Class<MediaSampleViewModel> getViewModelClass()
	{
		return MediaSampleViewModel.class;
	}


	@Override
	public FragmentMediaSampleBinding inflateBindingLayout(LayoutInflater inflater)
	{
		return FragmentMediaSampleBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mSoundManager = new SoundManager(getContext(), SoundManager.Mode.PLAY_SINGLE);
		mImagePicker = new ImagePicker(getContext(), getString(R.string.app_name));
	}


	@Override
	public void onStop()
	{
		super.onStop();
		mSoundManager.stopAll();
	}


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
						getViewModel().updateBitmap(bitmap);
					}
					break;
				}

				case ImagePicker.ACTION_PICK_IMAGE_FROM_GALLERY:
				{
					Bitmap bitmap = mImagePicker.handleImageFromGallery(data);
					if(bitmap != null)
					{
						getViewModel().updateBitmap(bitmap);
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


	@Override
	public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
	{
		// handle permissions result here
	}


	@Override
	public void onButtonPlaySoundClick()
	{
		mSoundManager.playAsset("sound.mp3");
	}


	@Override
	public void onButtonStopSoundClick()
	{
		mSoundManager.stopAll();
	}


	@Override
	public void onButtonPickImageFromCameraClick()
	{
		if(PermissionHelper.checkPermissionReadExternalStorage(this))
		{
			mImagePicker.pickImageFromCamera(this);
		}
	}


	@Override
	public void onButtonPickImageFromGalleryClick()
	{
		if(PermissionHelper.checkPermissionReadExternalStorage(this))
		{
			mImagePicker.pickImageFromGallery(this);
		}
	}
}
