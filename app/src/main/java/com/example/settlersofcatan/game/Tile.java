package com.example.settlersofcatan.game;

/**
 * Class to represent a tile on the board, has hexagonal shape and as  such 6 nodes and 6 edges.
 * It furthermore has a resource it belongs to. If its resource is null, it is desert.
 */
public class Tile {

    Edge northEdge;
    Edge northeastEdge;
     Edge southeastEdge;
     Edge southEdge;
     Edge southwestEdge;
     Edge northwestEdge;

    private Edge[] edges = new Edge[]{northEdge, northeastEdge, southeastEdge, southEdge, southwestEdge, northwestEdge};

     Node northeastNode;
     Node eastNode;
     Node southeastNode;
     Node southwestNode;
     Node westNode;
     Node northwestNode;

    private Node[] nodes = new Node[]{northeastNode, eastNode, southeastNode, southwestNode, westNode, northwestNode};

    private Resource resource;

    int u;
    int v;

    private int number;

    public Tile(Resource resource, int number) {
        this.resource = resource;
        this.number = number;
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
}
