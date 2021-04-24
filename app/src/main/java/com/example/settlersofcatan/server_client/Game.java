package com.example.settlersofcatan.server_client;

public class Game {
    private static Game instance;
    private GameClient client;
    private GameServer server;

    private Game(){

    }

    public GameClient getClient() {
        return client;
    }

    public void setClient(GameClient client) {
        this.client = client;
    }

    public GameServer getServer() {
        return server;
    }

    public void setServer(GameServer server) {
        this.server = server;
    }

    public static Game getInstance(){
        if (instance == null){
            instance = new Game();
        }
        return instance;
    }
}
