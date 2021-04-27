package com.example.settlersofcatan.game;

/**
 * ResourceCard class, always in the inventory of a player.
 * Has a resource which it belongs to.
 */
public class ResourceCard {

    private Resource resource;

    public ResourceCard(Resource resource) {
        this.resource = resource;
    }

    public Resource getResource() {
        return resource;
    }
}
