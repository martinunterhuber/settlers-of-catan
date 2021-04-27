package com.example.settlersofcatan.game;

/**
 * Class representing a edge of a tile, can be adjacent to two tiles.
 */
public class Edge {
    private Tile adjacent1;
    private Tile adjacent2;

    private Road road;
    private Harbor harbor;

    public Edge(Tile adjacent1, Tile adjacent2) {
        this.adjacent1 = adjacent1;
    }
}
