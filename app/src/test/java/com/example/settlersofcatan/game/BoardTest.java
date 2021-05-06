package com.example.settlersofcatan.game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BoardTest {
    private Tile[][] tiles;

    @Before
    public void init(){
        Board board = new Board();
        tiles = board.getTiles();
    }

    @Test
    public void testEdgeAdjacency(){
        Assert.assertEquals(tiles[3][2].westEdge, tiles[2][2].eastEdge);
    }

    @Test
    public void testNodeAdjacency(){
        Assert.assertEquals(tiles[3][2].northwestNode, tiles[2][2].northeastNode);
    }

    @Test
    public void testEdgeEndpoints(){
        Assert.assertEquals(
                tiles[3][2].northeastEdge.endpointNodes,
                new HashSet<>(Arrays.asList(tiles[3][2].northeastNode, tiles[3][2].northNode))
        );
    }

    @Test
    public void testNodeOutgoingEdges(){
        Assert.assertEquals(
                tiles[3][2].southeastNode.outgoingEdges,
                new HashSet<>(Arrays.asList(tiles[3][2].southeastEdge, tiles[3][2].eastEdge, tiles[3][3].northeastEdge))
        );
    }

    @Test
    public void testEdgeAdjacentTiles(){
        Assert.assertEquals(
                tiles[3][2].westEdge.adjacentTiles,
                new HashSet<>(Arrays.asList(tiles[2][2], tiles[3][2]))
        );
    }

    @Test
    public void testNodeAdjacentTiles(){
        Assert.assertEquals(
                tiles[3][2].southNode.adjacentTiles,
                new HashSet<>(Arrays.asList(tiles[3][2], tiles[2][3], tiles[3][3]))
        );
    }
}
