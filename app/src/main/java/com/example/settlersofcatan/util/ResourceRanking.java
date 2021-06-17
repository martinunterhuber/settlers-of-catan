package com.example.settlersofcatan.util;

import com.example.settlersofcatan.Ranking;
import com.example.settlersofcatan.ResourceView;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.server_client.GameClient;

import java.util.ArrayList;

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
