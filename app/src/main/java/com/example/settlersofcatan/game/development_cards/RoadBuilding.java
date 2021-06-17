package com.example.settlersofcatan.game.development_cards;

import android.util.Log;

import com.example.settlersofcatan.game.Game;

public class RoadBuilding extends DevelopmentCard{

    public RoadBuilding() {
        super(2);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "RoadBuilding played.");

        Game.getInstance().setHasPlayedCard(true);
        Game.getInstance().setFreeRoads(2);
    }
}
