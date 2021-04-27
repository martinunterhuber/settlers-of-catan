package com.example.settlersofcatan.server_client.networking.kryonet;

import com.example.settlersofcatan.server_client.networking.dto.BaseMessage;

public interface KryoNetComponent {

    /**
     * Register a class for serialization.
     *
     * @param c
     */
    void registerClass(Class<? extends BaseMessage> c);

}
