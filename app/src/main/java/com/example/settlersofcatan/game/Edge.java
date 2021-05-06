package com.example.settlersofcatan.game;

/**
 * Class representing a edge of a tile, can be adjacent to two tiles.
 */
public class Edge {
    public Tile adjacent1;
    public Tile adjacent2;

    private Road road;
    private Harbor harbor;

    public Edge() {

    }

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }
}
