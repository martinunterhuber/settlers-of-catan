package com.example.settlersofcatan.game;

import com.example.settlersofcatan.PlayerResources;
import com.example.settlersofcatan.R;
import com.example.settlersofcatan.Ranking;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.PlayerResourcesMessage;

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
    private boolean hasPlayedCard;
    private int freeRoads;

    // Variables for initial building phase
    private boolean hasBuiltSettlement;
    private boolean hasBuiltRoad;
    private Node lastBuiltNode;

    private Player longestRoadPlayer;

    private boolean canMoveRobber;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
        turnCounter = 0;
        hasRolled = false;
        hasPlayedCard = false;
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
            new Thread(() -> GameClient.getInstance()
                                    .sendMessage(new ClientDiceMessage(GameClient.getInstance().getUsername(),numberRolled)))
                                    .start();

            if (numberRolled == 7){
                robPlayers();
                canMoveRobber = true;
            } else {
                board.distributeResources(numberRolled);
                new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance()))).start();
            }
            hasRolled = true;
            return numberRolled;
        }
        return -1;
    }

    public void moveRobber(Tile tile, Resource resource, int playerToId, int playerFromId){
        if (tile != null
                && isPlayersTurn(playerToId)
                && canMoveRobber
                && !tile.hasRobber()){
            board.moveRobberTo(tile);
            if (playerFromId != -1){
                Player playerFrom = getPlayerById(playerFromId);
                Player playerTo = getPlayerById(playerToId);

                if (playerFrom.getResourceCount(resource) > 0){
                    playerFrom.takeResource(resource, 1);
                    playerTo.giveSingleResource(resource);

                    new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));
                }
            }
            canMoveRobber = false;
        }
    }

    public boolean canMoveRobber() {
        return canMoveRobber;
    }

    private void robPlayers(){
        for (Player player : players){
            for (Resource resource : Resource.values()){
                int resourceCount = player.getResourceCount(resource);
                if (resourceCount >= 7){
                    player.takeResource(resource, resourceCount / 2);
                    new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));
                }
            }
        }
    }

    public void endTurn(int playerId){
        if (isPlayersTurn(playerId) && canEndTurn() && !canMoveRobber) {
            if(getPlayerById(playerId).getVictoryPoints() + getPlayerById(playerId).getHiddenVictoryPoints() >= 10){
                Ranking ranking = Ranking.getInstance();
                new Thread(() -> clientCallback.callback(new ClientWinMessage(ranking))).start();
                return;
            }
            hasRolled = false;
            hasBuiltRoad = false;
            hasBuiltSettlement = false;
            lastBuiltNode = null;
            turnCounter++;
            setCurrentPlayerId();
            // TODO: send messages for every action
            new Thread(() -> { clientCallback.callback(new GameStateMessage(this));
                GameClient.getInstance().sendMessage(new DevelopmentCardMessage(DevelopmentCardDeck.getInstance()));
                clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance()));
            }).start();
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

    public Player getPlayerByName(String playerName){
        for (Player player : players){
            if (player.getName().equals(playerName)){
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

                new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));
            }
        }
    }

    public void buildCity(Node node, int playerId){
        Player player = getPlayerById(playerId);
        if (node.hasPlayersSettlement(playerId)
                && hasRolled
                && player.hasResources(City.costs)
                && !isBuildingPhase()
                && isPlayersTurn(playerId)
                && player.canPlaceCityOn(node)) {
            player.placeCity(node);
            player.takeResources(City.costs);

            new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));
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
            } else if (hasRolled && player.hasResources(Road.costs) && !hasPlayedCard){
                player.takeResources(Road.costs);
                player.placeRoad(edge);
                updateLongestRoadPlayer();

                new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));
            } else if (hasRolled && hasPlayedCard){
                player.placeRoad(edge);
                freeRoads--;
                updateLongestRoadPlayer();

                if (freeRoads == 0){
                    hasPlayedCard = false;
                }
            }
        }
    }

    public void updateLongestRoadPlayer(){
        int previousLongestRoad = 4;
        if (longestRoadPlayer != null){
            previousLongestRoad = longestRoadPlayer.longestRoad();
            if (previousLongestRoad < 5){
                longestRoadPlayer.addVictoryPoints(-2);
                longestRoadPlayer = null;
                previousLongestRoad = 4;
            }
        }
        for (Player player : players){
            int longestRoad = player.longestRoad();
            if (longestRoad > previousLongestRoad){
                if (longestRoadPlayer != null){
                    longestRoadPlayer.addVictoryPoints(-2);
                }
                longestRoadPlayer = player;
                player.addVictoryPoints(2);
                previousLongestRoad = longestRoad;
            }
        }
    }

    public int drawDevelopmentCard(int playerId){
        if (playerId == currentPlayerId
                && hasRolled
                && DevelopmentCardDeck.getInstance().getNumberOfCards() > 0
                && getPlayerById(playerId).getResourceCount(Resource.ORE) > 0
                && getPlayerById(playerId).getResourceCount(Resource.SHEEP) > 0
                && getPlayerById(playerId).getResourceCount(Resource.WHEAT )> 0){
            DevelopmentCard card = DevelopmentCardDeck.getInstance().drawDevelopmentCard();
            Player player=getPlayerById(playerId);

            player.takeResource(Resource.ORE,1);
            player.takeResource(Resource.SHEEP,1);
            player.takeResource(Resource.WHEAT,1);
            GameClient.getInstance().getGameActivity().findViewById(R.id.resourceView).invalidate();
            new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance())));

            if (card instanceof Knights){
                player.increaseDevelopmentCard(0);
                return 0;

            }else if (card instanceof VictoryPoints){
                player.increaseDevelopmentCard(1);
                card.playCard();
                return 1;

            }else if (card instanceof Monopoly){
                player.increaseDevelopmentCard(2);
                return 2;

            }else if (card instanceof RoadBuilding){
                player.increaseDevelopmentCard(3);
                return 3;

            }else if (card instanceof YearOfPlenty){
                player.increaseDevelopmentCard(4);
                return 4;
            }
        }
        return -1;
    }

    public void playDevelopmentCard(int playerId, int tag){
        if (playerId == currentPlayerId && hasRolled ) {
            DevelopmentCardDeck.getInstance().getDevelopmentCard(tag - 1).playCard();
            getPlayerById(playerId).decreaseDevelopmentCard(tag-1);
        }
    }

    // rob 1 resource from another player
    public void robResource(int playerFromId, int playerToId, Resource resource){

        Player playerFrom = getPlayerById(playerFromId);
        Player playerTo = getPlayerById(playerToId);

        if (playerFrom.getResourceCount(resource) > 0){
            playerFrom.takeResource(resource, 1);
            playerTo.giveSingleResource(resource);

            new Thread(() -> clientCallback.callback(new PlayerResourcesMessage(PlayerResources.getInstance()))).start();
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

    public void setCanMoveRobber(boolean canMoveRobber) {
        this.canMoveRobber = canMoveRobber;
    }

    public void setHasPlayedCard(boolean hasPlayedCard) {
        this.hasPlayedCard = hasPlayedCard;
    }

    public void setFreeRoads(int freeRoads) {
        this.freeRoads = freeRoads;
    }
}
