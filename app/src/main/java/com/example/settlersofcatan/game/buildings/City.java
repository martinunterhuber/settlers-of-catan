package com.example.settlersofcatan.game.buildings;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.Resource;
import com.example.settlersofcatan.game.resources.ResourceMap;
import com.example.settlersofcatan.game.board.Node;

/**
 * City building class, can be placed on a node and thus extends NodePlaceable.
 * Implements the giveResource method to give two copies of the resource to the owning player.
 */
public class City extends NodePlaceable {
    public static final ResourceMap costs = new ResourceMap(new int[]{0, 0, 2, 0, 3});

    private City() {
        this(null, null);
    }

    public City(Player player, Node location) {
        super(player, location);
    }

    @Override
    public void giveResource(Resource resource) {
        player.giveResources(resource, 2);
    }
}
