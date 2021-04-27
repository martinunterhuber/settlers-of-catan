package com.example.settlersofcatan.game;

import java.util.ArrayList;

/**
 * Player of the game, has an inventory of resource cards, development cards, placed cities,
 * settlements and roads todo other player attributes like their colour etc. and maximum on available cities/settlements/roads
 */
public class Player {

    private ArrayList<ResourceCard> resourceCards; //could be done in an array, may be subject to change
    private ArrayList<DevelopmentCard> unrevealedDevelopmentCards;
    private ArrayList<Settlement> settlements;
    private ArrayList<City> cities;
    private ArrayList<Road> roads;

    public void getResource(Resource resource) {
        resourceCards.add(new ResourceCard(resource));
    }

}
