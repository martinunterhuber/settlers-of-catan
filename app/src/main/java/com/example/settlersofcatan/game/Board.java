package com.example.settlersofcatan.game;

import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Related resources for the hexagon layout:
 * https://www.redblobgames.com/grids/hexagons/#map-storage
 * http://www-cs-students.stanford.edu/~amitp/game-programming/grids/#relationships
 */
public class Board {
    /**
     * 2D Array where the indexes are like this https://i.imgur.com/HJV0ipI.png
     */
    private Tile[][] tiles;

    /**
     * 1D Array for iterating over all tiles
     */
    private Tile[] packedTiles;


    public Board(){

    }

    public void init(){
        initializeTiles();
        initializeEdges();
        initializeNodes();
        initializeEdgeEndpoints();
    }

    private void initializeEdgeEndpoints(){
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (inRange(i, j)) {
                    Tile tile = tiles[i][j];
                    tile.getNorthwestEdge().setEndpoints(tile.getNorthNode(), tile.getNorthwestNode());
                    tile.getWestEdge().setEndpoints(tile.getNorthwestNode(), tile.getSouthwestNode());
                    tile.getSouthwestEdge().setEndpoints(tile.getSouthNode(), tile.getSouthwestNode());
                    tile.getSoutheastEdge().setEndpoints(tile.getSouthNode(), tile.getSoutheastNode());
                    tile.getEastEdge().setEndpoints(tile.getNortheastNode(), tile.getSoutheastNode());
                    tile.getNortheastEdge().setEndpoints(tile.getNortheastNode(), tile.getNorthNode());
                }
            }
        }
    }

    private void initializeTiles(){
        tiles = new Tile[5][5];
        packedTiles = new Tile[19];
        int packedTileIndex = 0;

        List<Integer> numbers = new ArrayList<>(Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12));
        Collections.shuffle(numbers);

        int[] resourceDistribution = {3, 3, 4, 4, 4};

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (inRange(i, j)){
                    if (isDesertTile(i, j)){
                        // For now we will use 'null' for the desert tile
                        tiles[i][j] = new Tile(null, 7);
                    } else {
                        int resourceIndex;
                        do {
                            resourceIndex = Game.random.nextInt(5);
                        } while(resourceDistribution[resourceIndex] <= 0);
                        resourceDistribution[resourceIndex]--;
                        tiles[i][j] = new Tile(Resource.valueOf(resourceIndex), numbers.remove(0));
                    }

                    tiles[i][j].setQ(i);
                    tiles[i][j].setR(j);

                    packedTiles[packedTileIndex++] = tiles[i][j];
                }
            }
        }
    }

    private boolean isDesertTile(int i, int j){
        return i == 2 && j == 2;
    }

    private void initializeEdges(){
        // Initialization of the edges (without duplicates)
        // For each Tile create the northeast, east and southeast Edges.
        // If it exists connect the southwest, west, and northwest Edges else create them.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (inRange(i, j)){
                    Edge ne = new Edge();
                    Edge e = new Edge();
                    Edge se = new Edge();
                    ne.addAdjacentTile(tiles[i][j]);
                    e.addAdjacentTile(tiles[i][j]);
                    se.addAdjacentTile(tiles[i][j]);
                    tiles[i][j].setNortheastEdge(ne);
                    tiles[i][j].setEastEdge(e);
                    tiles[i][j].setSoutheastEdge(se);

                    if (inRange(i - 1, j)){
                        tiles[i - 1][j].getEastEdge().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setWestEdge(tiles[i - 1][j].getEastEdge());
                    } else {
                        Edge w = new Edge();
                        w.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setWestEdge(w);
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].getSoutheastEdge().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNorthwestEdge(tiles[i][j - 1].getSoutheastEdge());
                    } else {
                        Edge nw = new Edge();
                        nw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNorthwestEdge(nw);
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].getNortheastEdge().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSouthwestEdge(tiles[i - 1][j + 1].getNortheastEdge());
                    } else {
                        Edge sw = new Edge();
                        sw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSouthwestEdge(sw);
                    }
                    tiles[i][j].initEdges();
                }
            }
        }
    }

    private void initializeNodes(){
        // For each Tile create the north and south Nodes.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (inRange(i, j)){
                    Node n = new Node();
                    Node s = new Node();
                    tiles[i][j].setNorthNode(n);
                    tiles[i][j].setSouthNode(s);
                    n.addAdjacentTile(tiles[i][j]);
                    s.addAdjacentTile(tiles[i][j]);
                }
            }
        }

        // If it exists connect the northeast, southeast, southwest, and northwest Edges else create them.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (inRange(i, j)){
                    if (inRange(i + 1, j - 1)){
                        tiles[i + 1][j - 1].getSouthNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNortheastNode(tiles[i + 1][j - 1].getSouthNode());
                    } else {
                        Node ne = new Node();
                        ne.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNortheastNode(ne);
                    }

                    if (inRange(i, j + 1)){
                        tiles[i][j + 1].getNorthNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSoutheastNode(tiles[i][j + 1].getNorthNode());
                    } else {
                        Node se = new Node();
                        se.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSoutheastNode(se);
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].getNorthNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSouthwestNode(tiles[i - 1][j + 1].getNorthNode());
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].getSoutheastNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSouthwestNode(tiles[i - 1][j].getSoutheastNode());
                    } else {
                        Node sw = new Node();
                        sw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setSouthwestNode(sw);
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].getSouthNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNorthwestNode(tiles[i][j - 1].getSouthNode());
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].getNortheastNode().addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNorthwestNode(tiles[i - 1][j].getNortheastNode());
                    } else {
                        Node nw = new Node();
                        nw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].setNorthwestNode(nw);
                    }
                    tiles[i][j].initNodes();
                }
            }
        }
    }

    private static boolean inRange(int i, int j){
        return i < 5 && i >= 0 && j < 5 && j >= 0 && i + j >= 2 && i + j <= 6;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public Tile[] getPackedTiles() {
        return packedTiles;
    }

    /**
     * Method which takes a number which was rolled and notifies the tiles with the corresponding
     * number.
     * @param rolledNumber number that was rolled
     */
    public void distributeResources(int rolledNumber) {
        for (Tile t : packedTiles) {
            if (t.getNumber() == rolledNumber){
                t.giveResource();
            }
        }
    }
}
