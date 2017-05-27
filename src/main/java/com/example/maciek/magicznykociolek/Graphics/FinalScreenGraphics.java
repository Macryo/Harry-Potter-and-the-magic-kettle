package com.example.maciek.magicznykociolek.Graphics;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageButton;

import com.example.maciek.magicznykociolek.R;

/**
 * Created by Maciek on 16.01.2017.
 */

public class FinalScreenGraphics {

    private AppCompatActivity activity;
    private GraphicScaling scaling;
    private Bitmap[] imageButtonsBitmaps;

    public FinalScreenGraphics(AppCompatActivity activity) {
        this.activity = activity;
        this.scaling = new GraphicScaling(activity);
        this.imageButtonsBitmaps = new Bitmap[4];
    }

    public void initGraphics(){
        this.imageButtonsBitmaps[0] = this.scaling.scaleGraphicToDisplay(R.drawable.menu,170,60);
        this.imageButtonsBitmaps[1] = this.scaling.scaleGraphicToDisplay(R.drawable.menu_clicked,170,60);
        this.imageButtonsBitmaps[2] = this.scaling.scaleGraphicToDisplay(R.drawable.repeat,170,60);
        this.imageButtonsBitmaps[3] = this.scaling.scaleGraphicToDisplay(R.drawable.repeat_clicked,170,60);
    }
    public Bitmap getButtonGraphics(int id){
        return this.imageButtonsBitmaps[id];
    }




}
