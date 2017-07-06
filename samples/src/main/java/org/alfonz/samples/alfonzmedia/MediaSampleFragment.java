package org.alfonz.samples.alfonzmedia;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.media.ImagePicker;
import org.alfonz.media.SoundManager;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.alfonzutility.utility.PermissionRationaleHandler;
import org.alfonz.samples.databinding.FragmentMediaSampleBinding;
import org.alfonz.utility.PermissionManager;


public class MediaSampleFragment extends BaseFragment<MediaSampleView, MediaSampleViewModel, FragmentMediaSampleBinding> implements MediaSampleView
{
	private SoundManager mSoundManager;
	private ImagePicker mImagePicker;
	private PermissionManager mPermissionManager;


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
		mSoundManager = new SoundManager(getContext(), SoundManager.PLAY_SINGLE);
		mImagePicker = new ImagePicker(getContext(), getString(R.string.app_name));
		mPermissionManager = new PermissionManager(this, new PermissionRationaleHandler());
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
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		mPermissionManager.onRequestPermissionsResult(requestCode, permissions, grantResults);
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
		mPermissionManager.request(
				Manifest.permission.READ_EXTERNAL_STORAGE,
				() -> mImagePicker.pickImageFromCamera(MediaSampleFragment.this));
	}


	@Override
	public void onButtonPickImageFromGalleryClick()
	{
		mPermissionManager.request(
				Manifest.permission.READ_EXTERNAL_STORAGE,
				() -> mImagePicker.pickImageFromGallery(MediaSampleFragment.this));
	}
}
