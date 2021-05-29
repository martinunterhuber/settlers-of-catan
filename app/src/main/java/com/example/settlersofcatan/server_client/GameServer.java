package com.example.settlersofcatan.server_client;

import android.util.Log;

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
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkServerKryo;

import java.io.IOException;
import java.net.BindException;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

public class GameServer {
    private static GameServer instance;
    private NetworkServerKryo server;
    private ArrayList<String> clientUsernames = new ArrayList<>();
    private Callback<String> userChangedCallback;

    private GameServer(){

    }

    public static GameServer getInstance(){
        if (instance == null){
            instance = new GameServer();
        }
        return instance;
    }

    public void init(){
        server = new NetworkServerKryo();
        registerMessageClasses();
        server.registerCallback(this::callback);
        startServer();
        clientUsernames.add("");
    }


    private void callback(BaseMessage message) {
        if (message instanceof ClientJoinedMessage){
            String username = ((ClientJoinedMessage) message).username;
            if (!username.equals(GameClient.getInstance().getUsername())){
                clientUsernames.add(username);
            } else {
                clientUsernames.set(0, username);
            }
            Log.i(NetworkConstants.TAG, username + " joined the lobby");
            userChangedCallback.callback(username);
        } else if (message instanceof ClientLeftMessage){
            String username = ((ClientLeftMessage) message).username;
            clientUsernames.remove(username);
            Log.i(NetworkConstants.TAG, username + " left the lobby");
            userChangedCallback.callback(username);
        } else if (message instanceof GameStateMessage) {
            broadcastMessage(message);
        }else if (message instanceof ClientWinMessage){
            broadcastMessage(message);
        }else if (message instanceof ClientDiceMessage){
            broadcastMessage(message);
        }else if (message instanceof DevelopmentCardMessage) {
            broadcastMessage(message);
        }else if (message instanceof PlayerResourcesMessage) {
            broadcastMessage(message);
        } else if (message instanceof BuildingMessage) {
            broadcastMessage(message);
        } else {
            Log.e(NetworkConstants.TAG,"Unknown message type!");
        }
    }

    public void registerUserChangedCallback(Callback<String> callback){
        this.userChangedCallback = callback;
    }

    private void registerMessageClasses(){
        server.registerClass(TextMessage.class);
        server.registerClass(ClientJoinedMessage.class);
        server.registerClass(ClientLeftMessage.class);
        server.registerClass(GameStateMessage.class);
        server.registerClass(ClientWinMessage.class);
        server.registerClass(Ranking.class);
        server.registerClass(Game.class);
        server.registerClass(HashSet.class);
        server.registerClass(ArrayList.class);
        server.registerClass(Board.class);
        server.registerClass(Player.class);
        server.registerClass(Tile.class);
        server.registerClass(Tile[].class);
        server.registerClass(Tile[][].class);
        server.registerClass(Edge.class);
        server.registerClass(Edge[].class);
        server.registerClass(Node.class);
        server.registerClass(Node[].class);
        server.registerClass(Settlement.class);
        server.registerClass(City.class);
        server.registerClass(Road.class);
        server.registerClass(Harbor.class);
        server.registerClass(Resource.class);
        server.registerClass(HashMap.class);
        server.registerClass(ResourceMap.class);
        server.registerClass(DevelopmentCardDeck.class);
        server.registerClass(DevelopmentCard.class);
        server.registerClass(DevelopmentCard[].class);
        server.registerClass(Knights.class);
        server.registerClass(VictoryPoints.class);
        server.registerClass(Monopoly.class);
        server.registerClass(RoadBuilding.class);
        server.registerClass(YearOfPlenty.class);
        server.registerClass(int[].class);
        server.registerClass(SecureRandom.class);
        server.registerClass(ClientDiceMessage.class);
        server.registerClass(Robber.class);
        server.registerClass(DevelopmentCardDeck.class);
        server.registerClass(DevelopmentCardMessage.class);
        server.registerClass(PlayerResourcesMessage.class);
        server.registerClass(PlayerResources.class);
        server.registerClass(TileCoordinates.class);
        server.registerClass(Direction.class);
        server.registerClass(SettlementBuildingMessage.class);
        server.registerClass(CityBuildingMessage.class);
        server.registerClass(RoadBuildingMessage.class);
        server.registerClass(BuildingMessage.class);
    }

    private void startServer(){
        try {
            server.start();
            Log.i(NetworkConstants.TAG, "Started Server");
        } catch (BindException e) {
            Log.i(NetworkConstants.TAG,"Server is already running");
        } catch (IOException e) {
            Log.e(NetworkConstants.TAG, e.getMessage(), e);
        }
    }

    public String getClientUsernameAt(int index){
        if (index >= clientUsernames.size()){
            return "";
        }
        return clientUsernames.get(index);
    }

    public ArrayList<String> getClientUsernames() {
        return clientUsernames;
    }

    public void broadcastMessage(BaseMessage message) {
        server.broadcastMessage(message);
    }
}
