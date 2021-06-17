package com.example.settlersofcatan.game.board;

import com.example.settlersofcatan.game.buildings.NodePlaceable;
import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.buildings.Settlement;

import java.util.HashSet;
import java.util.Set;

/**
 * Represents a Node of one or many tiles, can have a maximum of three adjacent tiles.
 * Can also have a building on it.
 */
public class Node {
    private final Set<Tile> adjacentTiles = new HashSet<>();
    private final Set<Edge> outgoingEdges = new HashSet<>();

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
        for (Edge edge : outgoingEdges){
            nodes.add(edge.getOtherEndpoint(this));
        }
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

    public Set<Edge> getOutgoingEdgesExcept(Edge edge){
        Set<Edge> edges = new HashSet<>(outgoingEdges);
        edges.remove(edge);
        return edges;
    }

    public Set<Tile> getAdjacentTilesExcept(Tile tile){
        Set<Tile> tiles = new HashSet<>(adjacentTiles);
        tiles.remove(tile);
        return tiles;
    }

    public Set<Edge> getOtherOutgoingEdges(Edge edge) {
        Set<Edge> otherOutgoingEdges = new HashSet<>();
        for (Edge e : outgoingEdges) {
            if (e != edge) {
                otherOutgoingEdges.add(e);
            }
        }
        return otherOutgoingEdges;
    }

    public boolean hasNoAdjacentBuildings(){
        if (building != null){
            return false;
        }
        for (Node node : getAdjacentNodes()){
            if (node.getBuilding() != null){
                return false;
            }
        }
        return true;
    }

    public boolean hasPlayersSettlement(int playerId){
        return building != null
                && playerId == building.getPlayerId()
                && building instanceof Settlement;
    }

    /**
     * Method to check if a Node has an adjacent Harbor
     * @return true if so, else false
     */
    public boolean isAdjacentToHarbor() {
        for (Edge edge : outgoingEdges) {
            if (edge.getHarbor() != null) {
                return true;
            }
        }
        return false;
    }

    public boolean hasPlayersBuildingOrNone(Player player){
        return building == null || building.getPlayer() == player;
    }

    /**
     * Method to return an adjacent harbor, (Node can only have one adjacent harbor)
     * @return The adjacent harbor, null if none exists.
     */
    public Harbor getAdjacentHarbor() {
        for (Edge edge : outgoingEdges) {
            if (edge.getHarbor() != null) {
                return edge.getHarbor();
            }
        }
        return null;
    }

    public Set<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }
}
