package com.example.maciek.magicznykociolek.Logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * Created by Maciek on 16.01.2017.
 */

public class TopScores {

    private int[] scores = new int[6];

    public TopScores() {
        scores[0] = (50);
        scores[1] =(100);
        scores[2] =(200);
        scores[3] = (250);
        scores[4] =(400);
    }

    public TopScores(int[] scores) {
        this.scores = scores;
    }

    public int[] getHighScores(){
        int[] high = new int[5];

        high[0] = this.scores[0];
        high[1] = this.scores[1];
        high[2] = this.scores[2];
        high[3] = this.scores[3];
        high[4] = this.scores[4];

        return this.scores;
    }

    public void checkIfBetter(int score){
        this.scores[5] = score;
        Arrays.sort(this.scores);

    }

}
