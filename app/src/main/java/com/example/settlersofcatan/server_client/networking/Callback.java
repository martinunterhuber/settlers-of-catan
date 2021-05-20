package com.example.settlersofcatan.server_client.networking;

/**
 * Used for callbacks.
 */
public interface Callback<T> {

    void callback(T argument);

}
