package com.example.settlersofcatan.game;

/**
 * Represents a Node of one or many tiles, can have a maximum of three adjacent tiles.
 * Can also have a building on it.
 */
public class Node {

    private Tile adjacent1;
    private Tile adjacent2;
    private Tile adjacent3;

    private NodePlaceable building;

    public Node(Tile adjacent1) {
        this.adjacent1 = adjacent1;
    }

    public Node(Tile adjacent1, Tile adjacent2) {
        this.adjacent1 = adjacent1;
        this.adjacent2 = adjacent2;
    }

    public Node(Tile adjacent1, Tile adjacent2, Tile adjacent3) {
        this.adjacent1 = adjacent1;
        this.adjacent2 = adjacent2;
        this.adjacent3 = adjacent3;
    }

    public NodePlaceable getBuilding() {
        return building;
    }

    public void setBuilding(NodePlaceable building) {
        this.building = building;
    }
}
