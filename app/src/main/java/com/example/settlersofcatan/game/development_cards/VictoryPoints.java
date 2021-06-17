package com.example.settlersofcatan.game.development_cards;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.GameClient;

public class VictoryPoints extends DevelopmentCard{

    public VictoryPoints() {
        super(5);
    }

    @Override
    public void playCard() {
        Game.getInstance().getPlayerById(GameClient.getInstance().getId())
                            .increaseHiddenVictoryPoints();
    }
}
