package com.example.settlersofcatan.game;

import java.util.Random;

/**
 * Class to represent a map/board for a game, consists of tiles and currently spans them on a two
 * dimensional array.
 */
public class Board {
    /**
     * This attribute is the actual map, with the center of the map being 0,0. It works under the
     * assumption that there are no more than 19 tiles, just like in the standard map for catan.
     */
    private Tile[] tiles;

    public static final int[] resourceDistribution = {3, 3, 4, 4, 4};

    public Board(){
        tiles = new Tile[19];

        // for now we'll use null for desert; maybe change this
        tiles[0] = new Tile(null);

        distributeTileResources();
    }

    private void distributeTileResources(){
        Random rand = new Random();
        int[] resourceDistributionCopy = resourceDistribution.clone();
        for (int i = 1; i < 19; i++) {
            int resourceIndex;
            do {
                resourceIndex = rand.nextInt();
            } while(resourceDistributionCopy[resourceIndex] < 0);
            resourceDistributionCopy[resourceIndex]--;
            tiles[i] = new Tile(Resource.valueOf(resourceIndex));
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
