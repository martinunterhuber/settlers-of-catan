package com.example.settlersofcatan.game.board;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.buildings.Road;

import java.util.HashSet;
import java.util.Set;

/**
 * Class representing a edge of a tile, can be adjacent to two tiles.
 */
public class Edge {
    private final Set<Tile> adjacentTiles = new HashSet<>();
    private final Set<Node> endpointNodes = new HashSet<>();

    private Road road;
    private Harbor harbor;

    public Road getRoad() {
        return road;
    }

    public void setRoad(Road road) {
        this.road = road;
    }

    public Harbor getHarbor() {
        return harbor;
    }

    public void setHarbor(Harbor harbor) {
        this.harbor = harbor;
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

    public Set<Node> getEndpointNodes() {
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

    public boolean hasPlayersRoad(Player player){
        return road != null  && road.getPlayer() == player;
    }

    public Set<Tile> getAdjacentTiles() {
        return adjacentTiles;
    }
}
