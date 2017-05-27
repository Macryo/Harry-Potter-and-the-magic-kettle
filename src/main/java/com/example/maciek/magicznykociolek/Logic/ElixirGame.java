package com.example.maciek.magicznykociolek.Logic;

import android.os.CountDownTimer;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.Elixir.Elixir;
import com.example.maciek.magicznykociolek.Elixir.Elixirs;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Graphics.GameGraphics;
import com.example.maciek.magicznykociolek.R;
import com.example.maciek.magicznykociolek.Sounds.GameSoundHandler;

import java.util.Random;

import static com.example.maciek.magicznykociolek.GameConstants.Constants.NULL;

/**
 * Created by Maciek on 11.01.2017.
 */

public class ElixirGame {

    private boolean gameInSession = false;
    private GameActivity logic;
    private Handler countdownHandler;
    private Runnable countdownRunnable;
    private GameSoundHandler soundHandler;
    private Animation elixirAnimation;
    private boolean animationStarted;
    private int elixirFallDelay;
    private Handler elixirDelayHandler;
    private Handler levelHandler;
    private Handler elixirIntervalHandler;
    private Runnable elixirDelayRunnable;
    private Runnable levelRunnable;
    private Runnable elixirIntervalRunnable;
    private int scoreCount;
    private boolean handlerStarted;
    private int countdown;
    private GameGraphics gameGraphics;
    private int snapeDialogPopUp = 50000;
    private boolean snapeDialogHasShown = false;
    private boolean snapeDialogHasFleed = false;

    public ElixirGame(GameActivity logic, GameGraphics gameGraphics, GameSoundHandler soundHandler) {
        this.logic = logic;
        this.soundHandler = soundHandler;
        this.soundHandler.initializeSoundFX();
        this.gameGraphics = gameGraphics;
        scoreCount = NULL;
        this.elixirFallDelay = Constants.ELIXIR_DELAY_TIME_DECREMENT_MED;
        handlerStarted = false;
        gameInSession = false;
    }

    public Player getPlayer() {
        return this.gameGraphics.getPlayer();
    }

    public int getScore() {
        return this.scoreCount;
    }


    public void startGame() {
        this.gameInSession = true;
        this.startCountdown();
    }

    private void startGameTimer() {
        new CountDownTimer(103000, 1000) {

            public void onTick(long millisUntilFinished) {
                if (millisUntilFinished <= 100000) {
                    gameGraphics.getTimer().setText(String.valueOf(millisUntilFinished / 1000));
                }
                if (millisUntilFinished <= snapeDialogPopUp && !snapeDialogHasShown){
                    snapeDialogHasShown = true;
                    if(scoreCount>200){
                        gameGraphics.getSnapeDialog(true).setVisibility(View.VISIBLE);
                        gameGraphics.getSnapeDialog(true).startAnimation(AnimationUtils.loadAnimation(logic,R.anim.fade_in_anim));
                    }else{
                        gameGraphics.getSnapeDialog(false).setVisibility(View.VISIBLE);
                        gameGraphics.getSnapeDialog(false).startAnimation(AnimationUtils.loadAnimation(logic,R.anim.fade_in_anim));
                    }
                }
                if(millisUntilFinished<=40000 && snapeDialogHasShown && !snapeDialogHasFleed){
                    snapeDialogHasFleed = true;
                    if(scoreCount>200){
                        gameGraphics.getSnapeDialog(true).setVisibility(View.INVISIBLE);
                        gameGraphics.getSnapeDialog(true).startAnimation(AnimationUtils.loadAnimation(logic,R.anim.fade_out_anim));
                    }else{
                        gameGraphics.getSnapeDialog(false).setVisibility(View.INVISIBLE);
                        gameGraphics.getSnapeDialog(false).startAnimation(AnimationUtils.loadAnimation(logic,R.anim.fade_out_anim));
                    }
                }
            }

            public void onFinish() {
                logic.gameOver();
            }
        }.start();
    }

    private void startCountdown() {
        this.gameGraphics.getCountdownView().setVisibility(View.VISIBLE);
        this.gameGraphics.getButton(0).setEnabled(false);
        this.gameGraphics.getButton(1).setEnabled(false);
        this.gameGraphics.getCountdownView().bringToFront();
        this.countdown = 3;
        this.countdownHandler = new Handler();
        this.countdownRunnable = new Runnable() {
            @Override
            public void run() {
                gameGraphics.getCountdownView().setText("Ready?");
                soundHandler.playSoundEffect(0,Constants.DEFAULT_VOLUME);
                while (true) {
                    countdown--;
                    if (countdown == 0) {
                        gameGraphics.getCountdownView().setText("Go!");
                        gameGraphics.getCountdownView().startAnimation(gameGraphics.getFadeout());
                        if (gameInSession) {
                            initiateGameHandlers();

                            countdownHandler.removeCallbacks(countdownRunnable);
                        }
                        break;
                    }
                    try {
                        synchronized (this) {
                            wait(1000);
                        }
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        this.countdownHandler.postDelayed(this.countdownRunnable, 400);
        this.startGameTimer();
    }

    private void initiateGameHandlers() {
        this.levelHandler = new Handler();
        this.gameGraphics.getButton(0).setEnabled(true);
        this.gameGraphics.getButton(1).setEnabled(true);
        this.gameGraphics.getCountdownView().setVisibility(View.GONE);
        this.levelRunnable = new Runnable() {
            @Override
            public void run() {
                createElixirFallHandler();
                if (gameInSession) {
                    levelHandler.postDelayed(levelRunnable, 1500);
                }
            }
        };
        levelHandler.post(levelRunnable);
        handlerStarted = true;
    }


    private void createElixirFallHandler() {
        this.elixirIntervalHandler = new Handler();
        this.elixirIntervalRunnable = new Runnable() {
            @Override
            public void run() {
                Elixir elixir = generateRandomElixir();
                startElixirFall(elixir);
                gameGraphics.refreshScreen();
            }
        };
        if (gameInSession) {
            elixirIntervalHandler.postDelayed(elixirIntervalRunnable, this.elixirFallDelay);
        }
    }

    private Elixir generateRandomElixir() {
        Random rand = new Random();
        Elixirs elixir;
        int elixirType = rand.nextInt(100);

        if(elixirType<=24){
            elixir = Elixirs.ELIXIR_FELIX_FELICIS;
        }else if(elixirType>24 && elixirType <= 45){
            elixir = Elixirs.ELIXIR_VERITASERUM;
        }else if(elixirType>45 && elixirType <=55){
            elixir = Elixirs.ELIXIR_TOJADOWY;
        }else if(elixirType >55 && elixirType <=60){
            elixir = Elixirs.ELIXIR_AMORTENCJA;
        }else if(elixirType > 60 && elixirType <=75){
            elixir = Elixirs.ELIXIR_SLUZU_GIGANTA;
        }else if(elixirType > 75 && elixirType <= 85){
            elixir = Elixirs.ELIXIR_SANGRE_DE_DIABLO;
        }else if(elixirType >85 && elixirType <= 95){
            elixir = Elixirs.ELIXIR_ROZPACZY;
        }else{
            elixir = Elixirs.ELIXIR_ZYWEJ_SMIERCI;
        }

        Elixir generatedElixir = new Elixir(this.logic, elixir);
        generatedElixir.setImageBitmap(this.gameGraphics.getElixirGraphics(elixir));
        generatedElixir.setY(0);
        int range = this.gameGraphics.getDeviceWidth() - 430;
        int position = rand.nextInt(range);
        generatedElixir.setX(position + 170);
        return generatedElixir;
    }

    private void startElixirFall(final Elixir elixir) {
        final int elixirX = (int) this.gameGraphics.getImageXCenter(elixir);
        this.gameGraphics.getRl().addView(elixir);
        int delayElixirFall = 1000;
        this.elixirDelayHandler = new Handler();
        this.elixirDelayRunnable = new Runnable() {
            @Override
            public void run() {
                animationStarted = true;
                elixirAnimation = AnimationUtils.loadAnimation(logic, R.anim.elixir_drop);
                elixir.startAnimation(elixirAnimation);
                setMyAnimListener(elixirX, elixir);
            }
        };
        this.elixirDelayHandler.postDelayed(this.elixirDelayRunnable, delayElixirFall);
    }

    private void setMyAnimListener(final int x, final Elixir elixir) {
        Animation.AnimationListener animOkListener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (handlerStarted) {
                    elixir.setVisibility(View.GONE);
                    checkIfScored(x, elixir.getElixirType());
                    animationStarted = false;
                }
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        };
        this.elixirAnimation.setAnimationListener(animOkListener);
    }

    private void checkIfScored(int position, Elixirs type) {
        boolean caught = false;
        float xBasketMaxRange;
        float xBasketMinRange;
        if (this.gameGraphics.getPlayer().getPlayerDirection() == Constants.DIRECTION_LEFT) {
            xBasketMaxRange = this.gameGraphics.getImageXCenter(this.getPlayer()) - 190;
            xBasketMinRange = this.gameGraphics.getImageXCenter(this.getPlayer());
            if (position >= xBasketMaxRange && position <= xBasketMinRange) {
                caught = true;
            }
        } else if (this.gameGraphics.getPlayer().getPlayerDirection() == Constants.DIRECTION_RIGHT) {
            xBasketMaxRange = this.gameGraphics.getImageXCenter(this.getPlayer()) + 190;
            xBasketMinRange = this.gameGraphics.getImageXCenter(this.getPlayer());
            if (position <= xBasketMaxRange && position >= xBasketMinRange) {
                caught = true;
            }
        }
        if (caught) {
            switch (type) {
                case ELIXIR_FELIX_FELICIS:
                    this.scoreCount += 10;
                    break;
                case ELIXIR_VERITASERUM:
                    this.scoreCount += 20;
                    break;
                case ELIXIR_TOJADOWY:
                    this.scoreCount += 15;
                    break;
                case ELIXIR_AMORTENCJA:
                    this.scoreCount += 50;
                    break;
                case ELIXIR_SLUZU_GIGANTA:
                    this.scoreCount -= 5;
                    break;
                case ELIXIR_SANGRE_DE_DIABLO:
                    this.scoreCount -= 10;
                    break;
                case ELIXIR_ROZPACZY:
                    this.scoreCount -= 25;
                    break;
                case ELIXIR_ZYWEJ_SMIERCI:
                    this.scoreCount -= (this.scoreCount / 2);
                    break;
            }
            this.soundHandler.playSoundEffect(2,Constants.DEFAULT_VOLUME);
            this.gameGraphics.updateScore(scoreCount);
        } else {
            this.soundHandler.playSoundEffect(1,Constants.DEFAULT_VOLUME-10);

        }
    }

    public void stopGame() {
        Log.d("Game stop", "STOPPED");
        this.gameInSession = false;
        cancelTimers();
        if (this.animationStarted) {
            Log.d("Stop game", "Animation clear!");
            this.elixirAnimation.cancel();
        }
    }

    private void cancelTimers() {
        Log.d("cancelTimer", "TIMER CANCELLED");
        this.elixirDelayHandler.removeCallbacks(this.elixirDelayRunnable);
        this.levelHandler.removeCallbacks(this.levelRunnable);
        this.elixirIntervalHandler.removeCallbacks(this.elixirIntervalRunnable);
        this.countdownHandler.removeCallbacks(this.countdownRunnable);
        this.handlerStarted = false;

    }


}
