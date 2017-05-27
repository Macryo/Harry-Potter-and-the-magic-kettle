package com.example.maciek.magicznykociolek.Activity;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.maciek.magicznykociolek.Elixir.Elixir;
import com.example.maciek.magicznykociolek.Graphics.GameGraphics;
import com.example.maciek.magicznykociolek.Listeners.RepeatListener;
import com.example.maciek.magicznykociolek.Logic.ElixirGame;
import com.example.maciek.magicznykociolek.Logic.Player;
import com.example.maciek.magicznykociolek.R;
import com.example.maciek.magicznykociolek.Sounds.GameSoundHandler;

import java.util.List;

public class GameActivity extends AppCompatActivity {

    private ElixirGame game;
    private GameGraphics graphics;
    private GameSoundHandler gameSoundHandler;
    private boolean gameOver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_game_logic);

        this.graphics = new GameGraphics(this);
        this.graphics.initGameGraphics();
        this.gameSoundHandler = new GameSoundHandler(true,this);

        this.gameOver = false;

        this.game = new ElixirGame(this, this.graphics, this.gameSoundHandler);
        this.initElements();

        this.game.startGame();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(!this.gameOver){
            try{
                this.gameSoundHandler.getPool().autoPause();
                this.gameSoundHandler.getPool().release();
                this.game.stopGame();
                Log.d("On Stop", "Called cancel timers");
            } catch(Exception e) {
                Log.d("On Stop", "exception caught");
            }
        }else{
        }
        finish();
    }

    private void initElements() {
        ImageButton left = (ImageButton) findViewById(R.id.leftButton);
        ImageButton right = (ImageButton) findViewById(R.id.rightButton);
        left.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphics.playerMoveLeft();
            }
        }));
        right.setOnTouchListener(new RepeatListener(400, 100, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                graphics.playerMoveRight();
            }
        }));
    }

    @Override
    protected void onPause() {
        if (this.isFinishing()){
            this.gameSoundHandler.getPool().autoPause();
            this.gameSoundHandler.getPool().release();
        }
        Context context = getApplicationContext();
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> taskInfo = am.getRunningTasks(1);
        if (!taskInfo.isEmpty()) {
            ComponentName topActivity = taskInfo.get(0).topActivity;
            if (!topActivity.getPackageName().equals(context.getPackageName())) {
                this.gameSoundHandler.getPool().autoPause();
                this.gameSoundHandler.getPool().release();
            }

        }
        super.onPause();
    }

    public void gameOver() {
        this.gameOver = true;
        this.game.stopGame();
        Toast.makeText(this.getApplication(), "Koniec gry!",
                Toast.LENGTH_LONG).show();
        this.gameSoundHandler.getPool().autoPause();
        this.gameSoundHandler.getPool().release();
        this.graphics.getButton(0).setEnabled(false);
        this.graphics.getButton(1).setEnabled(false);
        Intent intent = new Intent(this, FinalScreen.class);
        Bundle b = new Bundle();
        b.putInt("score", this.game.getScore());
        intent.putExtras(b);
        startActivity(intent);
        finish();
    }

}
