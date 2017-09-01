package org.alfonz.samples.alfonzgraphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;

import org.alfonz.graphics.bitmap.BitmapBlur;
import org.alfonz.graphics.bitmap.BitmapReflection;
import org.alfonz.graphics.bitmap.BitmapScaler;
import org.alfonz.graphics.drawable.CircularDrawable;
import org.alfonz.graphics.drawable.RoundedDrawable;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzmvvm.BaseFragment;
import org.alfonz.samples.databinding.FragmentGraphicsSampleBinding;


public class GraphicsSampleFragment extends BaseFragment<GraphicsSampleView, GraphicsSampleViewModel, FragmentGraphicsSampleBinding> implements GraphicsSampleView
{
	@Nullable
	@Override
	public Class<GraphicsSampleViewModel> getViewModelClass()
	{
		return GraphicsSampleViewModel.class;
	}


	@Override
	public FragmentGraphicsSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater)
	{
		return FragmentGraphicsSampleBinding.inflate(inflater);
	}


	@Override
	public void onActivityCreated(Bundle savedInstanceState)
	{
		super.onActivityCreated(savedInstanceState);
		setupImageViews();
	}


	private void setupImageViews()
	{
		setupImageViewBlur();
		setupImageViewReflection();
		setupImageViewScaler();
		setupImageViewCircular();
		setupImageViewRounded();
	}


	private void setupImageViewBlur()
	{
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap blurredBitmap = BitmapBlur.getBlurredBitmap(getContext(), originalBitmap, 0.5F, 5F);
		originalBitmap.recycle();
		getBinding().fragmentGraphicsSampleImageBlur.setImageBitmap(blurredBitmap);
	}


	private void setupImageViewReflection()
	{
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap reflectedBitmap = BitmapReflection.getReflectedBitmap(originalBitmap, 0);
		originalBitmap.recycle();
		getBinding().fragmentGraphicsSampleImageReflection.setImageBitmap(reflectedBitmap);
	}


	private void setupImageViewScaler()
	{
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap scaledBitmap = BitmapScaler.scaleToFill(originalBitmap, 32, 32);
		originalBitmap.recycle();
		getBinding().fragmentGraphicsSampleImageScaler.setImageBitmap(scaledBitmap);
	}


	private void setupImageViewCircular()
	{
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		CircularDrawable drawable = new CircularDrawable(bitmap);
		getBinding().fragmentGraphicsSampleImageCircular.setImageDrawable(drawable);
	}


	private void setupImageViewRounded()
	{
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		RoundedDrawable drawable = new RoundedDrawable(bitmap, 32);
		getBinding().fragmentGraphicsSampleImageRounded.setImageDrawable(drawable);
	}
}
