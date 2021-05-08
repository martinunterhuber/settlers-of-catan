package com.example.settlersofcatan.game;

/**
 * Class to represent a tile on the board, has hexagonal shape and as  such 6 nodes and 6 edges.
 * It furthermore has a resource it belongs to. If its resource is null, it is desert.
 */
public class Tile {

    private Edge eastEdge;
    private Edge northeastEdge;
    private Edge northwestEdge;
    private Edge southeastEdge;
    private Edge westEdge;
    private Edge southwestEdge;

    private Edge[] edges;

    private Node northNode;
    private Node northeastNode;
    private Node southeastNode;
    private Node southNode;
    private Node southwestNode;
    private Node northwestNode;

    private Node[] nodes;

    private Resource resource;

    private int q;
    private int r;

    private int number;

    private Tile() {
        this(null, -1);
    }

    public Tile(Resource resource, int number) {
        this.resource = resource;
        this.number = number;
    }

    public void initNodes(){
        nodes = new Node[]{northeastNode, northNode, southeastNode, southwestNode, southNode, northwestNode};
    }

    public void initEdges(){
        edges = new Edge[]{eastEdge, northeastEdge, southeastEdge, westEdge, southwestEdge, northwestEdge};
    }

    /**
     * Activates the giveResource method for all buildings adjacent to this tile,
     * used by the maps dice rolled method. todo implement robber functionality.
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

    public int getNumber() {
        return number;
    }

    public String getPosition(){
        return "q=" + q + " r=" + r;
    }

    public Edge getEastEdge() {
        return eastEdge;
    }

    void setEastEdge(Edge eastEdge) {
        this.eastEdge = eastEdge;
    }

    public Edge getNortheastEdge() {
        return northeastEdge;
    }

    void setNortheastEdge(Edge northeastEdge) {
        this.northeastEdge = northeastEdge;
    }

    public Edge getNorthwestEdge() {
        return northwestEdge;
    }

    void setNorthwestEdge(Edge northwestEdge) {
        this.northwestEdge = northwestEdge;
    }

    public Edge getSoutheastEdge() {
        return southeastEdge;
    }

    void setSoutheastEdge(Edge southeastEdge) {
        this.southeastEdge = southeastEdge;
    }

    public Edge getWestEdge() {
        return westEdge;
    }

    void setWestEdge(Edge westEdge) {
        this.westEdge = westEdge;
    }

    public Edge getSouthwestEdge() {
        return southwestEdge;
    }

    void setSouthwestEdge(Edge southwestEdge) {
        this.southwestEdge = southwestEdge;
    }

    public Edge[] getEdges() {
        return edges;
    }

    public Node getNorthNode() {
        return northNode;
    }

    void setNorthNode(Node northNode) {
        this.northNode = northNode;
    }

    public Node getNortheastNode() {
        return northeastNode;
    }

    void setNortheastNode(Node northeastNode) {
        this.northeastNode = northeastNode;
    }

    public Node getSoutheastNode() {
        return southeastNode;
    }

    void setSoutheastNode(Node southeastNode) {
        this.southeastNode = southeastNode;
    }

    public Node getSouthNode() {
        return southNode;
    }

    void setSouthNode(Node southNode) {
        this.southNode = southNode;
    }

    public Node getSouthwestNode() {
        return southwestNode;
    }

    void setSouthwestNode(Node southwestNode) {
        this.southwestNode = southwestNode;
    }

    public Node getNorthwestNode() {
        return northwestNode;
    }

    void setNorthwestNode(Node northwestNode) {
        this.northwestNode = northwestNode;
    }

    public Node[] getNodes() {
        return nodes;
    }

    public Resource getResource() {
        return resource;
    }

    public int getQ() {
        return q;
    }

    void setQ(int q) {
        this.q = q;
    }

    public int getR() {
        return r;
    }

    void setR(int r) {
        this.r = r;
    }
}
