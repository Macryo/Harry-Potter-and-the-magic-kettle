package com.example.maciek.magicznykociolek.Activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Graphics.GraphicScaling;
import com.example.maciek.magicznykociolek.R;
import com.example.maciek.magicznykociolek.Sounds.MenuSoundHandler;

import java.util.List;

public class Instructions extends AppCompatActivity {

    private MenuSoundHandler soundHandler;
    private GraphicScaling scaling;
    private ImageButton button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_instructions);
        this.initElements();
        this.soundHandler = new MenuSoundHandler(true, this);
        this.soundHandler.initializeSoundFX();
        this.soundHandler.playSoundEffect(0, Constants.DEFAULT_VOLUME);

    }

    @Override
    protected void onPause() {
        if (this.isFinishing()) {
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

    private void initElements() {
        this.scaling = new GraphicScaling(this);
        this.button = (ImageButton) findViewById(R.id.imageButton3);
        this.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setImageResource(R.drawable.start_clicked);
                MediaPlayer mp = soundHandler.getMediaPlayer(R.raw.button_click);
                mp.start();
                mp.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        mp.stop();
                        mp.release();
                        soundHandler.getPool().autoPause();
                        soundHandler.getPool().release();
                        Intent intent = new Intent(getApplicationContext(), GameActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        });

        ImageView elix1 = (ImageView) findViewById(R.id.imageView);
        ImageView elix2 = (ImageView) findViewById(R.id.imageView4);
        ImageView elix3 = (ImageView) findViewById(R.id.imageView5);
        ImageView elix4 = (ImageView) findViewById(R.id.imageView6);
        ImageView elix5 = (ImageView) findViewById(R.id.imageView15);
        ImageView elix6 = (ImageView) findViewById(R.id.imageView12);
        ImageView elix7 = (ImageView) findViewById(R.id.imageView13);
        ImageView elix8 = (ImageView) findViewById(R.id.imageView14);
        elix1.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir1, 100, 100));
        elix2.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir2, 100, 100));
        elix3.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir3, 100, 100));
        elix4.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir4, 100, 100));
        elix5.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir5, 100, 100));
        elix6.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir6, 100, 100));
        elix7.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir7, 100, 100));
        elix8.setImageBitmap(this.scaling.scaleGraphicToDisplay(R.drawable.elixir8, 100, 100));
    }

}
