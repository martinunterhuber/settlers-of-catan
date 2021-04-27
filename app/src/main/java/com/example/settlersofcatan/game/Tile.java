package com.example.settlersofcatan.game;

/**
 * Class to represent a tile on the board, has hexagonal shape and as  such 6 nodes and 6 edges.
 * It furthermore has a resource it belongs to. If its resource is null, it is desert.
 */
public class Tile {

    private Edge northEdge;
    private Edge northeastEdge;
    private Edge southeastEdge;
    private Edge southEdge;
    private Edge southwestEdge;
    private Edge northwestEdge;

    private Edge[] edges = new Edge[]{northEdge, northeastEdge, southeastEdge, southEdge, southwestEdge, northwestEdge};

    private Node northeastNode;
    private Node eastNode;
    private Node southeastNode;
    private Node southwestNode;
    private Node westNode;
    private Node northwestNode;

    private Node[] nodes = new Node[]{northeastNode, eastNode, southeastNode, southwestNode, westNode, northwestNode};

    private Resource resource;

    public Tile(Resource resource) {
        this.resource = resource;
    }

    /**
     * Activates the giveResource method for all buildings adjacent to this tile,
     * used by the maps dice rolled method. todo implement robber functionality.
     */
    public void giveResource() {
        for (Node n : nodes) {
            if (n.getBuilding() != null) {
                n.getBuilding().giveResource(resource);
            }
        }
    }
}
