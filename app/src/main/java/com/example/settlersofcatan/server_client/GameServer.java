package com.example.settlersofcatan.server_client;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.example.settlersofcatan.game.Game;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.GameStateMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkConstants;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkServerKryo;

import java.io.IOException;
import java.net.BindException;
import java.util.ArrayList;

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
            }
            Log.i(NetworkConstants.TAG, username + " joined the lobby");
            userChangedCallback.callback(username);
        } else if (message instanceof ClientLeftMessage){
            String username = ((ClientLeftMessage) message).username;
            clientUsernames.remove(username);
            Log.i(NetworkConstants.TAG, username + " left the lobby");
            userChangedCallback.callback(username);
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
        server.registerClass(Game.class);
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

    public void broadcastMessage(BaseMessage message) {
        server.broadcastMessage(message);
    }
}
