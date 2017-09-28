package org.alfonz.samples.alfonzmedia;

import android.Manifest;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.media.ImagePicker;
import org.alfonz.media.SoundManager;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentMediaSampleBinding;


public class MediaSampleFragment extends BaseFragment<MediaSampleViewModel, FragmentMediaSampleBinding> implements MediaSampleView
{
	private SoundManager mSoundManager;
	private ImagePicker mImagePicker;


	@Override
	public MediaSampleViewModel setupViewModel()
	{
		return ViewModelProviders.of(this).get(MediaSampleViewModel.class);
	}


	@Override
	public FragmentMediaSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentMediaSampleBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		mSoundManager = new SoundManager(getContext(), SoundManager.PLAY_SINGLE);
		mImagePicker = new ImagePicker(getContext(), getString(R.string.app_name));
	}


	@Override
	public void onStop()
	{
		super.onStop();
		mSoundManager.stopAll();
	}


	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		mImagePicker.onActivityResult(this, requestCode, resultCode, data);
	}


	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
	{
		getViewModel().permissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
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
		getViewModel().permissionManager.request(
				this,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				requestable -> requestable.pickImageFromCamera(requestable));
	}


	@Override
	public void onButtonPickImageFromGalleryClick()
	{
		getViewModel().permissionManager.request(
				this,
				Manifest.permission.READ_EXTERNAL_STORAGE,
				requestable -> requestable.pickImageFromGallery(requestable));
	}


	public void pickImageFromCamera(MediaSampleFragment fragment)
	{
		mImagePicker.pickImageFromCamera(
				fragment,
				(pickable, bitmap, path) -> pickable.getViewModel().updateBitmap(bitmap),
				pickable -> pickable.showToast("Canceled"));
	}


	public void pickImageFromGallery(MediaSampleFragment fragment)
	{
		mImagePicker.pickImageFromGallery(
				fragment,
				(pickable, bitmap, path) -> pickable.getViewModel().updateBitmap(bitmap),
				pickable -> pickable.showToast("Canceled"));
	}
}
