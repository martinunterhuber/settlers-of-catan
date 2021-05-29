package com.example.settlersofcatan.server_client;

import android.content.Intent;
import android.util.Log;

import com.example.settlersofcatan.GameActivity;
import com.example.settlersofcatan.GameEndActivity;
import com.example.settlersofcatan.PlayerResources;
import com.example.settlersofcatan.Ranking;
import com.example.settlersofcatan.game.Board;
import com.example.settlersofcatan.game.City;
import com.example.settlersofcatan.game.DevelopmentCard;
import com.example.settlersofcatan.game.DevelopmentCardDeck;
import com.example.settlersofcatan.game.Direction;
import com.example.settlersofcatan.game.Edge;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Harbor;
import com.example.settlersofcatan.game.Knights;
import com.example.settlersofcatan.game.Monopoly;
import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.ResourceMap;
import com.example.settlersofcatan.game.Road;
import com.example.settlersofcatan.game.RoadBuilding;
import com.example.settlersofcatan.game.Robber;
import com.example.settlersofcatan.game.Settlement;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.game.TileCoordinates;
import com.example.settlersofcatan.game.VictoryPoints;
import com.example.settlersofcatan.game.YearOfPlenty;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.BuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.CityBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientDiceMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientWinMessage;
import com.example.settlersofcatan.server_client.networking.dto.DevelopmentCardMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.PlayerResourcesMessage;
import com.example.settlersofcatan.server_client.networking.dto.RoadBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.SettlementBuildingMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkClientKryo;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;

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
        client.registerClass(PlayerResourcesMessage.class);
        client.registerClass(PlayerResources.class);
        client.registerClass(TileCoordinates.class);
        client.registerClass(Direction.class);
        client.registerClass(SettlementBuildingMessage.class);
        client.registerClass(CityBuildingMessage.class);
        client.registerClass(RoadBuildingMessage.class);
        client.registerClass(BuildingMessage.class);
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
            }
            if (gameActivity != null) {
                // gameActivity.redrawViews();
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
}
