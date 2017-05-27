package com.example.maciek.magicznykociolek.Activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Graphics.GraphicScaling;
import com.example.maciek.magicznykociolek.Logic.TopScores;
import com.example.maciek.magicznykociolek.R;
import com.example.maciek.magicznykociolek.Sounds.MenuSoundHandler;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class MenuActivity extends AppCompatActivity {

    private ImageButton start;
    private ImageButton scores;
    private ImageButton authors;
    private ImageButton quit;
    private RelativeLayout scoresRL;
    private RelativeLayout menuRL;
    private TextView infoView;
    private static String authorsList = "Maciej Rychlik - Development" +
            "\nPatryk Sokół - Graphic Design";
    private int[] scoreArray;
    private String scoresString;
    private MenuSoundHandler soundHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_menu);
        soundHandler = new MenuSoundHandler(true,this);
        soundHandler.initializeSoundFX();
        soundHandler.playSoundEffect(0, Constants.DEFAULT_VOLUME);
        initElements();
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

    @Override
    public void onStop() {
        super.onStop();
        if (!Constants.shouldPlay) {
            this.soundHandler.getPool().autoPause();
        }
    }

    private void initElements(){
        Bundle b = getIntent().getExtras();
        int[] value = null;
        if (b != null)
            value = b.getIntArray("scores");
        if(value!=null) {
            scoreArray = value;
        }else{
            scoreArray = new TopScores().getHighScores();
        }
        this.scoresString = this.createScoresString();
        start = (ImageButton) findViewById(R.id.start);
        scores = (ImageButton) findViewById(R.id.imageButton5);
        authors = (ImageButton) findViewById(R.id.authors);
        quit = (ImageButton) findViewById(R.id.quit);
        scoresRL = (RelativeLayout) findViewById(R.id.score_rl);
        menuRL = (RelativeLayout) findViewById(R.id.activity_menu);
        menuRL.setBackgroundResource(R.drawable.main_menu_background);
        infoView = (TextView) findViewById(R.id.textView);
        scoresRL.setVisibility(View.INVISIBLE);
        menuRL.bringToFront();
        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                start.setImageResource(R.drawable.start_clicked);
                MediaPlayer mp = soundHandler.getMediaPlayer(R.raw.button_click);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        soundHandler.getPool().autoPause();
                        soundHandler.getPool().release();
                        Intent intent = new Intent(getApplicationContext(), Instructions.class);
                        Constants.shouldPlay = true;
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        scores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                soundHandler.playSoundEffect(1, Constants.DEFAULT_VOLUME);
                scores.setImageResource(R.drawable.scores_clicked);
                infoView.setText(scoresString);
                scoresRL.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_anim));
                scoresRL.setVisibility(View.VISIBLE);
                scoresRL.bringToFront();
                 new CountDownTimer(2000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {

                    }

                    @Override
                    public void onFinish() {
                        scoresRL.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_anim));
                        scoresRL.setVisibility(View.INVISIBLE);
                        scores.setImageResource(R.drawable.scores);
                        menuRL.bringToFront();
                    }
                }.start();
            }
        });
        authors.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                soundHandler.playSoundEffect(1, Constants.DEFAULT_VOLUME);
                authors.setImageResource(R.drawable.authors_clicked);
                infoView.setText(authorsList);
                scoresRL.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_in_anim));
                scoresRL.setVisibility(View.VISIBLE);
                scoresRL.bringToFront();
                new CountDownTimer(2000, 500) {
                    @Override
                    public void onTick(long millisUntilFinished) {
                    }

                    @Override
                    public void onFinish() {
                        scoresRL.startAnimation(AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fade_out_anim));
                        scoresRL.setVisibility(View.INVISIBLE);
                        authors.setImageResource(R.drawable.authors);
                        menuRL.bringToFront();
                    }
                }.start();
            }
        });

        quit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quit.setImageResource(R.drawable.exit_clicked);
                MediaPlayer mp = soundHandler.getMediaPlayer(R.raw.button_click);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        soundHandler.getPool().autoPause();
                        soundHandler.getPool().release();
                        finish();
                        System.exit(0);
                    }
                });

            }
        });
    }

    private String createScoresString(){
        String result = "";
        Arrays.sort(this.scoreArray);
        for (int i = 0; i < this.scoreArray.length / 2; i++) {
            int temp = scoreArray[i];
            scoreArray[i] = scoreArray[scoreArray.length - 1 - i];
            scoreArray[scoreArray.length - 1 - i] = temp;
        }
        for(int i = 0; i<this.scoreArray.length-1;i++){
            result+= ""+(i+1)+". "+this.scoreArray[i]+"\n";
            System.out.println(this.scoreArray[i]);
        }
        return result;
    }
}
