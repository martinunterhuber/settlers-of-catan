package com.example.settlersofcatan.game;

/**
 * Class to represent a road. Always belongs to a player.
 */
public class Road extends Placeable {

    private Edge location;

    public Road(){
        super(null);
        location = null;
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
