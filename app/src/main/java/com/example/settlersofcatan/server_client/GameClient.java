package com.example.settlersofcatan.server_client;

import android.content.Intent;
import android.util.Log;

import com.example.settlersofcatan.ui.game.GameActivity;
import com.example.settlersofcatan.ui.ranking.GameEndActivity;
import com.example.settlersofcatan.game.resources.PlayerResources;
import com.example.settlersofcatan.ui.ranking.Ranking;
import com.example.settlersofcatan.game.board.Board;
import com.example.settlersofcatan.game.buildings.City;
import com.example.settlersofcatan.game.development_cards.DevelopmentCard;
import com.example.settlersofcatan.game.development_cards.DevelopmentCardDeck;
import com.example.settlersofcatan.game.board.Direction;
import com.example.settlersofcatan.game.board.Edge;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.board.Harbor;
import com.example.settlersofcatan.game.development_cards.Knights;
import com.example.settlersofcatan.game.development_cards.Monopoly;
import com.example.settlersofcatan.game.board.Node;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;
import com.example.settlersofcatan.game.buildings.Road;
import com.example.settlersofcatan.game.development_cards.RoadBuilding;
import com.example.settlersofcatan.game.board.Robber;
import com.example.settlersofcatan.game.buildings.Settlement;
import com.example.settlersofcatan.game.board.Tile;
import com.example.settlersofcatan.game.trade.TradeOffer;
import com.example.settlersofcatan.game.board.TileCoordinates;
import com.example.settlersofcatan.game.development_cards.VictoryPoints;
import com.example.settlersofcatan.game.development_cards.YearOfPlenty;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.ArmySizeIncreaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.BuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.CityBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
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
    }

    private void gameAsyncCallback(BaseMessage message){
        new Thread(() -> sendMessage(message)).start();
    }

    private void callback(BaseMessage message){
        if (message instanceof GameStateMessage){
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
        } else if (message instanceof ClientWinMessage && gameActivity != null){
            Intent intent = new Intent(gameActivity, GameEndActivity.class);
            gameActivity.startActivity(intent);
            gameActivity.finish();
        } else if (message instanceof PlayerResourcesMessage && gameActivity != null){
            PlayerResources.setInstance(((PlayerResourcesMessage) message).playerResources);
            for (Player p : Game.getInstance().getPlayers()){
                p.updateResources(PlayerResources.getInstance().getSinglePlayerResources(p.getId()));
            }

            if (((PlayerResourcesMessage) message).cheaterId != -1){
                Game.getInstance().updateCheaters(((PlayerResourcesMessage) message).cheaterId);
            }

            gameActivity.redrawViews();

        } else if (message instanceof ClientDiceMessage && gameActivity != null){
            gameActivity.runOnUiThread(() -> gameActivity.updateOpponentView(
                    ((ClientDiceMessage) message).getUsername(),
                    ((ClientDiceMessage) message).getRolled())

            );
        } else if (message instanceof DevelopmentCardMessage){
            DevelopmentCardDeck.setInstance(((DevelopmentCardMessage) message).deck);
        } else if (message instanceof TradeOfferMessage) {
            TradeOffer tradeOffer = ((TradeOfferMessage) message).tradeOffer;
            if (waitForReplyActivity != null && tradeOffer.getTo().getId() == id) {
                waitForReplyActivity.getCounterOffer();
                waitForReplyActivity = null;
            }
            if (gameActivity != null && tradeOffer.getTo().getId() == id) {
                gameActivity.runOnUiThread(() -> gameActivity.displayTradeOffer(tradeOffer));
            }
        } else if (message instanceof TradeReplyMessage) {
            if (waitForReplyActivity != null) {
                waitForReplyActivity.getReply(((TradeReplyMessage) message).acceptedTrade);
                waitForReplyActivity = null;
            }
        } else if (message instanceof BuildingMessage && gameActivity != null) {
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
        } else if (message instanceof MovedRobberMessage && gameActivity != null) {
            MovedRobberMessage robberMessage = (MovedRobberMessage)message;
            Board board = Game.getInstance().getBoard();
            board.moveRobberTo(board.getTileByCoordinates(robberMessage.coordinates));
            gameActivity.redrawViews();
        } else if (message instanceof EndTurnMessage){
            EndTurnMessage turnMessage = (EndTurnMessage) message;
            Game game = Game.getInstance();
            game.initializeNextTurn(turnMessage.nextPlayerId, turnMessage.turnCount);
            if (gameActivity != null) {
                gameActivity.redrawViewsTurnEnd();
            }
        } else if (message instanceof ArmySizeIncreaseMessage){
            int playerId = ((ArmySizeIncreaseMessage) message).playerId;
            Game game = Game.getInstance();
            game.getPlayerById(playerId).incrementPlayedKnights();
            game.updateLargestArmy();
            if (gameActivity != null) {
                gameActivity.redrawViews();
            }
        }
        Log.i(NetworkConstants.TAG, message.toString());
    }

    public void registerActivity(GameActivity activity){
        gameActivity = activity;
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
