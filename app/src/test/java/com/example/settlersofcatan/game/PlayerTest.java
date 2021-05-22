package com.example.settlersofcatan.game;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
    Player player;

    @Before
    public void init() {
        player = new Player(0, "Test");
        player.setResources(new ResourceMap(new int[]{2,2,2,2,2}));
    }

    @Test
    public void testTakeResource(){
        player.takeResource(Resource.SHEEP, 1);
        Assert.assertEquals(1, player.getResourceCount(Resource.SHEEP));
    }

    @Test
    public void testHasResourcesTrue(){
        player.takeResource(Resource.SHEEP, 1);
        Assert.assertTrue(player.hasResources(new ResourceMap(new int[]{1,1,0,0,1})));
    }

    @Test
    public void testHasResourcesFalse(){
        player.takeResource(Resource.SHEEP, 1);
        Assert.assertFalse(player.hasResources(new ResourceMap(new int[]{3, 0, 1, 2, 1})));
    }
}
