package com.example.maciek.magicznykociolek.Activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Graphics.FinalScreenGraphics;
import com.example.maciek.magicznykociolek.Graphics.GraphicScaling;
import com.example.maciek.magicznykociolek.Logic.TopScores;
import com.example.maciek.magicznykociolek.R;
import com.example.maciek.magicznykociolek.Sounds.FinalActivitySoundHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FinalScreen extends AppCompatActivity {

    private int[] highScores;
    private FinalActivitySoundHandler soundHandler;
    private FinalScreenGraphics graphics;
    private ImageButton menu;
    private ImageButton repeat;
    private TextView score;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_final_screen);
        Bundle b = getIntent().getExtras();
        int value = b.getInt("score");
        TopScores scores = new TopScores();
        scores.checkIfBetter(value);
        this.highScores = scores.getHighScores();
        soundHandler = new FinalActivitySoundHandler(true, this);
        soundHandler.initializeSoundFX();
        initButtons(value);
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){
            this.soundHandler.getPool().autoPause();
            this.soundHandler.getPool().release();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                this.soundHandler.getPool().autoPause();
                this.soundHandler.getPool().release();
            }
        }
        super.onPause();
    }

    private void initButtons(int value) {
        this.mediaPlayer = this.soundHandler.getMediaPlayer(R.raw.final_music);
        this.mediaPlayer.start();
        this.graphics = new FinalScreenGraphics(this);
        this.graphics.initGraphics();
        GraphicScaling scaling = new GraphicScaling(this);
        this.menu = (ImageButton) findViewById(R.id.imageButton);
        this.menu.setBackgroundColor(Color.TRANSPARENT);
        this.repeat = (ImageButton) findViewById(R.id.imageButton2);
        this.repeat.setBackgroundColor(Color.TRANSPARENT);
        scaling.scaleBackground(((RelativeLayout) findViewById(R.id.activity_final_screen)));
        this.score = (TextView) findViewById(R.id.score_info);
        this.score.setText(String.valueOf(value));
        menu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                menu.setImageResource(R.drawable.menu_clicked);
                MediaPlayer mp = soundHandler.getMediaPlayer(R.raw.button_click);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        soundHandler.getPool().autoPause();
                        soundHandler.getPool().release();
                        Intent intent = new Intent(getApplicationContext(), MenuActivity.class);
                        Bundle b = new Bundle();
                        b.putIntArray("scores", highScores);
                        intent.putExtras(b);
                        startActivity(intent);
                        finish();
                    }
                });

            }
        });

        repeat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                repeat.setImageResource(R.drawable.repeat_clicked);
                MediaPlayer mp = soundHandler.getMediaPlayer(R.raw.button_click);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        mediaPlayer.stop();
                        mediaPlayer.release();
                        soundHandler.getPool().autoPause();
                        soundHandler.getPool().release();
                        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

    }


}
