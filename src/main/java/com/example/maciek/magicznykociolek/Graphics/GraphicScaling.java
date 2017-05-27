package com.example.maciek.magicznykociolek.Graphics;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;
import android.widget.RelativeLayout;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.GameConstants.Constants;
import com.example.maciek.magicznykociolek.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import static com.example.maciek.magicznykociolek.GameConstants.Constants.IMAGE_MAX_SIZE;

/**
 * Created by Maciek on 11.01.2017.
 */

public class GraphicScaling {

    private AppCompatActivity logic;

    public GraphicScaling(AppCompatActivity logic) {
        this.logic = logic;
    }

    private int getDeviceWidth() {
        return logic.getResources().getDisplayMetrics().widthPixels;
    }

    private int getDeviceHeight() {
        return logic.getResources().getDisplayMetrics().heightPixels;
    }


    public void scaleBackground(RelativeLayout rl){
        Bitmap bitmap = BitmapFactory.decodeResource(this.logic.getResources(),R.drawable.background);
        rl.setBackgroundDrawable(new BitmapDrawable(bitmap));
    }

    public Bitmap scaleGraphicToDisplay(int RDrawable, int desiredX, int desiredY) {
        Bitmap map = BitmapFactory.decodeResource(logic.getResources(), RDrawable);
        Bitmap newBitmap = Bitmap.createScaledBitmap(map, desiredX, desiredY, false);
        return newBitmap;
    }
}
