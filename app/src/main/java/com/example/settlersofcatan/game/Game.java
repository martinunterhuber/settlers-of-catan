package com.example.settlersofcatan.game;

import com.example.settlersofcatan.Ranking;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.GameServer;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;

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
    private int turnCounter;

    private boolean alreadyRolled;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
        turnCounter = 0;
        alreadyRolled = false;
    }

    public static Game getInstance() {
        if (instance == null){
            instance = new Game();
            instance.board.init();
        }
        return instance;
    }

    public static void setInstance(Game instance) {
        Game.instance = instance;
    }

    public void init(ArrayList<String> names){
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            players.add(new Player(i, name));
        }
    }

    public int rollDice(int playerId) {
        if (playerId == currentPlayerId && !alreadyRolled){
            int numberRolled = random.nextInt(6) + 1 + random.nextInt(6) + 1;
            board.distributeResources(numberRolled);
            alreadyRolled = true;
            return numberRolled;
        }
        return -1;
    }

    public void endTurn(int playerId){
        if (playerId == currentPlayerId) {
            if(getPlayerById(playerId).getVictoryPoints() == 10){
                Ranking ranking = Ranking.getInstance();
                new Thread(() -> GameServer.getInstance().broadcastMessage(new ClientWinMessage(ranking))).start();
            }else {
                currentPlayerId = (playerId + 1) % players.size();
                alreadyRolled = false;
                turnCounter++;
                // TODO: send messages for every action
                new Thread(() -> GameClient.getInstance().sendMessage(new GameStateMessage(this))).start();
            }
        }
    }

    public Player getPlayerById(int playerId){
        for (Player player : players){
            if (player.getId() == playerId){
                return player;
            }
        }
        throw new IllegalArgumentException("Player does not exits");
    }

    public void buildSettlement(Node node, int playerId){
        Player player = getPlayerById(playerId);
        if (node.getBuilding() == null
                && playerId == currentPlayerId
                && node.hasNoAdjacentBuildings()
                && player.canPlayerPlaceSettlement()) {
            player.placeSettlement(node);
        }
    }

    public void buildCity(Node node, int playerId){
        Player player = getPlayerById(playerId);
        if (node.getBuilding() != null
                && node.getBuilding() instanceof Settlement
                && ((Settlement) node.getBuilding()).player.getId() == currentPlayerId
                && playerId == currentPlayerId
                && player.canPlayerPlaceCity()) {
            player.placeCity(node);
        }
    }

    public void buildRoad(Edge edge, int playerId){
        Player player = getPlayerById(playerId);
        if (edge.getRoad() == null
                && playerId == currentPlayerId
                && player.canPlayerPlaceRoad()) {
            player.placeRoad(edge);
        }
    }

    public boolean isBuildingPhase(){
        return (turnCounter / players.size()) < 2;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }
}
