package com.example.settlersofcatan.game;

import android.util.Log;

import com.example.settlersofcatan.R;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.networking.dto.ArmySizeIncreaseMessage;

public class Knights extends DevelopmentCard{

    public Knights() {
        super(14);
    }

    @Override
    public void playCard() {
        super.playCard();
        Log.i("DEVELOPMENTCARDS: ", "Knights played.");

        Game game = Game.getInstance();
        int playerId = GameClient.getInstance().getId();

        game.setCanMoveRobber(true);
        game.getPlayerById(playerId).incrementPlayedKnights();
        game.doAsyncClientCallback(new ArmySizeIncreaseMessage(playerId));
        game.updateLargestArmy();

        GameClient.getInstance().getGameActivity().findViewById(R.id.moveRobber).setEnabled(true);
    }
}
