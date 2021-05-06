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

    private Tile[] tiles;
    private Node[] nodes;
    private Edge[] edges;

    public static final int[] defaultResourceDistribution = {3, 3, 4, 4, 4};

    public Board(){
        tiles = new Tile[19];
        nodes = new Node[72];
        edges = new Edge[72];

        // for now we'll use null for desert; maybe change this
        tiles[0] = new Tile(null, 7);

        distributeTileResources();
    }

    private void distributeTileResources(){
        List<Integer> numbers = Arrays.asList(2, 3, 3, 4, 4, 5, 5, 6, 6, 8, 8, 9, 9, 10, 10, 11, 11, 12);
        Collections.shuffle(numbers);

        int[] resourceDistributionCopy = defaultResourceDistribution.clone();

        Random rand = new Random();
        for (int i = 1; i < 19; i++) {
            int resourceIndex;
            do {
                resourceIndex = rand.nextInt();
            } while(resourceDistributionCopy[resourceIndex] <= 0);
            resourceDistributionCopy[resourceIndex]--;
            tiles[i] = new Tile(Resource.valueOf(resourceIndex), numbers.remove(0));
        }
    }

    /**
     * Method which takes a number which was rolled and notifies the tiles with the corresponding
     * number.
     * @param rolledNumber number that was rolled
     */
    public void distributeResources(int rolledNumber) {
        for (Tile t : tiles) {
            if (t.getNumber() == rolledNumber){
                t.giveResource();
            }
        }
    }
}
