package com.example.settlersofcatan.game;

/**
 * Represents a Node of one or many tiles, can have a maximum of three adjacent tiles.
 * Can also have a building on it.
 */
public class Node {

     Tile adjacent1;
     Tile adjacent2;
     Tile adjacent3;

    private NodePlaceable building;

    public Node() {

    }

    public NodePlaceable getBuilding() {
        return building;
    }

    public void setBuilding(NodePlaceable building) {
        this.building = building;
    }

    public void setFreeAdjacentNode(Tile adjacent){
        if (adjacent1 == null){
            adjacent1 = adjacent;
        } else if (adjacent2 == null){
            adjacent2 = adjacent;
        } else if (adjacent3 == null) {
            adjacent3 = adjacent;
        } else {
            throw new IllegalStateException("Too many adjacent tiles!");
        }
    }

    public Tile[] getAdjacentTiles(){
        return new Tile[]{adjacent1, adjacent2, adjacent3};
    }

    public Tile[] getAdjacentTilesExcept(Tile tile){
        if (tile.equals(adjacent1)){
            return new Tile[]{adjacent2, adjacent3};
        } else if (tile.equals(adjacent2)){
            return new Tile[]{adjacent1, adjacent3};
        } else if (tile.equals(adjacent3)) {
            return new Tile[]{adjacent1, adjacent2};
        } else {
            return getAdjacentTiles();
        }
    }
}
