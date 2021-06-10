package com.example.settlersofcatan;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.ResourceMap;

import java.util.HashMap;

public class PlayerResources {
    private static PlayerResources instance;
    private HashMap<Integer, ResourceMap> playersResourceMaps;

    private PlayerResources() {
        playersResourceMaps = new HashMap<>();
        for (Player p : Game.getInstance().getPlayers()){
            playersResourceMaps.put(p.getId(), p.getResources());
        }
    }

    public static PlayerResources getInstance() {
        if (instance == null){
            instance = new PlayerResources();
        }
        return instance;
    }

    public static void setInstance(PlayerResources instance){
        PlayerResources.instance = instance;
    }

    public ResourceMap getSinglePlayerResources(int id){
        return playersResourceMaps.get(id);
    }
}
