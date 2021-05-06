package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Class representing a edge of a tile, can be adjacent to two tiles.
 */
public class Edge {
    final Set<Tile> adjacentTiles = new HashSet<>();
    final Set<Node> endpointNodes = new HashSet<>();

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
        adjacentTiles.add(tile);

        if (adjacentTiles.size() > 2){
            throw new  IllegalStateException("Edge has too many adjacent tiles!");
        }
    }

    public void setEndpoints(Node ... endpoints){
        for (Node endpoint : endpoints){
            endpointNodes.add(endpoint);
            endpoint.addOutgoingEdge(this);
        }
        if (endpointNodes.size() > 2){
            throw new  IllegalStateException("Edge has too many endpoints!");
        }
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
