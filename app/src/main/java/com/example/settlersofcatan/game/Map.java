package com.example.settlersofcatan.game;

import java.util.ArrayList;

/**
 * Class to represent a map/board for a game, consists of tiles and currently spans them on a two
 * dimensional array.
 */
public class Map {
    /**
     * This attribute is the actual map, with the center of the map being 0,0. It works under the
     * assumption that there are no more than 19 tiles, just like in the standard map for catan.
     */
    private Tile[][] map = new Tile[19][19];

    /**
     * This Tile ArrayList array indicates which number the tiles are assigned to, with the indices
     * being the numbers from 2-12. So all tiles which have 8 assigned to them are in the ArrayList
     * at the 8. index.
     */
    private ArrayList<Tile>[] numbers;

    /**
     * Method which takes a number which was rolled and notifies the tiles with the corresponding
     * number.
     * @param num number that was rolled
     */
    public void diceRolled(int num) {
        for (Tile t : numbers[num]) {
            t.giveResource();
        }
    }

}
