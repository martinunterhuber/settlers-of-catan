package com.example.settlersofcatan.server_client.networking.kryonet;

import android.util.Log;

import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;
import com.esotericsoftware.kryonet.Server;
import com.example.settlersofcatan.server_client.networking.Callback;
import com.example.settlersofcatan.server_client.networking.NetworkServer;
import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;

import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

public class NetworkServerKryo implements NetworkServer, KryoNetComponent {
    private Server server;
    private Callback<BaseMessage> messageCallback;

    public NetworkServerKryo() {
        server = new Server(1000000, 1000000);
        server.getKryo().setReferences(true);
    }

    public void registerClass(Class<?> c) {
        server.getKryo().register(c);
    }

    public void start() throws IOException {
        server.start();
        server.bind(NetworkConstants.TCP_PORT);

        server.addListener(new Listener() {
            @Override
            public void received(Connection connection, Object object) {
                if (messageCallback != null && object instanceof BaseMessage)
                    messageCallback.callback((BaseMessage) object);
            }
        });
    }

    public void registerCallback(Callback<BaseMessage> callback) {
        this.messageCallback = callback;
    }

    public void broadcastMessage(BaseMessage message) {
        for (Connection connection : server.getConnections())
            connection.sendTCP(message);
    }

    public int getNumberOfConnections(){
        return server.getConnections().length;
    }

    public static String getLocalIPAddress(){
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> addresses = networkInterface.getInetAddresses();

                while (addresses.hasMoreElements()) {
                    InetAddress address = addresses.nextElement();
                    if (!address.isLoopbackAddress() && address instanceof Inet4Address){
                        return address.getHostAddress();
                    }
                }
            }
        } catch (Exception e) {
            Log.e(NetworkConstants.TAG, e.getMessage(), e);
        }
        return "-";
    }
}
