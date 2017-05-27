package com.example.maciek.magicznykociolek.Sounds;

import android.media.AudioManager;
import android.media.SoundPool;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.R;

/**
 * Created by Maciek on 13.01.2017.
 */

public class GameSoundHandler {


    private SoundPool pool;
    private int[] soundIndex = new int[3];
    private boolean soundOn;
    private GameActivity gameActivity;

    public GameSoundHandler(boolean soundOn, GameActivity gameActivity) {
        this.soundOn = soundOn;
        this.gameActivity = gameActivity;
    }

    public void initializeSoundFX(){
        this.pool = new SoundPool(5, AudioManager.STREAM_MUSIC,0);
        this.soundIndex[0] = this.pool.load(this.gameActivity, R.raw.game_music,1);
        this.soundIndex[1] =  this.pool.load(this.gameActivity, R.raw.glass_break,1);
        this.soundIndex[2] = this.pool.load(this.gameActivity,R.raw.drop,1);
    }
    public SoundPool getPool() {
        return pool;
    }

    public void playSoundEffect(int id,int vol){
        if(soundOn){
            if(id==0) {
                this.pool.play(this.soundIndex[id], vol, vol,1, -1, 0.7f);
            }else if(id==1){
                this.pool.play(this.soundIndex[id],vol,vol,1,0,1);
            }else{
                this.pool.play(this.soundIndex[id],vol,vol,1,0,1);
            }
        }
    }
}
