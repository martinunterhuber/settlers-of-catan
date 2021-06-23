package com.example.settlersofcatan.game.buildings;

import com.example.settlersofcatan.game.Player;
import com.example.settlersofcatan.game.board.Node;
import com.example.settlersofcatan.game.resources.Resource;

public abstract class NodePlaceable extends Placeable {
    protected Node location;

    protected NodePlaceable(Player player, Node location) {
        super(player);
        this.location = location;
    }

    public Node getLocation() {
        return location;
    }

    public abstract void giveResource(Resource resource);
}
