package com.example.settlersofcatan.game;

/**
 * Class to represent a harbor, has a specific resource which it trades, if none is given the harbor
 * is a 3 : 1 harbor
 */
public class Harbor {
    private Resource resource;

    /**
     * For 2 : 1 harbors
     * @param resource the resource of the harbor
     */
    public Harbor (Resource resource) {
        this.resource = resource;
    }

    /**
     * for 3 : 1 harbors
     */
    public Harbor () {

    }
}
