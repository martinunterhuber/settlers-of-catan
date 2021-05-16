package com.example.settlersofcatan.game;

import android.util.Log;

public class Monopoly extends DevelopmentCard{

    public Monopoly() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Monopoly played.");

        //TODO implement rule
    }
}
