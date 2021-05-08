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
        for (Node node : getAdjacentNodes()){
            if (node.getBuilding() != null){
                return false;
            }
        }
        return true;
    }

    public Set<Edge> getOutgoingEdges() {
        return outgoingEdges;
    }
}
