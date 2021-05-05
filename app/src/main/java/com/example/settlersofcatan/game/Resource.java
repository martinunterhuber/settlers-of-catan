package com.example.settlersofcatan.game;

/**
 * basic enum for the available resources in the game
 */
public enum Resource {
    SHEEP(0), FOREST(1), WHEAT(2), CLAY(3), ORE(4);

    private int index;

    Resource(int index){
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public static Resource valueOf(int index){
        for (Resource resource : Resource.values()) {
            if (index == resource.index){
                return resource;
            }
        }
        throw new IllegalArgumentException("Resource does not exist!");
    }
}
