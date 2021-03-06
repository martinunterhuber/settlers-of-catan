package com.example.settlersofcatan.server_client.networking.kryonet;

import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.NetworkClient;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;

import java.io.IOException;

public class NetworkClientKryo implements NetworkClient, KryoNetComponent {
    private Client client;
    private Callback<BaseMessage> callback;

    public NetworkClientKryo() {
        client = new Client(1000000, 1000000);
        client.getKryo().setReferences(true);
    }

    public void registerClass(Class<?> c) {
        client.getKryo().register(c);
    }

    public void connect(String host) throws IOException {
        client.start();
        client.connect(5000, host, NetworkConstants.TCP_PORT);

        client.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (callback != null && object instanceof BaseMessage)
                    callback.callback((BaseMessage) object);
            }
        });
    }

    public void registerCallback(Callback<BaseMessage> callback) {
        this.callback = callback;
    }

    public void sendMessage(BaseMessage message) {
        client.sendTCP(message);
    }

    public void disconnect(){
        client.close();
    }

    public boolean isConnected(){
        return client.isConnected();
    }
}
