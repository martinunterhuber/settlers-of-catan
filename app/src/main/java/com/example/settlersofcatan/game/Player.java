package com.example.settlersofcatan.game;

import java.util.ArrayList;
import java.util.List;

/**
 * Player of the game, has an inventory of resource cards, development cards, placed cities,
 * settlements and roads todo other player attributes like their colour etc. and maximum on available cities/settlements/roads
 */
public class Player {

    private static final int ROAD_COUNT = 15;
    private static final int SETTLEMENT_COUNT = 5;
    private static final int CITY_COUNT = 4;

    private String name;
    private int id;

    private ResourceMap resources;
    private ArrayList<DevelopmentCard> unrevealedDevelopmentCards;
    private ArrayList<Settlement> settlements;
    private ArrayList<City> cities;
    private ArrayList<Road> roads;
    private ArrayList<Harbor> harborsSettledOn;

    private ArrayList<Edge> potentialRoadPlacements;
    private ArrayList<Node> potentialSettlementPlacements;
    private ArrayList<Node> potentialCityPlacements;

    private Player(){
        this(-1, null);
    }

    public Player(int id, String name){
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void giveSingleResource(Resource resource) {
        resources.incrementResourceCount(resource, 1);
    }

    public int getResourceCount(Resource resource) {
        return resources.getResourceCount(resource);
    }

    public void takeResource(Resource resource, int count) {
        resources.decrementResourceCount(resource, count);
    }

    public int getId() {
        return id;
    }

    // Calculation in getters subject to change, just there to guarantee authenticity
    public ArrayList<Edge> getPotentialRoadPlacements() {
        calculatePotentialRoadPlacements();
        return potentialRoadPlacements;
    }

    public ArrayList<Node> getPotentialSettlementPlacements() {
        calculatePotentialSettlementPlacements();
        return potentialSettlementPlacements;
    }

    public ArrayList<Node> getPotentialCityPlacements() {
        calculatePotentialCityPlacements();
        return potentialCityPlacements;
    }
    // Does not take into account the Road Placement during the start of the game.
    private void calculatePotentialRoadPlacements() {
        potentialRoadPlacements.clear();
        List<Edge> correspondingEdges = new ArrayList<>();
        for (Road r : roads) {
            correspondingEdges.add(r.getLocation());
        }
        for (Edge correspondingEdge : correspondingEdges) {
            for (Node n : correspondingEdge.getEndpointNodes()){
                if (n.getBuilding() != null && n.getBuilding().getPlayer() != this) {
                    break;
                }
                List<Edge> potentialCandidates = n.getOtherOutgoingEdges(correspondingEdge);
                for (Edge potentialCandidate : potentialCandidates) {
                    if (potentialCandidate.getRoad() == null) {
                        potentialRoadPlacements.add(potentialCandidate);
                    }
                }
            }
        }
    }

    // Does not take into account the settlement placement at the start of the game
    private void calculatePotentialSettlementPlacements() {
        potentialSettlementPlacements.clear();
        List<Node> allAccessibleNodes = new ArrayList<>();
        for (Road r : roads) {
            allAccessibleNodes.addAll(r.getLocation().getEndpointNodes());
        }
        for (Node n : allAccessibleNodes) {
            if (n.getBuilding() == null && n.hasNoAdjacentBuildings()) {
                potentialSettlementPlacements.add(n);
            }
        }
    }

    private void calculatePotentialCityPlacements() {
        potentialCityPlacements.clear();
        for (Settlement s : settlements) {
            potentialCityPlacements.add(s.getLocation());
        }
    }

    public boolean canPlayerPlaceRoad() {
        calculatePotentialRoadPlacements();
        boolean canAffordRoad = getResourceCount(Resource.FOREST) > 0 && getResourceCount(Resource.CLAY) > 0;
        return canAffordRoad && roads.size() < ROAD_COUNT && !potentialRoadPlacements.isEmpty();
    }

    public boolean canPlayerPlaceSettlement() {
        calculatePotentialSettlementPlacements();
        boolean canAffordSettlement = getResourceCount(Resource.FOREST) > 0 && getResourceCount(Resource.CLAY) > 0
                && getResourceCount(Resource.SHEEP) > 0 && getResourceCount(Resource.WHEAT) > 0;
        return canAffordSettlement && settlements.size() < SETTLEMENT_COUNT && !potentialSettlementPlacements.isEmpty();
    }

    public boolean canPlayerPlaceCity() {
        calculatePotentialCityPlacements();
        boolean canAffordCity = getResourceCount(Resource.WHEAT) > 2 && getResourceCount(Resource.ORE) > 3;
        return canAffordCity && cities.size() < CITY_COUNT && !potentialCityPlacements.isEmpty();
    }

    /**
     * Method to place a road, assumes that all the prerequisites have been met (resources available
     * road available, edge selected, edge connected, edge empty)
     * @param e the edge to place the road on
     */
    private void placeRoad(Edge e) {
        takeResource(Resource.FOREST, 1);
        takeResource(Resource.CLAY, 1);
        Road roadToPlace = new Road(this, e);
        roads.add(roadToPlace);
        e.setRoad(roadToPlace);
    }

    /**
     * Method to place a settlement, assumes that all the prerequisites have been met (resources available,
     * settlement available, node selected, node empty, no adjacent building)
     * @param n the node to place the settlement on
     */
    private void placeSettlement(Node n) {
        takeResource(Resource.FOREST, 1);
        takeResource(Resource.CLAY, 1);
        takeResource(Resource.WHEAT, 1);
        takeResource(Resource.SHEEP,1);
        Settlement settlementToPlace = new Settlement(this, n);
        settlements.add(settlementToPlace);
        n.setBuilding(settlementToPlace);
        //DISCLAIMER: Remember to add same functionality to the initialSettlementPlacement Method
        if (n.isAdjacentToHarbor()) {
            harborsSettledOn.add(n.getAdjacentHarbor());
        }
    }

    /**
     * Method to place a City, assumes that all the prerequisites have been met (resources available, city available,
     * node selected, node has settlement)
     * @param n the node to place the city on
     */
    private void placeCity(Node n) {
        takeResource(Resource.ORE, 3);
        takeResource(Resource.WHEAT, 2);
        City cityToPlace = new City(this, n);
        cities.add(cityToPlace);
        settlements.remove((Settlement) n.getBuilding());
        n.setBuilding(cityToPlace);
    }

    /**
     * Method to check if a player has the resources necessary to be able to accept a specific trade offer
     * @param tradeOffer the trade offer
     * @return true, if he can  afford it, false if not
     */
    public boolean isEligibleForTradeOffer(TradeOffer tradeOffer) {
        return resources.containsResourceMap(tradeOffer.getReceive());
    }

    /**
     * Method to execute a trade offer
     * DISCLAIMER: This should probably be moved to the game class and not the player class, so
     * that the resource movement can be facilitated on both sides of the offer more easily
     * @param tradeOffer the trade offer the player accepted
     */
    public void acceptTradeOffer(TradeOffer tradeOffer) {
        resources.decrementResourceMap(tradeOffer.getReceive());
        resources.incrementResourceMap(tradeOffer.getGive());
    }


}
