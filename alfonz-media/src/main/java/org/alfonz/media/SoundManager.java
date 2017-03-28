package org.alfonz.media;

import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class SoundManager
{
	private Context mContext;
	private Mode mMode;
	private Map<String, MediaPlayer> mMediaMap;


	public enum Mode
	{
		PLAY_SINGLE,           // play 1 sound at the moment, immediately stop all currently playing sounds
		PLAY_SINGLE_CONTINUE,  // play 1 sound at the moment, if the sound is same as currently playing sound, continue playing
		PLAY_MULTIPLE_CONTINUE // play multiple sounds at the moment, if the sound is same as currently playing sound, continue playing
	}


	public SoundManager(Context context, Mode mode)
	{
		mContext = context.getApplicationContext();
		mMode = mode;
		mMediaMap = new HashMap<>();
	}


	public void play(String path)
	{
		playSound(path, null);
	}


	public void playAsset(String filename)
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


	private void playSound(final String path, AssetFileDescriptor assetFileDescriptor)
	{
		// stop all currently playing sounds
		if(mMode.equals(Mode.PLAY_SINGLE))
		{
			stopAll();
		}

		// sound already playing
		if(mMediaMap.containsKey(path))
		{
			return;
		}

		// stop all currently playing sounds
		if(mMode.equals(Mode.PLAY_SINGLE_CONTINUE))
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
		catch(IllegalArgumentException | IllegalStateException | IOException e)
		{
			e.printStackTrace();
			return;
		}

		// play sound
		mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener()
		{
			@Override
			public void onPrepared(MediaPlayer mediaPlayer)
			{
				mediaPlayer.start();
			}
		});

		// release media player
		mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener()
		{
			@Override
			public void onCompletion(MediaPlayer mediaPlayer)
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
