package com.example.settlersofcatan.game;

import android.util.Log;

public class Knights extends DevelopmentCard{

    public Knights() {
        super(14);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Knights played.");

        //TODO implement rule
    }
}
