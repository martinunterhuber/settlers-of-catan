package com.example.settlersofcatan.game;

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

    public Board(){
        tiles = new Tile[19];
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
