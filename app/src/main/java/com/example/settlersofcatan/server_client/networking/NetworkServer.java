package com.example.settlersofcatan.server_client.networking;

import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;

import java.io.IOException;

public interface NetworkServer {

    /**
     * Starts the Server.
     *
     * @throws IOException
     */
    void start() throws IOException;

    /**
     * Registers a callback which gets called if a message is received.
     *
     * @param callback
     */
    void registerCallback(Callback<BaseMessage> callback);

    /**
     * Sends a message to all clients.
     *
     * @param message
     */
    void broadcastMessage(BaseMessage message);

    int getNumberOfConnections();
}
