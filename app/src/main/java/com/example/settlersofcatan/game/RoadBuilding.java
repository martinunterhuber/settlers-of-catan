package com.example.settlersofcatan.game;

import android.util.Log;

public class RoadBuilding extends DevelopmentCard{

    public RoadBuilding() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "RoadBuilding played.");

        //TODO implement rule
    }
}