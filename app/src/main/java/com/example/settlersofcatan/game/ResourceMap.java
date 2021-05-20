package com.example.settlersofcatan.game;

import android.content.res.Resources;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class ResourceMap {

    private Map<Resource, Integer> resources;

    public ResourceMap() {
        resources = new HashMap<>();
        for (Resource resource : Resource.values()){
            resources.put(resource, 5);
        }
    }

    public ResourceMap(int[] values) {
        resources = new HashMap<>();
        for (int i = 0; i < values.length; i++) {
            Resource resource = Resource.valueOf(i);
            resources.put(resource, values[i]);
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

    public void decrementResourceMap(ResourceMap resourceMap) {
        if (containsResourceMap(resourceMap)) {
            for (Resource resource : Resource.values()) {
                decrementResourceCount(resource, resourceMap.getResourceCount(resource));
            }
        }
    }

    public void incrementResourceMap(ResourceMap resourceMap) {
        for (Resource resource : Resource.values()) {
            incrementResourceCount(resource, resourceMap.getResourceCount(resource));
        }
    }

    public boolean containsResourceMap(ResourceMap resourceMap) {
        for (Resource resource : Resource.values()) {
            if (getResourceCount(resource) < resourceMap.getResourceCount(resource)) {
                return false;
            }
        }
        return true;
    }

    public static ResourceMap cloneResourceMap(ResourceMap resourceMapToCopy) {
        ResourceMap resourceMap = getEmptyResourceMap();
        for (Resource resource : Resource.values()) {
            resourceMap.incrementResourceCount(resource, resourceMapToCopy.getResourceCount(resource));
        }
        return resourceMap;
    }

    public static ResourceMap getEmptyResourceMap() {
        return new ResourceMap(new int[]{0, 0, 0, 0, 0});
    }

}
