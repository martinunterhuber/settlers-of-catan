package com.example.settlersofcatan;

import android.util.Log;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.ResourceMap;

import java.util.HashMap;

public class PlayerResources {
    private static PlayerResources instance;
    private HashMap<Integer, ResourceMap> playerResources;

    private PlayerResources() {
        playerResources = new HashMap<>();
        for (Player p : Game.getInstance().getPlayers()){
            playerResources.put(p.getId(),new ResourceMap());
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
        Log.i("PLAYER_RESOURCES","PlayerResource set");
    }

    public void setSinglePlayerResources(ResourceMap resources, int playerId){
        playerResources.put(playerId,resources);
        Log.i("PLAYER_RESOURCES","Player " + playerId + " resources updated.");
    }

    public ResourceMap getSinglePlayerResources(int id){
        return playerResources.get(id);
    }
}
