package com.example.settlersofcatan.ui.ranking;

import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;

import java.util.ArrayList;

public class Ranking {
    private static Ranking instance;
    private ArrayList<Player> placement;

    private Ranking (){
        this.placement=new ArrayList<>();
        gameOutcome();
    }

    public static Ranking getInstance() {
        if (instance == null){
            instance = new Ranking();
        }
        return instance;
    }

    // Sets placement of players
    public void gameOutcome(){
        for (Player p : Game.getInstance().getPlayers()){

            if (placement.size() == 0){
                placement.add(p);
            }else {
                int index = getPlacement(p);

                if (index != placement.size()) {
                    placement.add(index, p);
                }else {
                    placement.add(p);
                }
            }

        }
    }

//---------- Getter & Setter -----------------------------------------------------------------

    private Integer getPlacement (Player player){
        for (int i = 0; i < placement.size(); i++){
            if (player.getVictoryPoints() > placement.get(i).getVictoryPoints()){
                return i;
            }
        }
        return placement.size();
    }

    public Player getPlayer (int index){
        return placement.get(index);
    }
}
