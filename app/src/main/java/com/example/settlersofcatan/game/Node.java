package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a Node of one or many tiles, can have a maximum of three adjacent tiles.
 * Can also have a building on it.
 */
public class Node {
    final List<Tile> adjacentTiles = new ArrayList<>();
    final List<Edge> outgoingEdges = new ArrayList<>();

    private NodePlaceable building;

    public Node() {

    }

    public NodePlaceable getBuilding() {
        return building;
    }

    public void setBuilding(NodePlaceable building) {
        this.building = building;
    }

    public List<Node> getAdjacentNodes(){
        List<Node> nodes = new ArrayList<>();
        for (Edge e : outgoingEdges) {
            nodes.add(e.getOtherEndpoint(this));
        }
        return nodes;
    }

    public void addOutgoingEdge(Edge outgoingEdge){
        if (!outgoingEdges.contains(outgoingEdge) && outgoingEdges.size() < 3){
            outgoingEdges.add(outgoingEdge);
        }
    }

    public void addAdjacentTile(Tile adjacent){
        if (!adjacentTiles.contains(adjacent)){
            adjacentTiles.add(adjacent);
        }
    }

    public List<Tile> getAdjacentTiles(){
        return adjacentTiles;
    }

    public List<Tile> getAdjacentTilesExcept(Tile tile){
        List<Tile> tiles = new ArrayList<>();
        for (Tile t : adjacentTiles){
            if (t != tile){
                tiles.add(t);
            }
        }
        return tiles;
    }

    public List<Edge> getOtherOutgoingEdges(Edge edge) {
        List<Edge> otherOutgoingEdges = new ArrayList<>();
        for (Edge e : outgoingEdges) {
            if (e != edge) {
                otherOutgoingEdges.add(e);
            }
        }
        return otherOutgoingEdges;
    }

    public boolean hasNoAdjacentBuildings(){
        List<Node> adjacentNodes = getAdjacentNodes();
        for (Node n : adjacentNodes) {
            if (n.getBuilding() != null) {
                return  false;
            }
        }
        return true;
    }
}
