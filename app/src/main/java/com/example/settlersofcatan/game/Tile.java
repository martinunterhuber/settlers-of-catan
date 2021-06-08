package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.example.settlersofcatan.game.Direction.*;

/**
 * Class to represent a tile on the board, has hexagonal shape and as  such 6 nodes and 6 edges.
 * It furthermore has a resource it belongs to. If its resource is null, it is desert.
 */
public class Tile {

    private final Map<Direction, Edge> edgeDirectionMap = new HashMap<>();
    private final Edge[] edges = new Edge[6];

    private final Map<Direction, Node> nodeDirectionMap = new HashMap<>();
    private final Node[] nodes = new Node[6];

    private final Resource resource;

    private final TileCoordinates coordinates;

    private final int number;

    private Robber robber;

    private Tile() {
        this(null, null, -1);
    }

    public Tile(TileCoordinates coordinates, Resource resource, int number) {
        this.coordinates = coordinates;
        this.resource = resource;
        this.number = number;
    }

    public void initNodes(){
        Direction[] nodeDirections = Direction.nodeValues();
        for (int i = 0; i < nodes.length; i++) {
            nodes[i] = nodeDirectionMap.get(nodeDirections[i]);
        }
    }

    public void initEdges(){
        Direction[] edgeDirections = Direction.edgeValues();
        for (int i = 0; i < edges.length; i++) {
            edges[i] = edgeDirectionMap.get(edgeDirections[i]);
        }
    }

    /**
     * Activates the giveResource method for all buildings adjacent to this tile,
     * used by the maps dice rolled method. implement robber functionality.
     */
    public void giveResource() {
        // Case for the desert tile
        if (this.resource == null){
            return;
        }

        for (Node n : nodes) {
            if (n.getBuilding() != null) {
                n.getBuilding().giveResource(resource);
            }
        }
    }

    /**
     * Returns a list of all the names of other players which have a building
     * on a node adjacent to this tile
     */
    public List<String> getAdjacentPlayersNamesExcept(Player player){
        List<String> players = new ArrayList<>();
        for (Node node : nodes){
            if (node.getBuilding() != null){
                Player other = node.getBuilding().getPlayer();
                if (player.getId() != other.getId() && !players.contains(other.getName())){
                    players.add(other.getName());
                }
            }
        }
        return players;
    }

    public int getNumber() {
        return number;
    }

    public Edge getEastEdge() {
        return edgeDirectionMap.get(EAST);
    }

    void setEastEdge(Edge eastEdge) {
        this.edgeDirectionMap.put(EAST, eastEdge);
    }

    public Edge getNortheastEdge() {
        return edgeDirectionMap.get(NORTH_EAST);
    }

    void setNortheastEdge(Edge northeastEdge) {
        this.edgeDirectionMap.put(NORTH_EAST, northeastEdge);
    }

    public Edge getNorthwestEdge() {
        return edgeDirectionMap.get(NORTH_WEST);
    }

    void setNorthwestEdge(Edge northwestEdge) {
        this.edgeDirectionMap.put(NORTH_WEST, northwestEdge);
    }

    public Edge getSoutheastEdge() {
        return edgeDirectionMap.get(SOUTH_EAST);
    }

    void setSoutheastEdge(Edge southeastEdge) {
        this.edgeDirectionMap.put(SOUTH_EAST, southeastEdge);
    }

    public Edge getWestEdge() {
        return edgeDirectionMap.get(WEST);
    }

    void setWestEdge(Edge westEdge) {
        this.edgeDirectionMap.put(WEST, westEdge);
    }

    public Edge getSouthwestEdge() {
        return edgeDirectionMap.get(SOUTH_WEST);
    }

    void setSouthwestEdge(Edge southwestEdge) {
        this.edgeDirectionMap.put(SOUTH_WEST, southwestEdge);
    }

    public Edge[] getEdges() {
        return edges;
    }

    public Edge getEdgeByDirection(Direction direction){
        return edgeDirectionMap.get(direction);
    }

    public Direction getDirectionOfEdge(Edge edge){
        for (Map.Entry<Direction, Edge> entry : edgeDirectionMap.entrySet()){
            if (edge == entry.getValue()){
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Node does not belong to this Tile!");
    }

    public Node getNorthNode() {
        return nodeDirectionMap.get(NORTH);
    }

    void setNorthNode(Node northNode) {
        this.nodeDirectionMap.put(NORTH, northNode);
    }

    public Node getNortheastNode() {
        return nodeDirectionMap.get(NORTH_EAST);
    }

    void setNortheastNode(Node northeastNode) {
        this.nodeDirectionMap.put(NORTH_EAST, northeastNode);
    }

    public Node getSoutheastNode() {
        return nodeDirectionMap.get(SOUTH_EAST);
    }

    void setSoutheastNode(Node southeastNode) {
        this.nodeDirectionMap.put(SOUTH_EAST, southeastNode);
    }

    public Node getSouthNode() {
        return nodeDirectionMap.get(SOUTH);
    }

    void setSouthNode(Node southNode) {
        this.nodeDirectionMap.put(SOUTH, southNode);
    }

    public Node getSouthwestNode() {
        return nodeDirectionMap.get(SOUTH_WEST);
    }

    void setSouthwestNode(Node southwestNode) {
        this.nodeDirectionMap.put(SOUTH_WEST, southwestNode);
    }

    public Node getNorthwestNode() {
        return nodeDirectionMap.get(NORTH_WEST);
    }

    void setNorthwestNode(Node northwestNode) {
        this.nodeDirectionMap.put(NORTH_WEST, northwestNode);
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Node getNodeByDirection(Direction direction){
        return nodeDirectionMap.get(direction);
    }

    public Direction getDirectionOfNode(Node node){
        for (Map.Entry<Direction, Node> entry : nodeDirectionMap.entrySet()){
            if (node == entry.getValue()){
                return entry.getKey();
            }
        }
        throw new IllegalArgumentException("Node does not belong to this Tile!");
    }

    public Resource getResource() {
        return resource;
    }

    public int getQ() {
        return coordinates.getQ();
    }

    public int getR() {
        return coordinates.getR();
    }

    public double getX(){
        return coordinates.getX();
    }

    public double getY(){
        return coordinates.getY();
    }

    public boolean hasRobber(){
        return robber != null;
    }

    public Robber getRobber() {
        return robber;
    }

    void setRobber(Robber robber) {
        this.robber = robber;
    }

    public TileCoordinates getCoordinates() {
        return coordinates;
    }
}
