package com.example.settlersofcatan.server_client;

import android.util.Log;

import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientJoinedMessage;
import com.example.settlersofcatan.server_client.networking.dto.ClientLeftMessage;
import com.example.settlersofcatan.server_client.networking.dto.TextMessage;
import com.example.settlersofcatan.server_client.networking.kryonet.NetworkClientKryo;

import java.io.IOException;

public class GameClient {
    private NetworkClientKryo client;
    private String username;

    GameClient(String host, String username) throws IOException {
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
    }

    private void callback(BaseMessage message){
        Log.i("Networking", message.toString());
    }


    private void connect(String host) throws IOException {
        client.connect(host);
        Log.i("Networking", "Connected to Host");
        client.sendMessage(new ClientJoinedMessage(username));
    }

    public void disconnect(){
        client.sendMessage(new ClientLeftMessage(username));
        client.close();
        Log.i("Networking", "Disconnected from Host");
    }
}
