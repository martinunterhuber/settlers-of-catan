package com.example.settlersofcatan.game;

import android.util.Log;

import com.example.settlersofcatan.PlayerResources;
import com.example.settlersofcatan.R;
import com.example.settlersofcatan.Ranking;
import com.example.settlersofcatan.server_client.GameClient;
import com.example.settlersofcatan.server_client.networking.AsyncCallback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.CityBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.EndTurnMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.MovedRobberMessage;
import com.example.settlersofcatan.server_client.networking.dto.PlayerResourcesMessage;
import com.example.settlersofcatan.server_client.networking.dto.RoadBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.SettlementBuildingMessage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

/**
 * Class to represent a game as a whole, includes the map which the game is played on,
 * and the players who will play on it, todo as well as the rules which them game is played under.
 */
public class Game {
    private static Game instance;
    public static final Random random = new Random();

    // transient = "do not serialize this"
    transient private AsyncCallback<BaseMessage> clientCallback;

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
    private Player largestArmyPlayer;

    private boolean canMoveRobber;

    private HashMap<Integer, ArrayList<Integer>> cheated;

    private Game(){
        players = new ArrayList<>();
        board = new Board();
        currentPlayerId = 0;
        turnCounter = 0;
        hasRolled = false;
        hasPlayedCard = false;
        cheated = new HashMap<>();
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

    public void setClientCallback(AsyncCallback<BaseMessage> callback){
        this.clientCallback = callback;
    }

    public int rollDice(int playerId) {
        if (isPlayersTurn(playerId) && !hasRolled && !isBuildingPhase()){
            int numberRolled = random.nextInt(6) + 1 + random.nextInt(6) + 1;
            clientCallback.asyncCallback(new ClientDiceMessage(GameClient.getInstance().getUsername(),numberRolled));

            if (numberRolled == 7){
                robPlayers();
                canMoveRobber = true;
            } else {
                board.distributeResources(numberRolled);
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
                }
            }
            canMoveRobber = false;

            clientCallback.asyncCallback(new MovedRobberMessage(tile.getCoordinates()));
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
                }
            }
        }
    }

    public void endTurn(int playerId){
        if (isPlayersTurn(playerId) && canEndTurn() && !canMoveRobber) {
            if(getPlayerById(playerId).getVictoryPoints() + getPlayerById(playerId).getHiddenVictoryPoints() >= 10){
                Ranking ranking = Ranking.getInstance();
                clientCallback.asyncCallback(new ClientWinMessage(ranking));
                return;
            }

            turnCounter++;
            setCurrentPlayerId();

            clientCallback.asyncCallback(new EndTurnMessage(turnCounter, currentPlayerId));
            clientCallback.asyncCallback(new DevelopmentCardMessage(DevelopmentCardDeck.getInstance()));
            clientCallback.asyncCallback(new PlayerResourcesMessage(PlayerResources.getInstance()));
        }
    }

    public void initializeNextTurn(int nextPlayerId, int turnCounter){
        updateLongestRoadPlayer();
        updateLargestArmy();
        hasRolled = false;
        hasBuiltRoad = false;
        hasBuiltSettlement = false;
        lastBuiltNode = null;
        currentPlayerId = nextPlayerId;
        this.turnCounter = turnCounter;
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
        boolean built = false;
        if (node.hasNoAdjacentBuildings() && isPlayersTurn(playerId)){
            if (isBuildingPhase()){
                if (!hasBuiltSettlement){
                    player.placeSettlement(node);
                    lastBuiltNode = node;
                    hasBuiltSettlement = true;
                    built = true;
                }
            } else if (hasRolled && player.hasResources(Settlement.costs) && player.canPlaceSettlementOn(node)){
                player.takeResources(Settlement.costs);
                player.placeSettlement(node);
                updateLongestRoadPlayer();
                built = true;
            }
        }
        if (built){
            Tile tile = node.getAdjacentTiles().iterator().next();
            clientCallback.asyncCallback(
                    new SettlementBuildingMessage(
                            playerId,
                            tile.getCoordinates(),
                            tile.getDirectionOfNode(node)
                    )
            );
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

            Tile tile = node.getAdjacentTiles().iterator().next();
            clientCallback.asyncCallback(
                    new CityBuildingMessage(
                            playerId,
                            tile.getCoordinates(),
                            tile.getDirectionOfNode(node)
                    )
            );
        }
    }

    public void buildRoad(Edge edge, int playerId){
        Player player = getPlayerById(playerId);
        boolean built = false;
        if (isPlayersTurn(playerId) && player.canPlaceRoadOn(edge)) {
            if (isBuildingPhase()){
                if (!hasBuiltRoad && lastBuiltNode != null && lastBuiltNode.getOutgoingEdges().contains(edge)){
                    player.placeRoad(edge);
                    hasBuiltRoad = true;
                    built = true;
                }
            } else if (hasRolled && player.hasResources(Road.costs) && !hasPlayedCard){
                player.takeResources(Road.costs);
                player.placeRoad(edge);
                updateLongestRoadPlayer();
                built = true;

            } else if (hasRolled && hasPlayedCard){
                player.placeRoad(edge);
                freeRoads--;
                updateLongestRoadPlayer();
                built = true;

                if (freeRoads == 0){
                    hasPlayedCard = false;
                }
            }
        }
        if (built){
            Tile tile = edge.getAdjacentTiles().iterator().next();
            clientCallback.asyncCallback(
                    new RoadBuildingMessage(
                            playerId,
                            tile.getCoordinates(),
                            tile.getDirectionOfEdge(edge)
                    )
            );
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

            updateCheaters(playerToId);

            clientCallback.asyncCallback(new PlayerResourcesMessage(PlayerResources.getInstance(), playerToId));
        }

    }

    public void updateCheaters(int cheater){
        ArrayList<Integer> cheaters;

        if (cheated.get(turnCounter) != null){  //more than 1 cheater per round
            cheaters = cheated.get(turnCounter);
            cheaters.add(cheater);
            cheated.put(turnCounter,cheaters);
        }else {
            cheaters = new ArrayList<>();
            cheaters.add(cheater);
            cheated.put(turnCounter, cheaters);
        }

        Log.i("CHEAT", "Player " + cheater + " cheated on turn " + turnCounter);
    }

    public boolean hasCheated(int cheaterId){
        for (int i = turnCounter; i > turnCounter - players.size() && i >= 0; i--){
            if (cheated.get(i) != null){
                List<Integer> cheater = cheated.get(i);
                if (cheater != null){
                    for (int cId : cheater){
                        if (cId == cheaterId){
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public void exposeCheater(int cheaterId){
        Player cheater = getPlayerById(cheaterId);
        Player player = getPlayerById(GameClient.getInstance().getId());

        for (Resource resource : Resource.values()){
            int resourceCount = cheater.getResourceCount(resource);
            int count;
            if (resourceCount%2 == 0) {
                count = resourceCount / 2;
            } else {
                count = resourceCount / 2 +1;
            }

            cheater.takeResource(resource, count);
            for (int i=0; i<count; i++){
                player.giveSingleResource(resource);
            }
        }

    }

    public void penaltyForFalseCharge(int playerToId) {
        Player playerTo = getPlayerById(playerToId);
        Player punishedPlayer = getPlayerById(GameClient.getInstance().getId());

        for (Resource resource : Resource.values()) {
            int count;
            int resourceCount = punishedPlayer.getResourceCount(resource);
            if (resourceCount % 2 == 0) {
                count = resourceCount / 2;
            } else {
                count = resourceCount / 2 + 1;
            }

            punishedPlayer.takeResource(resource, count);
            for (int i = 0; i < count; i++) {
                playerTo.giveSingleResource(resource);
            }
        }
    }

    public void updateLargestArmy(){
        if (largestArmyPlayer != null){
            if (getPlayerById(getCurrentPlayerId()).getPlayedKnights() > largestArmyPlayer.getPlayedKnights()){
                largestArmyPlayer.addVictoryPoints(-2);
                largestArmyPlayer = getPlayerById(getCurrentPlayerId());
                largestArmyPlayer.addVictoryPoints(2);
            }

        } else if (getPlayerById(getCurrentPlayerId()).getPlayedKnights() >= 3) {
            largestArmyPlayer = getPlayerById(getCurrentPlayerId());
            largestArmyPlayer.addVictoryPoints(2);
        }
    }

    public void doAsyncClientCallback(BaseMessage message){
        clientCallback.asyncCallback(message);
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

    public Player getLongestRoadPlayer() {
        return longestRoadPlayer;
    }
}
