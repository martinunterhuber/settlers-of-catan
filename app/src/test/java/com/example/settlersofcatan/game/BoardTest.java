package com.example.settlersofcatan.game;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

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
    public void testNodeOutgoingEdgesOnlyTwo(){
        Assert.assertEquals(
                tiles[2][0].northwestNode.outgoingEdges,
                new HashSet<>(Arrays.asList(tiles[2][0].westEdge, tiles[2][0].northwestEdge))
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
    public void testEdgeAdjacentTilesOnlyOne(){
        Assert.assertEquals(
                tiles[2][4].southeastEdge.adjacentTiles,
                new HashSet<>(Collections.singletonList(tiles[2][4]))
        );
    }

    @Test
    public void testNodeAdjacentTiles(){
        Assert.assertEquals(
                tiles[3][2].southNode.adjacentTiles,
                new HashSet<>(Arrays.asList(tiles[3][2], tiles[2][3], tiles[3][3]))
        );
    }

    @Test
    public void testNodeAdjacentTilesOnlyOne(){
        Assert.assertEquals(
                tiles[0][4].southwestNode.adjacentTiles,
                new HashSet<>(Collections.singletonList(tiles[0][4]))
        );
    }
}
