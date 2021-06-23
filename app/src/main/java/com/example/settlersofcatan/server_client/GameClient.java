package com.example.settlersofcatan.server_client;

import android.content.Intent;
import android.util.Log;

import com.example.settlersofcatan.ui.color.ChooseColorActivity;
import com.example.settlersofcatan.ui.color.PlayerColors;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.board.Board;
import com.example.settlersofcatan.game.board.Direction;
import com.example.settlersofcatan.game.board.Edge;
import com.example.settlersofcatan.game.board.Harbor;
import com.example.settlersofcatan.game.board.Node;
import com.example.settlersofcatan.game.board.Robber;
import com.example.settlersofcatan.game.board.Tile;
import com.example.settlersofcatan.game.board.TileCoordinates;
import com.example.settlersofcatan.game.buildings.City;
import com.example.settlersofcatan.game.buildings.Road;
import com.example.settlersofcatan.game.buildings.Settlement;
import com.example.settlersofcatan.game.development_cards.DevelopmentCard;
import com.example.settlersofcatan.game.development_cards.DevelopmentCardDeck;
import com.example.settlersofcatan.game.development_cards.Knights;
import com.example.settlersofcatan.game.development_cards.Monopoly;
import com.example.settlersofcatan.game.development_cards.RoadBuilding;
import com.example.settlersofcatan.game.development_cards.VictoryPoints;
import com.example.settlersofcatan.game.development_cards.YearOfPlenty;
import com.example.settlersofcatan.game.resources.PlayerResources;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;
import com.example.settlersofcatan.game.trade.TradeOffer;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.ArmySizeIncreaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.BuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.CityBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.ColorMessage;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.EndTurnMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.MovedRobberMessage;
import com.example.settlersofcatan.server_client.networking.dto.PlayerResourcesMessage;
import com.example.settlersofcatan.server_client.networking.dto.RoadBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.SettlementBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.dto.TradeOfferMessage;
import com.example.settlersofcatan.server_client.networking.dto.TradeReplyMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkClientKryo;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.ui.game.GameActivity;
import com.example.settlersofcatan.ui.ranking.GameEndActivity;
import com.example.settlersofcatan.ui.ranking.Ranking;
import com.example.settlersofcatan.ui.trade.WaitForReplyActivity;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameClient {
    private static GameClient instance;
    private NetworkClientKryo client;
    private String username = "";
    private int id;
    private Callback<BaseMessage> startGameCallback;
    private GameActivity gameActivity;
    private ChooseColorActivity colorActivity;
    private WaitForReplyActivity waitForReplyActivity;

    private GameClient(){

    }

    public static GameClient getInstance(){
        if (instance == null){
            instance = new GameClient();
        }
        return instance;
    }

    public void connect(String host, String username) {
        this.username = username;
        client = new NetworkClientKryo();
        registerMessageClasses();
        client.registerCallback(this::callback);
        try {
            connect(host);
        } catch (IOException e){
            Log.e(NetworkConstants.TAG, "Failed to connect to " + host);
        }
    }

    public boolean isConnected(){
        return client.isConnected();
    }

    private void registerMessageClasses(){
        client.registerClass(TextMessage.class);
        client.registerClass(ClientJoinedMessage.class);
        client.registerClass(ClientLeftMessage.class);
        client.registerClass(GameStateMessage.class);
        client.registerClass(ClientWinMessage.class);
        client.registerClass(Ranking.class);
        client.registerClass(Game.class);
        client.registerClass(HashSet.class);
        client.registerClass(ArrayList.class);
        client.registerClass(Board.class);
        client.registerClass(Player.class);
        client.registerClass(Tile.class);
        client.registerClass(Tile[].class);
        client.registerClass(Tile[][].class);
        client.registerClass(Edge.class);
        client.registerClass(Edge[].class);
        client.registerClass(Node.class);
        client.registerClass(Node[].class);
        client.registerClass(Settlement.class);
        client.registerClass(City.class);
        client.registerClass(Road.class);
        client.registerClass(Harbor.class);
        client.registerClass(Resource.class);
        client.registerClass(HashMap.class);
        client.registerClass(ResourceMap.class);
        client.registerClass(DevelopmentCardDeck.class);
        client.registerClass(DevelopmentCard.class);
        client.registerClass(DevelopmentCard[].class);
        client.registerClass(Knights.class);
        client.registerClass(VictoryPoints.class);
        client.registerClass(Monopoly.class);
        client.registerClass(RoadBuilding.class);
        client.registerClass(YearOfPlenty.class);
        client.registerClass(int[].class);
        client.registerClass(SecureRandom.class);
        client.registerClass(ClientDiceMessage.class);
        client.registerClass(Robber.class);
        client.registerClass(DevelopmentCardDeck.class);
        client.registerClass(DevelopmentCardMessage.class);
        client.registerClass(TradeOfferMessage.class);
        client.registerClass(TradeOffer.class);
        client.registerClass(TradeReplyMessage.class);
        client.registerClass(PlayerResourcesMessage.class);
        client.registerClass(PlayerResources.class);
        client.registerClass(TileCoordinates.class);
        client.registerClass(Direction.class);
        client.registerClass(SettlementBuildingMessage.class);
        client.registerClass(CityBuildingMessage.class);
        client.registerClass(RoadBuildingMessage.class);
        client.registerClass(BuildingMessage.class);
        client.registerClass(MovedRobberMessage.class);
        client.registerClass(EndTurnMessage.class);
        client.registerClass(ArmySizeIncreaseMessage.class);
        client.registerClass(ChooseColorActivity.class);
        client.registerClass(ColorMessage.class);
        client.registerClass(PlayerColors.class);
    }

    private void gameAsyncCallback(BaseMessage message){
        new Thread(() -> sendMessage(message)).start();
    }

    private void callback(BaseMessage message){
        if (message instanceof GameStateMessage){
            handleGameStateChange(message);
        } else if (message instanceof ClientWinMessage && gameActivity != null){
            handleClientWin();
        } else if (message instanceof PlayerResourcesMessage && gameActivity != null){
            handleResourceUpdate((PlayerResourcesMessage) message);
        } else if (message instanceof ClientDiceMessage && gameActivity != null){
            handleDiceRoll((ClientDiceMessage) message);
        } else if (message instanceof DevelopmentCardMessage){
            DevelopmentCardDeck.setInstance(((DevelopmentCardMessage) message).deck);
        } else if (message instanceof TradeOfferMessage) {
            handleTradeOffer((TradeOfferMessage) message);
        } else if (message instanceof TradeReplyMessage) {
            handleTradeReply((TradeReplyMessage) message);
        } else if (message instanceof BuildingMessage && gameActivity != null) {
            handleBuilding(message);
        } else if (message instanceof MovedRobberMessage && gameActivity != null) {
            handleMoveRobber((MovedRobberMessage) message);
        } else if (message instanceof EndTurnMessage){
            handleTurnEnd((EndTurnMessage) message);
        } else if (message instanceof ArmySizeIncreaseMessage){
            handleArmyIncrease((ArmySizeIncreaseMessage) message);
        } else if (message instanceof ColorMessage && colorActivity != null){
            handleColorChange((ColorMessage) message);
        }
        Log.i(NetworkConstants.TAG, message.toString());
    }
  
    private void handleColorChange(ColorMessage message) {
        PlayerColors.getInstance().setSinglePlayerColor(message.playerId, message.playerColor);

        colorActivity.runOnUiThread(()-> {
            colorActivity.disableColor();
            colorActivity.startGame();
        });
    }

    private void handleArmyIncrease(ArmySizeIncreaseMessage message) {
        int playerId = message.playerId;
        Game game = Game.getInstance();
        game.getPlayerById(playerId).incrementPlayedKnights();
        game.updateLargestArmy();
        if (gameActivity != null) {
            gameActivity.redrawViews();
        }
    }

    private void handleTurnEnd(EndTurnMessage message) {
        Game game = Game.getInstance();
        game.initializeNextTurn(message.nextPlayerId, message.turnCount);
        if (gameActivity != null) {
            gameActivity.redrawViewsTurnEnd();
        }
    }

    private void handleMoveRobber(MovedRobberMessage message) {
        Board board = Game.getInstance().getBoard();
        board.moveRobberTo(board.getTileByCoordinates(message.coordinates));
        gameActivity.redrawViews();
    }

    private void handleBuilding(BaseMessage message) {
        BuildingMessage buildMessage = (BuildingMessage) message;
        Game game = Game.getInstance();
        if (buildMessage.playerId != id) {
            Tile tile = game.getBoard().getTileByCoordinates(buildMessage.tileCoordinates);
            Player player = game.getPlayerById(buildMessage.playerId);
            if (message instanceof CityBuildingMessage) {
                player.placeCity(tile.getNodeByDirection(buildMessage.direction));
            } else if (message instanceof RoadBuildingMessage) {
                player.placeRoad(tile.getEdgeByDirection(buildMessage.direction));
            } else {
                player.placeSettlement(tile.getNodeByDirection(buildMessage.direction));
            }
            gameActivity.redrawViews();
        }
    }

    private void handleTradeReply(TradeReplyMessage message) {
        if (waitForReplyActivity != null) {
            waitForReplyActivity.getReply(message.acceptedTrade);
            waitForReplyActivity = null;
        }
    }

    private void handleTradeOffer(TradeOfferMessage message) {
        TradeOffer tradeOffer = message.tradeOffer;
        if (waitForReplyActivity != null && tradeOffer.getTo().getId() == id) {
            waitForReplyActivity.getCounterOffer();
            waitForReplyActivity = null;
        }
        if (gameActivity != null && tradeOffer.getTo().getId() == id) {
            gameActivity.runOnUiThread(() -> gameActivity.displayTradeOffer(tradeOffer));
        }
    }

    private void handleDiceRoll(ClientDiceMessage message) {
        gameActivity.runOnUiThread(() -> gameActivity.updateOpponentView(
                message.getUsername(),
                message.getRolled())

        );
    }

    private void handleResourceUpdate(PlayerResourcesMessage message) {
        PlayerResources.setInstance(message.playerResources);
        for (Player p : Game.getInstance().getPlayers()){
            p.updateResources(PlayerResources.getInstance().getSinglePlayerResources(p.getId()));
        }

        if (message.cheaterId != -1){
            Game.getInstance().updateCheaters(message.cheaterId);
        }

        gameActivity.redrawViews();
    }

    private void handleClientWin() {
        Intent intent = new Intent(gameActivity, GameEndActivity.class);
        gameActivity.startActivity(intent);
        gameActivity.finish();
    }

    private void handleGameStateChange(BaseMessage message) {
        Game game = ((GameStateMessage) message).game;
        game.setClientCallback(this::gameAsyncCallback);
        Game.setInstance(game);
        for (Player player : Game.getInstance().getPlayers()){
            if (player.getName().equals(this.username)){
                id = player.getId();
            }
        }
        if (startGameCallback != null){
            startGameCallback.callback(message);
            startGameCallback = null;
        }
        if (gameActivity != null) {
            gameActivity.redrawViewsNewGameState();
        }
    }

    public void registerActivity(GameActivity activity){
        gameActivity = activity;
    }

    public void registerActivity(ChooseColorActivity activity){
        colorActivity = activity;
    }

    public void registerStartGameCallback(Callback<BaseMessage> c){
        this.startGameCallback = c;
    }


    private void connect(String host) throws IOException {
        client.connect(host);
        Log.i(NetworkConstants.TAG, "Connected to Host");
        client.sendMessage(new ClientJoinedMessage(username));
    }

    public void disconnect(){
        client.sendMessage(new ClientLeftMessage(username));
        client.disconnect();
        Log.i(NetworkConstants.TAG, "Disconnected from Host");
    }

    public void sendMessage(BaseMessage message){
        client.sendMessage(message);
    }

    public String getUsername() {
        return username;
    }

    public int getId() {
        return id;
    }

    public GameActivity getGameActivity(){
        return gameActivity;
    }

    public void registerWaitForReplyActivity(WaitForReplyActivity activity) {
        waitForReplyActivity = activity;
    }
}
