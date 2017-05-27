package com.example.maciek.magicznykociolek.Elixir;

import android.widget.ImageView;

import com.example.maciek.magicznykociolek.Activity.GameActivity;

/**
 * Created by Maciek on 12.01.2017.
 */

public class Elixir extends ImageView{

    private Elixirs elixirType;

    public Elixir(GameActivity logic, Elixirs type) {
        super(logic.getApplicationContext());
        this.elixirType = type;
        this.setX((logic.getResources().getDisplayMetrics().widthPixels / 2));
        this.setY((logic.getResources().getDisplayMetrics().heightPixels) / 2);
    }

    public Elixirs getElixirType(){
        return this.elixirType;
    }

}
