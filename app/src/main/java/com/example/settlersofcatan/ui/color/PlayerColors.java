package com.example.settlersofcatan.ui.color;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;

import java.util.HashMap;

public class PlayerColors {
    private static PlayerColors instance;
    private HashMap<Integer, String> playersColorMap;

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

    public String getSinglePlayerColor(int id){
        return playersColorMap.get(id);
    }

    public void setSinglePlayerColor(int id, String color){
        if (colorNotAvailable(color)) {
            playersColorMap.put(id, color);
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

    private boolean colorNotAvailable(String color){
        for (int i = 0; i < playersColorMap.size(); i++){

            if (playersColorMap.get(i) != null && playersColorMap.get(i).equals(color))
                return false;

        }
        return true;
    }

    public HashMap<Integer, String> getPlayersColorMap() {
        return playersColorMap;
    }
}
