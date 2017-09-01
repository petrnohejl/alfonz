package org.alfonz.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SoundManager
{
	public static final int PLAY_SINGLE = 0;            // play 1 sound at the moment, immediately stop all currently playing sounds
	public static final int PLAY_SINGLE_CONTINUE = 1;   // play 1 sound at the moment, if the sound is same as currently playing sound, continue playing
	public static final int PLAY_MULTIPLE_CONTINUE = 2; // play multiple sounds at the moment, if the sound is same as currently playing sound, continue playing

	private Context mContext;
	@Mode private int mMode;
	private Map<String, MediaPlayer> mMediaMap;


	@Retention(RetentionPolicy.SOURCE)
	@IntDef({PLAY_SINGLE, PLAY_SINGLE_CONTINUE, PLAY_MULTIPLE_CONTINUE})
	public @interface Mode {}


	public SoundManager(@NonNull Context context, @Mode int mode)
	{
		mContext = context.getApplicationContext();
		mMode = mode;
		mMediaMap = new HashMap<>();
	}


	public void play(@NonNull String path)
	{
		playSound(path, null);
	}


	public void playAsset(@NonNull String filename)
	{
		// get sound
		AssetFileDescriptor assetFileDescriptor;
		try
		{
			assetFileDescriptor = mContext.getAssets().openFd(filename);
		}
		catch(IOException e)
		{
			e.printStackTrace();
			return;
		}

		// play
		playSound(filename, assetFileDescriptor);
	}


	// should be called in Activity.onStop()
	public void stopAll()
	{
		Collection<MediaPlayer> collection = mMediaMap.values();
		Iterator<MediaPlayer> iterator = collection.iterator();

		while(iterator.hasNext())
		{
			MediaPlayer mediaPlayer = iterator.next();
			if(mediaPlayer != null)
			{
				if(mediaPlayer.isPlaying()) mediaPlayer.stop();
				mediaPlayer.release();
			}
		}

		mMediaMap.clear();
	}


	private void playSound(@NonNull final String path, @Nullable AssetFileDescriptor assetFileDescriptor)
	{
		// stop all currently playing sounds
		if(mMode == PLAY_SINGLE)
		{
			stopAll();
		}

		// sound already playing
		if(mMediaMap.containsKey(path))
		{
			return;
		}

		// stop all currently playing sounds
		if(mMode == PLAY_SINGLE_CONTINUE)
		{
			stopAll();
		}

		// init media player
		MediaPlayer mediaPlayer;
		try
		{
			mediaPlayer = new MediaPlayer();
			mMediaMap.put(path, mediaPlayer);

			// data source
			if(assetFileDescriptor != null)
			{
				mediaPlayer.setDataSource(assetFileDescriptor.getFileDescriptor(), assetFileDescriptor.getStartOffset(), assetFileDescriptor.getLength());
			}
			else
			{
				mediaPlayer.setDataSource(path);
			}

			mediaPlayer.prepareAsync();
		}
		catch(@NonNull IllegalArgumentException | IllegalStateException | IOException e)
		{
			e.printStackTrace();
			return;
		}

		// play sound
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(@NonNull MediaPlayer mediaPlayer)
			{
				mediaPlayer.start();
			}
		});

		// release media player
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(@Nullable MediaPlayer mediaPlayer)
			{
				mMediaMap.remove(path);
				if(mediaPlayer != null)
				{
					mediaPlayer.release();
				}
			}
		});
	}
}
