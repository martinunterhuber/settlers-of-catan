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
        board.init();
        tiles = board.getTiles();
    }

    @Test
    public void testEdgeAdjacency(){
        Assert.assertEquals(tiles[3][2].getWestEdge(), tiles[2][2].getEastEdge());
    }

    @Test
    public void testNodeAdjacency(){
        Assert.assertEquals(tiles[3][2].getNorthwestNode(), tiles[2][2].getNortheastNode());
    }

    @Test
    public void testEdgeEndpoints(){
        Assert.assertEquals(
                tiles[3][2].getNortheastEdge().getEndpointNodes(),
                new HashSet<>(Arrays.asList(tiles[3][2].getNortheastNode(), tiles[3][2].getNorthNode()))
        );
    }

    @Test
    public void testNodeOutgoingEdges(){
        Assert.assertEquals(
                tiles[3][2].getSoutheastNode().getOutgoingEdges(),
                new HashSet<>(Arrays.asList(tiles[3][2].getSoutheastEdge(), tiles[3][2].getEastEdge(), tiles[3][3].getNortheastEdge()))
        );
    }

    @Test
    public void testNodeOutgoingEdgesOnlyTwo(){
        Assert.assertEquals(
                tiles[2][0].getNorthwestNode().getOutgoingEdges(),
                new HashSet<>(Arrays.asList(tiles[2][0].getWestEdge(), tiles[2][0].getNorthwestEdge()))
        );
    }

    @Test
    public void testEdgeAdjacentTiles(){
        Assert.assertEquals(
                tiles[3][2].getWestEdge().getAdjacentTiles(),
                new HashSet<>(Arrays.asList(tiles[2][2], tiles[3][2]))
        );
    }

    @Test
    public void testEdgeAdjacentTilesOnlyOne(){
        Assert.assertEquals(
                tiles[2][4].getSoutheastEdge().getAdjacentTiles(),
                new HashSet<>(Collections.singletonList(tiles[2][4]))
        );
    }

    @Test
    public void testNodeAdjacentTiles(){
        Assert.assertEquals(
                tiles[3][2].getSouthNode().getAdjacentTiles(),
                new HashSet<>(Arrays.asList(tiles[3][2], tiles[2][3], tiles[3][3]))
        );
    }

    @Test
    public void testNodeAdjacentTilesOnlyOne(){
        Assert.assertEquals(
                tiles[0][4].getSouthwestNode().getAdjacentTiles(),
                new HashSet<>(Collections.singletonList(tiles[0][4]))
        );
    }
}
