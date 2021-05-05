package com.example.settlersofcatan.game;

import java.util.ArrayList;

/**
 * Player of the game, has an inventory of resource cards, development cards, placed cities,
 * settlements and roads todo other player attributes like their colour etc. and maximum on available cities/settlements/roads
 */
public class Player {
    private String name;
    private int id;

    private ResourceMap resources;
    private ArrayList<DevelopmentCard> unrevealedDevelopmentCards;
    private ArrayList<Settlement> settlements;
    private ArrayList<City> cities;
    private ArrayList<Road> roads;

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public void giveSingleResource(Resource resource) {
        resources.incrementResourceCount(resource, 1);
    }
}
