package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.Random;

/**
 * Class to represent a game as a whole, includes the map which the game is played on,
 * and the players who will play on it, todo as well as the rules which them game is played under.
 */
public class Game {
    private static Game instance;
    public static final Random random = new Random();

    private ArrayList<Player> players;
    private Board board;

    private int currentPlayerId;

    private boolean alreadyRolled;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
        alreadyRolled = false;
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

    private void rollDice(Player player) {
        if (player.getId() == currentPlayerId && !alreadyRolled){
            int numberRolled = random.nextInt(6) + 1 + random.nextInt(6) + 1;
            board.distributeResources(numberRolled);
            alreadyRolled = true;
        }
    }

    private void endTurn(Player player){
        if (player.getId() == currentPlayerId) {
            currentPlayerId = (players.indexOf(player) + 1) % players.size();
            alreadyRolled = false;
            // TODO: GameClient.getInstance() send this to server
        }
    }

    private void buildSettlement(Node node, Player player){
        if (node.getBuilding() == null && player.getId() == currentPlayerId && node.hasNoAdjacentBuildings()) {
            node.setBuilding(new Settlement(player, node));
        }
    }

    private void buildCity(Node node, Player player){
        if (node.getBuilding() != null
                && node.getBuilding() instanceof Settlement
                && ((Settlement) node.getBuilding()).player.getId() == currentPlayerId
                && player.getId() == currentPlayerId) {
            node.setBuilding(new City(player, node));
        }
    }

    private void buildRoad(Edge edge, Player player){
        if (edge.getRoad() == null
                && player.getId() == currentPlayerId
                && edge.connectsPlayer(player)) {
            edge.setRoad(new Road(player));
        }
    }
}