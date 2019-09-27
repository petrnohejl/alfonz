package org.alfonz.samples.alfonzgraphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.LayoutInflater;

import org.alfonz.graphics.bitmap.BitmapBlur;
import org.alfonz.graphics.bitmap.BitmapReflection;
import org.alfonz.graphics.bitmap.BitmapScaler;
import org.alfonz.graphics.drawable.CircularDrawable;
import org.alfonz.graphics.drawable.PlaceholderDrawable;
import org.alfonz.graphics.drawable.RoundedDrawable;
import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentGraphicsSampleBinding;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;

public class GraphicsSampleFragment extends BaseFragment<GraphicsSampleViewModel, FragmentGraphicsSampleBinding> implements GraphicsSampleView {
	@Override
	public GraphicsSampleViewModel setupViewModel() {
		return ViewModelProviders.of(this).get(GraphicsSampleViewModel.class);
	}

	@Override
	public FragmentGraphicsSampleBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentGraphicsSampleBinding.inflate(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		setupImageViews();
	}

	private void setupImageViews() {
		setupImageViewBlur();
		setupImageViewReflection();
		setupImageViewScaler();
		setupImageViewCircular();
		setupImageViewRounded();
		setupImageViewPlaceholder();
	}

	private void setupImageViewBlur() {
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap blurredBitmap = BitmapBlur.getBlurredBitmap(getContext(), originalBitmap, 0.5F, 5F);
		originalBitmap.recycle();
		getBinding().graphicsSampleImageBlur.setImageBitmap(blurredBitmap);
	}

	private void setupImageViewReflection() {
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap reflectedBitmap = BitmapReflection.getReflectedBitmap(originalBitmap, 0);
		originalBitmap.recycle();
		getBinding().graphicsSampleImageReflection.setImageBitmap(reflectedBitmap);
	}

	private void setupImageViewScaler() {
		Bitmap originalBitmap = BitmapFactory.decodeResource(getContext().getResources(), R.drawable.photo);
		Bitmap scaledBitmap = BitmapScaler.scaleToFill(originalBitmap, 32, 32);
		originalBitmap.recycle();
		getBinding().graphicsSampleImageScaler.setImageBitmap(scaledBitmap);
	}

	private void setupImageViewCircular() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		CircularDrawable drawable = new CircularDrawable(bitmap);
		getBinding().graphicsSampleImageCircular.setImageDrawable(drawable);
	}

	private void setupImageViewRounded() {
		Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.photo);
		RoundedDrawable drawable = new RoundedDrawable(bitmap, 32);
		getBinding().graphicsSampleImageRounded.setImageDrawable(drawable);
	}

	private void setupImageViewPlaceholder() {
		PlaceholderDrawable drawable = new PlaceholderDrawable(getString(R.string.app_name), "?", 50, true);
		getBinding().graphicsSampleImagePlaceholder.setImageDrawable(drawable);
	}
}
