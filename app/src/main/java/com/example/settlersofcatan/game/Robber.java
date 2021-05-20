package com.example.settlersofcatan.game;

public class Robber {
    private Tile tile;

    private Robber(){

    }

    public Robber(Tile tile){
        this.tile = tile;
        tile.setRobber(this);
    }

    public Tile getTile() {
        return tile;
    }

    public void setTile(Tile tile){
        this.tile = tile;
    }
}
