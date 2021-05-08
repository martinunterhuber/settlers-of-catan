package com.example.settlersofcatan.game;

import java.util.EnumMap;

public class ResourceMap {

    private EnumMap<Resource, Integer> resources;

    public ResourceMap() {
        resources = new EnumMap<>(Resource.class);
        for (Resource resource : Resource.values()){
            resources.put(resource, 0);
        }
    }

    public Integer getResourceCount(Resource resource) {
        return resources.get(resource);
    }

    public void incrementResourceCount(Resource resource, int increment) {
        Integer count = resources.get(resource);
        resources.put(resource, count + increment);
    }

    public void decrementResourceCount(Resource resource, int decrement) {
        Integer count = resources.get(resource);
        resources.put(resource, count - decrement);
    }
}
