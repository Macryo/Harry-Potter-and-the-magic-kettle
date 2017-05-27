package com.example.maciek.magicznykociolek.Sounds;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.example.maciek.magicznykociolek.Activity.FinalScreen;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.R;

/**
 * Created by Maciek on 16.01.2017.
 */

public class FinalActivitySoundHandler {
    private SoundPool pool;
    private int[] soundIndex = new int[2];
    private boolean soundOn;
    private FinalScreen screen;
    private MediaPlayer player;

    public FinalActivitySoundHandler(boolean soundOn, FinalScreen screen) {
        this.soundOn = soundOn;
        this.screen = screen;
    }

    public void initializeSoundFX(){
        this.pool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        this.soundIndex[0] = this.pool.load(this.screen, R.raw.final_music,1);
        this.soundIndex[1] =  this.pool.load(this.screen, R.raw.button_click,1);
        this.pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundIndex[0], Constants.DEFAULT_VOLUME,Constants.DEFAULT_VOLUME,1,0,0.7f);
            }
        });
    }

    public FinalActivitySoundHandler(FinalScreen screen) {
        this.screen = screen;
    }
    public MediaPlayer getMediaPlayer(int id)
    {
        this.player = MediaPlayer.create(this.screen,id);
        return this.player;
    }

    public SoundPool getPool() {
        return pool;
    }

    public void playSoundEffect(int id,int vol){
        if(soundOn){
            if(id==0) {
                this.pool.play(this.soundIndex[id], vol, vol,1, 0, 0.7f);
            }else{
                this.pool.play(this.soundIndex[id],vol,vol,1,0,1);
            }
        }
    }
}
