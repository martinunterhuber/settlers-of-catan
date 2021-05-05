package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class to represent a game as a whole, includes the map which the game is played on,
 * and the players who will play on it, todo as well as the rules which them game is played under.
 */
public class Game {
    private static Game instance;

    private ArrayList<Player> players;
    private Board board;

    private int currentPlayerId;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
    }

    public static Game getInstance() {
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }

    public void init(ArrayList<String> names){
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            players.add(new Player(i, name));
        }
    }

    private void rollDice() {
        Random rand = new Random();
        int numberRolled = rand.nextInt(6) + 1 + rand.nextInt(6) + 1;
        board.distributeResources(numberRolled);
    }
}