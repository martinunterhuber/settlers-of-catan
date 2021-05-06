package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Class representing a edge of a tile, can be adjacent to two tiles.
 */
public class Edge {
    final List<Tile> adjacentTiles = new ArrayList<>();
    final List<Node> endpointNodes = new ArrayList<>();

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

    public void addAdjacentTile(Tile tile){
        if (!adjacentTiles.contains(tile)){
            adjacentTiles.add(tile);
        }
    }

    public void setEndpoints(Node ... endpoints){
        for (Node endpoint : endpoints){
            endpointNodes.add(endpoint);
            endpoint.addOutgoingEdge(this);
        }
    }

    public List<Node> getEndpointNodes() {
        return endpointNodes;
    }

    public Node getOtherEndpoint(Node node){
        for (Node n : endpointNodes){
            if (n != node){
                return n;
            }
        }
        return null;
    }

    public boolean hasNoAdjacentBuildings(){
        return false;
    }
}
