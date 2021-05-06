package com.example.settlersofcatan.game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class BoardTest {
    private Tile[][] tiles;

    @Before
    public void init(){
        Board board = new Board();
        tiles = board.getTiles();
    }

    @Test
    public void testTileAdjacency(){
        Assert.assertEquals(tiles[3][2].westEdge, tiles[2][2].eastEdge);
    }

    @Test
    public void testNodeAdjacency(){
        Assert.assertEquals(tiles[3][2].northwestNode, tiles[2][2].northeastNode);
    }
}
