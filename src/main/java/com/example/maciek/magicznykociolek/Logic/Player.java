package com.example.maciek.magicznykociolek.Logic;

import android.widget.ImageView;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.Graphics.GameGraphics;

/**
 * Created by Maciek on 11.01.2017.
 */

public class Player extends ImageView {

    private GameActivity logic;
    private int playerDirection;
    private int previousDirection = Constants.DIRECTION_LEFT;
    private GameGraphics graphics;

    public Player(GameActivity logic) {
        super(logic.getApplicationContext());
        this.logic = logic;
        this.playerDirection = this.previousDirection;
        this.setX((logic.getResources().getDisplayMetrics().widthPixels / 2) - 80);
        this.setY((logic.getResources().getDisplayMetrics().heightPixels / 2) );
    }

    public int getPlayerDirection() {
        return this.playerDirection;
    }

    public int getPlayerPreviousDirection() {
        return this.previousDirection;
    }

    public void setPlayerDirection(int direction) {
        this.playerDirection = direction;
    }

    public void setPlayerPreviousDirection(int direction) {
        this.previousDirection = direction;
    }

}
