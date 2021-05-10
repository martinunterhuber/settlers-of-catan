package com.example.settlersofcatan.game;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ResourceMap {

    private Map<Resource, Integer> resources;

    public ResourceMap() {
        resources = new HashMap<>();
        for (Resource resource : Resource.values()){
            resources.put(resource, 10);
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
