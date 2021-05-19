package com.example.settlersofcatan.game;

import android.util.Log;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.server_client.GameClient;

public class Knights extends DevelopmentCard{

    public Knights() {
        super(14);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Knights played.");

        Game.getInstance().setCanMoveRobber(true);
        GameClient.getInstance().getGameActivity().findViewById(R.id.moveRobber).setEnabled(true);

    }
}
