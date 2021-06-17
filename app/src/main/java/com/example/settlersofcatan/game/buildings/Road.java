package com.example.settlersofcatan.game.buildings;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.resources.ResourceMap;
import com.example.settlersofcatan.game.board.Edge;

/**
 * Class to represent a road. Always belongs to a player.
 */
public class Road extends Placeable {
    public static final ResourceMap costs = new ResourceMap(new int[]{0, 1, 0, 1, 0});

    private Edge location;

    private Road(){
        this(null, null);
    }

    public Road(Player player) {
        super(player);
    }

    public Road(Player player, Edge location) {
        super(player);
        this.location = location;
    }

    public Edge getLocation() {
        return location;
    }
}
