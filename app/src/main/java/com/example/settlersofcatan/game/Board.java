package com.example.settlersofcatan.game;

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
                    tile.northwestEdge.setEndpoints(tile.northNode, tile.northwestNode);
                    tile.westEdge.setEndpoints(tile.northwestNode, tile.southwestNode);
                    tile.southwestEdge.setEndpoints(tile.southNode, tile.southwestNode);
                    tile.southeastEdge.setEndpoints(tile.southNode, tile.southeastNode);
                    tile.eastEdge.setEndpoints(tile.northeastNode, tile.southeastNode);
                    tile.northeastEdge.setEndpoints(tile.northeastNode, tile.northNode);
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

                    tiles[i][j].q = i;
                    tiles[i][j].r = j;

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
                if (i + j >= 2 && i + j <= 6){
                    Edge ne = new Edge();
                    Edge e = new Edge();
                    Edge se = new Edge();
                    ne.addAdjacentTile(tiles[i][j]);
                    e.addAdjacentTile(tiles[i][j]);
                    se.addAdjacentTile(tiles[i][j]);
                    tiles[i][j].northeastEdge = ne;
                    tiles[i][j].eastEdge = e;
                    tiles[i][j].southeastEdge = se;

                    if (inRange(i - 1, j)){
                        tiles[i - 1][j].eastEdge.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].westEdge = tiles[i - 1][j].eastEdge;
                    } else {
                        Edge w = new Edge();
                        w.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].westEdge = w;
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].southeastEdge.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northwestEdge = tiles[i][j - 1].southeastEdge;
                    } else {
                        Edge nw = new Edge();
                        nw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northwestEdge = nw;
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].northeastEdge.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southwestEdge = tiles[i - 1][j + 1].northeastEdge;
                    } else {
                        Edge sw = new Edge();
                        sw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southwestEdge = sw;
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
                if (i + j >= 2 && i + j <= 6){
                    Node n = new Node();
                    Node s = new Node();
                    tiles[i][j].northNode = n;
                    tiles[i][j].southNode = s;
                    n.addAdjacentTile(tiles[i][j]);
                    s.addAdjacentTile(tiles[i][j]);
                }
            }
        }

        // If it exists connect the northeast, southeast, southwest, and northwest Edges else create them.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j >= 2 && i + j <= 6){
                    if (inRange(i + 1, j - 1)){
                        tiles[i + 1][j - 1].southNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northeastNode = tiles[i + 1][j - 1].southNode;
                    } else {
                        Node ne = new Node();
                        ne.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northeastNode = ne;
                    }

                    if (inRange(i, j + 1)){
                        tiles[i][j + 1].northNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southeastNode = tiles[i][j + 1].northNode;
                    } else {
                        Node se = new Node();
                        se.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southeastNode = se;
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].northNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southwestNode = tiles[i - 1][j + 1].northNode;
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].southeastNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southwestNode = tiles[i - 1][j].southeastNode;
                    } else {
                        Node sw = new Node();
                        sw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].southwestNode = sw;
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].southNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northwestNode = tiles[i][j - 1].southNode;
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].northeastNode.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northwestNode = tiles[i - 1][j].northeastNode;
                    } else {
                        Node nw = new Node();
                        nw.addAdjacentTile(tiles[i][j]);
                        tiles[i][j].northwestNode = nw;
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
