package com.example.settlersofcatan.server_client;

import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.example.settlersofcatan.GameActivity;
import com.example.settlersofcatan.R;
import com.example.settlersofcatan.game.Board;
import com.example.settlersofcatan.game.City;
import com.example.settlersofcatan.game.Edge;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.game.Harbor;
import com.example.settlersofcatan.game.Node;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.Resource;
import com.example.settlersofcatan.game.ResourceMap;
import com.example.settlersofcatan.game.Road;
import com.example.settlersofcatan.game.Settlement;
import com.example.settlersofcatan.game.Tile;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkClientKryo;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;

public class GameClient {
    private static GameClient instance;
    private NetworkClientKryo client;
    private String username = "";
    private int id;
    private Callback<BaseMessage> startGameCallback;
    private AppCompatActivity gameActivity;

    private GameClient(){

    }

    public static GameClient getInstance(){
        if (instance == null){
            instance = new GameClient();
        }
        return instance;
    }

    public void init(String host, String username) throws IOException {
        this.username = username;
        client = new NetworkClientKryo();
        registerMessageClasses();
        client.registerCallback(this::callback);
        connect(host);
    }

    private void registerMessageClasses(){
        client.registerClass(TextMessage.class);
        client.registerClass(ClientJoinedMessage.class);
        client.registerClass(ClientLeftMessage.class);
        client.registerClass(GameStateMessage.class);
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
    }

    private void callback(BaseMessage message){
        if (message instanceof GameStateMessage){
            Game.setInstance(((GameStateMessage) message).game);
            for (Player player : Game.getInstance().getPlayers()){
                if (player.getName().equals(this.username)){
                    id = player.getId();
                }
            }
            if (startGameCallback != null){
                startGameCallback.callback(message);
            }
            if (gameActivity != null) {
                gameActivity.overridePendingTransition(0, 0);
                gameActivity.findViewById(R.id.mapView).invalidate();
                gameActivity.findViewById(R.id.playerView).invalidate();
                gameActivity.findViewById(R.id.resourceView).invalidate();
                gameActivity.overridePendingTransition(0, 0);
            }
        }
        Log.i(NetworkConstants.TAG, message.toString());
    }

    public void registerActivity(AppCompatActivity activity){
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
        client.close();
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
}
