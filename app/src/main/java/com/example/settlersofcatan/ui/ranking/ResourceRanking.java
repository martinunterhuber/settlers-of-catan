package com.example.settlersofcatan.ui.ranking;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.server_client.GameClient;

public class ResourceRanking {

    private String firstwood;
    private String firstclay;
    private String firstsheep;
    private String firstore;
    private String firstwheat;

    public String getFirstclay() {
        Player player = Game.getInstance().getPlayerById(GameClient.getInstance().getId());
        
        return firstclay;
    }
}
