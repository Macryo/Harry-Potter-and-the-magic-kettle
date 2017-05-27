package com.example.maciek.magicznykociolek.Elixir;


/**
 * Created by Maciek on 11.01.2017.
 */

public enum Elixirs {

    ELIXIR_FELIX_FELICIS(0,"Felix Felicis"), //+10
    ELIXIR_VERITASERUM(1,"Veritaserum"), //+20
    ELIXIR_TOJADOWY(2,"Eliksir Tojadowy"), //+15
    ELIXIR_AMORTENCJA(3,"Amortencja"), //+50
    ELIXIR_SLUZU_GIGANTA(4,"Sluz Giganta"), //- 15
    ELIXIR_SANGRE_DE_DIABLO(5,"Sangre de Diablo"), //-10
    ELIXIR_ROZPACZY(6,"Eliksir Rozpaczy"), //- 25
    ELIXIR_ZYWEJ_SMIERCI(7,"Eliksir Żywej Smierci"); // - połowa obecna

    private String name;
    private int index;

    private Elixirs(int index, String elixirName){
        this.name = elixirName;
        this.index = index;
    }

    public String getElixirName(){
        return this.name;
    }
    public int getElixirIndex(){
        return this.index;
    }

}
