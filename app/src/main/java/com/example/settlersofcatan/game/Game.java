package com.example.settlersofcatan.game;

import android.util.Log;

import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
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

    // transient = "do not serialize this"
    transient private Callback<BaseMessage> clientCallback;

    private ArrayList<Player> players;
    private Board board;

    private int currentPlayerId;
    private int turnCounter;

    private boolean hasRolled;

    // Variables for initial building phase
    private boolean hasBuiltSettlement;
    private boolean hasBuiltRoad;
    private Node lastBuiltNode;

    private Player longestRoadPlayer;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
        turnCounter = 0;
        hasRolled = false;
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

    public void setClientCallback(Callback<BaseMessage> callback){
        this.clientCallback = callback;
    }

    public int rollDice(int playerId) {
        if (isPlayersTurn(playerId) && !hasRolled && !isBuildingPhase()){
            int numberRolled = random.nextInt(6) + 1 + random.nextInt(6) + 1;
            board.distributeResources(numberRolled);
            hasRolled = true;
            return numberRolled;
        }
        return -1;
    }

    public void endTurn(int playerId){
        if (isPlayersTurn(playerId) && canEndTurn()) {
            hasRolled = false;
            hasBuiltRoad = false;
            hasBuiltSettlement = false;
            lastBuiltNode = null;
            turnCounter++;
            setCurrentPlayerId();
            // TODO: send messages for every action
            new Thread(() -> clientCallback.callback(new GameStateMessage(this))).start();
        }
    }

    private boolean canEndTurn(){
        return (!isBuildingPhase() && hasRolled) || (isBuildingPhase() && hasBuiltSettlement && hasBuiltRoad);
    }

    private void setCurrentPlayerId(){
        if (isBuildingPhase() && isSecondRound()){
            if (turnCounter != players.size()){
                currentPlayerId = (currentPlayerId - 1) % players.size();
            }
        } else {
            if (turnCounter != players.size() * 2){
                currentPlayerId = (currentPlayerId + 1) % players.size();
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

        if (node.hasNoAdjacentBuildings() && isPlayersTurn(playerId)){
            if (isBuildingPhase()){
                if (!hasBuiltSettlement){
                    player.placeSettlement(node);
                    lastBuiltNode = node;
                    hasBuiltSettlement = true;
                }
            } else if (hasRolled && player.hasResources(Settlement.costs) && player.canPlaceSettlementOn(node)){
                player.takeResources(Settlement.costs);
                player.placeSettlement(node);
                updateLongestRoadPlayer();
            }
        }
    }

    public void buildCity(Node node, int playerId){
        Player player = getPlayerById(playerId);
        if (node.hasPlayersSettlement(playerId)
                && hasRolled
                && isPlayersTurn(playerId)
                && player.canPlaceCityOn(node)) {
            player.placeCity(node);
        }
    }

    public void buildRoad(Edge edge, int playerId){
        Player player = getPlayerById(playerId);

        if (isPlayersTurn(playerId) && player.canPlaceRoadOn(edge)) {
            if (isBuildingPhase()){
                if (!hasBuiltRoad && lastBuiltNode != null && lastBuiltNode.getOutgoingEdges().contains(edge)){
                    player.placeRoad(edge);
                    hasBuiltRoad = true;
                }
            } else if (hasRolled && player.hasResources(Road.costs)){
                player.takeResources(Road.costs);
                player.placeRoad(edge);
                updateLongestRoadPlayer();
            }
        }
    }

    public void updateLongestRoadPlayer(){
        Player currentPlayer = getPlayerById(currentPlayerId);
        int currentPlayerLongestRoad = currentPlayer.longestRoad();
        if (currentPlayerLongestRoad >= 5){
            int previousLongestRoad = 0;
            if (longestRoadPlayer != null){
                previousLongestRoad = longestRoadPlayer.longestRoad();
            }
            if (previousLongestRoad < currentPlayerLongestRoad && longestRoadPlayer != currentPlayer){
                if (longestRoadPlayer != null) {
                    longestRoadPlayer.addVictoryPoints(-2);
                }
                longestRoadPlayer = currentPlayer;
                currentPlayer.addVictoryPoints(2);
            }
        } else if (currentPlayer == longestRoadPlayer){
            // currentPlayer doesn't have a road >= 5 anymore (because of enemy settlement)
            for (Player otherPlayer : players){
                if (otherPlayer.getId() != currentPlayerId){
                    updateLongestRoadPlayer();
                }
            }
        }
    }

    public boolean isBuildingPhase(){
        return (turnCounter / players.size()) < 2;
    }

    public boolean isSecondRound(){
        return (turnCounter / players.size()) == 1;
    }

    public boolean isPlayersTurn(int playerId){
        return playerId == currentPlayerId;
    }

    public ArrayList<Player> getPlayers() {
        return players;
    }

    public Board getBoard() {
        return board;
    }

    public int getCurrentPlayerId() {
        return currentPlayerId;
    }
}
