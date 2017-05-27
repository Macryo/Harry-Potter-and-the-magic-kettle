package com.example.maciek.magicznykociolek.Sounds;

import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.support.v7.app.AppCompatActivity;

import com.example.maciek.magicznykociolek.Activity.Instructions;
import com.example.maciek.magicznykociolek.Activity.MenuActivity;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.R;

/**
 * Created by Maciek on 16.01.2017.
 */

public class MenuSoundHandler {

    private MediaPlayer player;
    private SoundPool pool;
    private int[] soundIndex = new int[2];
    private boolean soundOn;
    private AppCompatActivity menu;

    public MenuSoundHandler(boolean soundOn, AppCompatActivity menu) {
        this.soundOn = soundOn;
        this.menu = menu;
    }

    public MenuSoundHandler(AppCompatActivity menu) {
        this.menu = menu;
    }


    public MediaPlayer getMediaPlayer(int id){
        this.player = MediaPlayer.create(this.menu,id);
        return this.player;
    }

    public void initializeSoundFX(){
        this.pool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        this.soundIndex[0] = this.pool.load(this.menu, R.raw.menu_music,1);
        this.soundIndex[1] =  this.pool.load(this.menu, R.raw.button_click,1);
        this.pool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
            @Override
            public void onLoadComplete(SoundPool soundPool, int sampleId, int status) {
                soundPool.play(soundIndex[0], 100,100,1,-1,0.7f);
            }
        });
    }
    public SoundPool getPool() {
        return pool;
    }

    public void playSoundEffect(int id,int vol){
        if(soundOn){
            if(id==0) {
                this.pool.play(this.soundIndex[id], vol, vol,1, -1, 0.7f);
            }else {
                this.pool.play(this.soundIndex[id],vol,vol,1,0,1);
            }
        }
    }
}
