package com.example.settlersofcatan.game;

import android.util.Log;

/**
 * Class to represent a Development card, unimplemented
 */
public class DevelopmentCard {

    private int count;

    public DevelopmentCard() {
    }

    public DevelopmentCard(int count) {
        this.count=count;
    }

    // Player uses resources to get a development card
    public void getCard(){
        count--;
    }

    // Player plays a development card
    public void playCard(){
        Log.i("DEVELOPMENT","Development card was played.");
    }

    public int getCount() {
        return count;
    }
}
