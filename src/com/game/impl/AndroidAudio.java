package com.game.impl;

import java.io.IOException;

import android.app.Activity;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;

import com.game.audio.Audio;
import com.game.audio.Music;
import com.game.audio.Sound;

public class AndroidAudio implements Audio {
	AssetManager assets;
	SoundPool soundPool;
	
	public AndroidAudio(Activity activity) {
		activity.setVolumeControlStream(AudioManager.STREAM_MUSIC);
		this.assets = activity.getAssets();
		/* By 20 we mean it can play 20 sound in parallel */
		this.soundPool = new SoundPool( 20, AudioManager.STREAM_MUSIC, 0); 
	}
	
	@Override
	public Music newMusic(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			return new AndroidMusic(assetDescriptor);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load music '" + filename + "'");
		}
	}

	@Override
	public Sound newSound(String filename) {
		try {
			AssetFileDescriptor assetDescriptor = assets.openFd(filename);
			/* Here we load before Playing the Sound */
			int soundId = soundPool.load(assetDescriptor, 0);
			return new AndroidSound(soundPool, soundId);
		} catch (IOException e) {
			throw new RuntimeException("Couldn't load sound '" + filename + "'");
		}
	}
}
