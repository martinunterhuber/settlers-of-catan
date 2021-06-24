package com.example.settlersofcatan.ui.color;

import com.example.settlersofcatan.game.PlayerColor;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;

import java.util.HashMap;
import java.util.Map;

public class PlayerColors {
    private static PlayerColors instance;
    private HashMap<Integer, PlayerColor> playersColorMap;

    private PlayerColors() {
        playersColorMap = new HashMap<>();
        for (Player p : Game.getInstance().getPlayers()){
            playersColorMap.put(p.getId(), p.getColor());
        }
    }

    public static PlayerColors getInstance() {
        if (instance == null){
            instance = new PlayerColors();
        }
        return instance;
    }

    public static void setInstance(PlayerColors instance){
        PlayerColors.instance = instance;
    }

    public PlayerColor getSinglePlayerColor(int id){
        return playersColorMap.get(id);
    }

    public void setSinglePlayerColor(int id, PlayerColor playerColor){
        if (colorNotAvailable(playerColor)) {
            playersColorMap.put(id, playerColor);
        }
    }

    public boolean checkIfAllPlayersChose(){
        boolean noColor = false;
        for (int i = 0; i < playersColorMap.size(); i++){
            if (playersColorMap.get(i) == null){
                noColor=true;
            }
        }

        return !noColor;
    }

    private boolean colorNotAvailable(PlayerColor playerColor){
        for (int i = 0; i < playersColorMap.size(); i++){

            if (playersColorMap.get(i) != null && playersColorMap.get(i).equals(playerColor))
                return false;

        }
        return true;
    }

    public Map<Integer, PlayerColor> getPlayersColorMap() {
        return playersColorMap;
    }
}
