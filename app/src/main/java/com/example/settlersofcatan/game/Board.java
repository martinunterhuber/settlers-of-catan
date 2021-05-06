package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

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
                    ne.adjacent1 = tiles[i][j];
                    e.adjacent1 = tiles[i][j];
                    se.adjacent1 = tiles[i][j];
                    tiles[i][j].northeastEdge = ne;
                    tiles[i][j].eastEdge = e;
                    tiles[i][j].southeastEdge = se;

                    if (inRange(i - 1, j)){
                        tiles[i - 1][j].eastEdge.adjacent2 = tiles[i][j];
                        tiles[i][j].westEdge = tiles[i - 1][j].eastEdge;
                    } else {
                        Edge w = new Edge();
                        w.adjacent1 = tiles[i][j];
                        tiles[i][j].westEdge = w;
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].southeastEdge.adjacent2 = tiles[i][j];
                        tiles[i][j].northwestEdge = tiles[i][j - 1].southeastEdge;
                    } else {
                        Edge nw = new Edge();
                        nw.adjacent1 = tiles[i][j];
                        tiles[i][j].northwestEdge = nw;
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].northeastEdge.adjacent2 = tiles[i][j];
                        tiles[i][j].southwestEdge = tiles[i - 1][j + 1].northeastEdge;
                    } else {
                        Edge sw = new Edge();
                        sw.adjacent1 = tiles[i][j];
                        tiles[i][j].southwestEdge = sw;
                    }
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
                    n.adjacent1 = tiles[i][j];
                    s.adjacent1 = tiles[i][j];
                }
            }
        }

        // If it exists connect the northeast, southeast, southwest, and northwest Edges else create them.
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                if (i + j >= 2 && i + j <= 6){
                    if (inRange(i + 1, j - 1)){
                        tiles[i + 1][j - 1].southNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].northeastNode = tiles[i + 1][j - 1].southNode;
                    } else {
                        Node ne = new Node();
                        ne.adjacent1 = tiles[i][j];
                        tiles[i][j].northeastNode = ne;
                    }

                    if (inRange(i, j + 1)){
                        tiles[i][j + 1].northNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].southeastNode = tiles[i][j + 1].northNode;
                    } else {
                        Node w = new Node();
                        w.adjacent1 = tiles[i][j];
                        tiles[i][j].southeastNode = w;
                    }

                    if (inRange(i - 1, j + 1)){
                        tiles[i - 1][j + 1].northNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].southwestNode = tiles[i - 1][j + 1].northNode;
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].southeastNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].southwestNode = tiles[i - 1][j].southeastNode;
                    } else {
                        Node w = new Node();
                        w.adjacent1 = tiles[i][j];
                        tiles[i][j].southwestNode = w;
                    }

                    if (inRange(i, j - 1)){
                        tiles[i][j - 1].southNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].northwestNode = tiles[i][j - 1].southNode;
                    } else if (inRange(i - 1, j)){
                        tiles[i - 1][j].northeastNode.setFreeAdjacentNode(tiles[i][j]);
                        tiles[i][j].northwestNode = tiles[i - 1][j].northeastNode;
                    } else {
                        Node w = new Node();
                        w.adjacent1 = tiles[i][j];
                        tiles[i][j].northwestNode = w;
                    }
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
