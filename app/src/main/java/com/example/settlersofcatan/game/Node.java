package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Represents a Node of one or many tiles, can have a maximum of three adjacent tiles.
 * Can also have a building on it.
 */
public class Node {
    final Set<Tile> adjacentTiles = new HashSet<>();
    final Set<Edge> outgoingEdges = new HashSet<>();

    private NodePlaceable building;

    public Node() {

    }

    public NodePlaceable getBuilding() {
        return building;
    }

    public void setBuilding(NodePlaceable building) {
        this.building = building;
    }

    public Set<Node> getAdjacentNodes(){
        Set<Node> nodes = new HashSet<>();
        return nodes;
    }

    public void addOutgoingEdge(Edge outgoingEdge){
        outgoingEdges.add(outgoingEdge);

        if (adjacentTiles.size() > 3){
            throw new  IllegalStateException("Node has too many outgoing edges!");
        }
    }

    public void addAdjacentTile(Tile adjacent){
        adjacentTiles.add(adjacent);

        if (adjacentTiles.size() > 3){
            throw new  IllegalStateException("Edge has too many adjacent tiles!");
        }
    }

    public Set<Tile> getAdjacentTiles(){
        return adjacentTiles;
    }

    public Set<Tile> getAdjacentTilesExcept(Tile tile){
        Set<Tile> tiles = new HashSet<>(adjacentTiles);
        tiles.remove(tile);
        return tiles;
    }

    public boolean hasNoAdjacentBuildings(){
        // TODO
        return false;
    }
}
