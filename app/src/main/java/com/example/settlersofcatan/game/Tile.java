package com.example.settlersofcatan.game;

/**
 * Class to represent a tile on the board, has hexagonal shape and as  such 6 nodes and 6 edges.
 * It furthermore has a resource it belongs to. If its resource is null, it is desert.
 */
public class Tile {

    Edge eastEdge;
    Edge northeastEdge;
    Edge northwestEdge;
    Edge southeastEdge;
    Edge westEdge;
    Edge southwestEdge;

    Edge[] edges;

     Node northNode;
     Node northeastNode;
     Node southeastNode;
     Node southNode;
     Node southwestNode;
     Node northwestNode;

    Node[] nodes;

    private Resource resource;

    int q;
    int r;

    private int number;

    public Tile() {
        this.resource = null;
        this.number = -1;
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
}
