package com.example.settlersofcatan.game;

import java.util.ArrayList;

/**
 * Class to represent a game as a whole, includes the map which the game is played on,
 * and the players who will play on it, todo as well as the rules which them game is played under.
 */
public class Game {

    private Map map;
    private ArrayList<Player> players;

    private void rollDice() {
        int numberRolled = (int) ((Math.random() * 6) + 1) + (int) ((Math.random() * 6) + 1);
        map.diceRolled(numberRolled);
    }


}
