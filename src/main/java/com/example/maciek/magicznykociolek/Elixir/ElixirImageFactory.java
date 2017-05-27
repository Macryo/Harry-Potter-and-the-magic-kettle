package com.example.maciek.magicznykociolek.Elixir;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.example.maciek.magicznykociolek.Activity.GameActivity;
import com.example.maciek.magicznykociolek.Graphics.GraphicScaling;
import com.example.maciek.magicznykociolek.R;

/**
 * Created by Maciek on 11.01.2017.
 */

public class ElixirImageFactory {

    public static Bitmap getElixirImage(Elixirs elixirs, GameActivity logic){
        GraphicScaling scaling = new GraphicScaling(logic);
        int drawable = 0;
        switch (elixirs){
            case ELIXIR_FELIX_FELICIS:
                drawable = R.drawable.elixir1;
                break;
            case ELIXIR_VERITASERUM:
                drawable = R.drawable.elixir2;
                break;
            case ELIXIR_TOJADOWY:
                drawable = R.drawable.elixir3;
                break;
            case ELIXIR_AMORTENCJA:
                drawable = R.drawable.elixir4;
                break;
            case ELIXIR_SLUZU_GIGANTA:
                drawable = R.drawable.elixir5;
                break;
            case ELIXIR_SANGRE_DE_DIABLO:
                drawable = R.drawable.elixir6;
                break;
            case ELIXIR_ROZPACZY:
                drawable = R.drawable.elixir7;
                break;
            case ELIXIR_ZYWEJ_SMIERCI:
                drawable = R.drawable.elixir8;
                break;
        }
        Bitmap bitmap = scaling.scaleGraphicToDisplay(drawable,100,100);
        return bitmap;
    }
}
