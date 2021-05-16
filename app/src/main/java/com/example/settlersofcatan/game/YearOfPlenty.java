package com.example.settlersofcatan.game;

import android.util.Log;

public class YearOfPlenty extends DevelopmentCard{

    public YearOfPlenty() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Year of Plenty played.");

        //TODO implement rule
    }
}
