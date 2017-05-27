package com.example.maciek.magicznykociolek.Graphics;

import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.Elixir.Elixirs;
import com.example.maciek.magicznykociolek.Elixir.ElixirImageFactory;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Logic.Player;
import com.example.maciek.magicznykociolek.R;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Maciek on 11.01.2017.
 */

public class GameGraphics {

    private GameActivity logic;
    private Map<Elixirs, Bitmap> elixirs;
    private GraphicScaling graphicsScaling;
    private Bitmap bench;
    private Bitmap snape;
    private ImageButton[] buttons;
    private TextView timer;
    private Animation fadeout;
    private Bitmap harry_l;
    private Bitmap harry_r;
    private TextView score;
    private ImageView snapeIV;
    private ImageView benchIV;
    private TextView countdownView;
    private TextView timeInfoFrame;
    private RelativeLayout rl;
    private Player player;
    private Bitmap snapePositive;
    private Bitmap snapeNegative;
    private ImageView snapeDialog;

    public GameGraphics(GameActivity logic) {
        this.logic = logic;
        this.elixirs = new HashMap<>();
        this.buttons = new ImageButton[2];
        this.graphicsScaling = new GraphicScaling(logic);
    }


    public void initGameGraphics() {
        this.initElixirGraphics();
        this.initHarryGraphics();
        this.initAnimations();
        this.initElements();
        this.bringElementsToFront();
    }

    public void bringElementsToFront() {

    }

    public TextView getTimer() {
        return timer;
    }

    private void initElixirGraphics() {
        this.elixirs.put(Elixirs.ELIXIR_FELIX_FELICIS, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_FELIX_FELICIS, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_VERITASERUM, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_VERITASERUM, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_TOJADOWY, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_TOJADOWY, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_AMORTENCJA, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_AMORTENCJA, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_SLUZU_GIGANTA, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_SLUZU_GIGANTA, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_SANGRE_DE_DIABLO, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_SANGRE_DE_DIABLO, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_ROZPACZY, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_ROZPACZY, this.logic));
        this.elixirs.put(Elixirs.ELIXIR_ZYWEJ_SMIERCI, ElixirImageFactory.getElixirImage(Elixirs.ELIXIR_ZYWEJ_SMIERCI, this.logic));
        System.out.println(this.elixirs);
    }

    private void initElements() {
        this.buttons[0] = (ImageButton) this.logic.findViewById(R.id.leftButton);
        this.buttons[1] = (ImageButton) this.logic.findViewById(R.id.rightButton);
        this.buttons[0].setImageBitmap(this.graphicsScaling.scaleGraphicToDisplay(R.drawable.left,180,180));
        this.buttons[0].setBackgroundColor(Color.TRANSPARENT);
        this.buttons[1].setImageBitmap( this.graphicsScaling.scaleGraphicToDisplay(R.drawable.right,180,180));
        this.buttons[1].setBackgroundColor(Color.TRANSPARENT);
        this.timer = (TextView) this.logic.findViewById(R.id.timeCounter);
        this.timeInfoFrame = (TextView) this.logic.findViewById(R.id.time);
        this.timeInfoFrame.setVisibility(View.VISIBLE);
        this.timeInfoFrame.bringToFront();
        this.timer.setText("100");
        this.score = (TextView) this.logic.findViewById(R.id.score);
        this.countdownView = (TextView) this.logic.findViewById(R.id.countdownView);
        this.getCountdownView().setTextColor(Color.BLACK);
        this.getCountdownView().setText("Ready?");
        this.snapeIV = (ImageView) this.logic.findViewById(R.id.snape);
        this.loadSnapeGraphics();
        this.loadSnapeDialogs();
        this.benchIV = (ImageView) this.logic.findViewById(R.id.imageView2);
        this.loadBenchImage();
        this.rl = (RelativeLayout) this.logic.findViewById(R.id.activity_game_logic);
        this.player = new Player(this.logic);
        this.setPlayerImage();
        this.rl.addView(this.player);
        this.loadBackgroundImage(rl);
    }

    private void setPlayerImage() {
        this.player.setImageBitmap(getHarry_l());
    }

    public void playerMoveLeft() {
        if (this.player.getX() > 200) {
            TranslateAnimation animation = new TranslateAnimation(0, -40, 0, 0);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.player.setX(this.player.getX() - 40);
            this.player.startAnimation(animation);
        }
        this.player.setPlayerDirection(Constants.DIRECTION_LEFT);
        if (this.player.getPlayerPreviousDirection() != this.player.getPlayerDirection()) {
            updatePlayerImage(this.player.getPlayerDirection());
        }
    }

    public void playerMoveRight() {
        if (this.player.getX() < 700) {
            TranslateAnimation animation = new TranslateAnimation(0, 40, 0, 0);
            animation.setDuration(500);
            animation.setFillAfter(true);
            this.player.setX(this.player.getX() + 40);
            this.player.startAnimation(animation);
        }
        this.player.setPlayerDirection(Constants.DIRECTION_RIGHT);
        if (this.player.getPlayerPreviousDirection() != this.player.getPlayerDirection()) {
            updatePlayerImage(this.player.getPlayerDirection());
        }
    }

    private void updatePlayerImage(int direction) {
        if (direction == Constants.DIRECTION_LEFT) {
            this.player.setImageBitmap(getHarry_l());
            this.player.setPlayerPreviousDirection(Constants.DIRECTION_LEFT);
        } else {
            this.player.setImageBitmap(getHarry_r());
            this.player.setPlayerPreviousDirection(Constants.DIRECTION_RIGHT);
        }
    }

    public Player getPlayer() {
        return this.player;
    }

    public RelativeLayout getRl() {
        return rl;
    }

    public void updateScore(int valueUpdated) {
        this.score.setText(String.valueOf(valueUpdated));
    }

    public void refreshScreen() {
        ViewGroup vg = (ViewGroup) this.logic.findViewById(R.id.activity_game_logic);
        vg.invalidate();
        this.benchIV.bringToFront();
        if (this.snapeDialog.getVisibility() == View.VISIBLE) {
            this.snapeDialog.bringToFront();
        }
    }

    private void initHarryGraphics() {
        this.harry_l = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.character_l,330,350);
        this.harry_r = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.character_r,330,350);
    }

    public Bitmap getHarry_l() {
        return harry_l;
    }

    public Bitmap getHarry_r() {
        return harry_r;
    }


    public TextView getCountdownView() {
        return this.countdownView;
    }

    private void loadBackgroundImage(RelativeLayout rl) {
        this.graphicsScaling.scaleBackground(rl);
    }

    private void loadBenchImage() {
        this.bench = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.bench, this.getDeviceWidth() - 260, 250);
        this.benchIV.setImageBitmap(this.bench);
    }

    public Bitmap getElixirGraphics(Elixirs elixir) {
        return this.elixirs.get(elixir);
    }


    public float getImageXCenter(ImageView view) {
        return view.getX() + view.getWidth() / 2;
    }

    public float getImageYCenter(ImageView view) {
        return view.getY() + view.getHeight() / 2;
    }

    public ImageButton getButton(int index) {
        return this.buttons[index];
    }

    public Animation getFadeout() {
        return this.fadeout;
    }

    private void loadSnapeDialogs() {
        this.snapeNegative = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.snape_negative, 400, 400);
        this.snapePositive = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.snape_positive, 400, 400);
        this.snapeDialog = (ImageView) this.logic.findViewById(R.id.snape_dialog);
        this.snapeDialog.setVisibility(View.INVISIBLE);
        this.snapeDialog.setRotation(-10);
    }

    private void loadSnapeGraphics() {
        this.snape = this.graphicsScaling.scaleGraphicToDisplay(R.drawable.snape_l, 350, 500);
        this.snapeIV.setImageBitmap(this.snape);
    }

    private void initAnimations() {
        this.fadeout = AnimationUtils.loadAnimation(this.logic, R.anim.fade_out_anim);
    }

    public ImageView getSnapeDialog(boolean positive) {
        if (!positive) {
            this.snapeDialog.setImageBitmap(this.snapeNegative);
            return this.snapeDialog;
        } else {
            this.snapeDialog.setImageBitmap(this.snapePositive);
            return this.snapeDialog;
        }
    }

    public int getDeviceWidth() {
        return logic.getResources().getDisplayMetrics().widthPixels;
    }

    public int getDeviceHeight() {
        return logic.getResources().getDisplayMetrics().heightPixels;
    }


}
