package com.example.settlersofcatan.game;

import java.util.HashSet;
import java.util.Set;

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

    private int victoryPoints;

    private ResourceMap resources;
    private HashSet<DevelopmentCard> unrevealedDevelopmentCards = new HashSet<>();
    private HashSet<Settlement> settlements = new HashSet<>();
    private HashSet<City> cities = new HashSet<>();
    private HashSet<Road> roads = new HashSet<>();
    private HashSet<Harbor> harborsSettledOn;

    private HashSet<Edge> potentialRoadPlacements = new HashSet<>();
    private HashSet<Node> potentialSettlementPlacements = new HashSet<>();
    private HashSet<Node> potentialCityPlacements = new HashSet<>();

    private Player(){
        this(-1, null);
    }

    public Player(int id, String name){
        this.id = id;
        this.name = name;
        this.resources = new ResourceMap();
        this.victoryPoints = 0;
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

    public int getVictoryPoints() {
        return victoryPoints;
    }

    // Calculation in getters subject to change, just there to guarantee authenticity
    public HashSet<Edge> getPotentialRoadPlacements() {
        calculatePotentialRoadPlacements();
        return potentialRoadPlacements;
    }

    public HashSet<Node> getPotentialSettlementPlacements() {
        calculatePotentialSettlementPlacements();
        return potentialSettlementPlacements;
    }

    public HashSet<Node> getPotentialCityPlacements() {
        calculatePotentialCityPlacements();
        return potentialCityPlacements;
    }

    private void calculatePotentialRoadPlacements() {
        potentialRoadPlacements.clear();
        Set<Edge> correspondingEdges = new HashSet<>();
        for (Road r : roads) {
            correspondingEdges.add(r.getLocation());
        }
        for (Edge correspondingEdge : correspondingEdges) {
            for (Node n : correspondingEdge.getEndpointNodes()){
                if (n.getBuilding() != null && n.getBuilding().getPlayer() != this) {
                    break;
                }
                Set<Edge> potentialCandidates = n.getOtherOutgoingEdges(correspondingEdge);
                for (Edge potentialCandidate : potentialCandidates) {
                    if (potentialCandidate.getRoad() == null) {
                        potentialRoadPlacements.add(potentialCandidate);
                    }
                }
            }
        }

        calculatePotentialRoadsFromBuildings();
    }

    private void calculatePotentialRoadsFromBuildings(){
        Set<NodePlaceable> buildings = new HashSet<>();
        buildings.addAll(settlements);
        buildings.addAll(cities);
        for (NodePlaceable building : buildings){
            Node node = building.getLocation();
            for (Edge edge : node.getOutgoingEdges()){
                if (edge.getRoad() == null){
                    potentialRoadPlacements.add(edge);
                }
            }
        }
    }

    // Does not take into account the settlement placement at the start of the game
    private void calculatePotentialSettlementPlacements() {
        potentialSettlementPlacements.clear();
        Set<Node> allAccessibleNodes = new HashSet<>();
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

    public boolean canPlaceRoadOn(Edge edge) {
        calculatePotentialRoadPlacements();
        return roads.size() < ROAD_COUNT
                 && potentialRoadPlacements.contains(edge);
    }

    public boolean canPlaceSettlementOn(Node node) {
        calculatePotentialSettlementPlacements();
        return settlements.size() < SETTLEMENT_COUNT
                && potentialSettlementPlacements.contains(node);
    }

    public boolean canPlaceCityOn(Node node) {
        calculatePotentialCityPlacements();
        return cities.size() < CITY_COUNT
                && potentialCityPlacements.contains(node);
    }

    public boolean hasResources(ResourceMap costs){
        for (Resource resource : Resource.values()){
            if (getResourceCount(resource) < costs.getResourceCount(resource)){
                return false;
            }
        }
        return true;
    }

    public void takeResources(ResourceMap costs){
        for (Resource resource : Resource.values()){
            takeResource(resource, costs.getResourceCount(resource));
        }
    }

    public int longestRoad(){
        int maxLength = 0;
        for (Road road : roads){
            for (Node endpoint : road.getLocation().getEndpointNodes()){
                maxLength = Math.max(maxLength, longestRoadRecursive(road, endpoint, new HashSet<>()));
            }
        }
        return maxLength;
    }

    private int longestRoadRecursive(Road current, Node via, Set<Road> visited){
        if (visited.contains(current)){
            return 0;
        }
        visited.add(current);

        int maxLength = 0;
        Node node = current.getLocation().getOtherEndpoint(via);
        for (Edge edge : node.getOutgoingEdgesExcept(current.getLocation())){
            if (edge.hasPlayersRoad(this) && node.hasPlayersBuildingOrNone(this)){
                int roadLength = longestRoadRecursive(edge.getRoad(), node, new HashSet<>(visited));
                maxLength = Math.max(maxLength, roadLength);
            }
        }
        return maxLength + 1;
    }

    /**
     * Method to place a road, assumes that all the prerequisites have been met (resources available
     * road available, edge selected, edge connected, edge empty)
     * @param e the edge to place the road on
     */
    public void placeRoad(Edge e) {
        Road roadToPlace = new Road(this, e);
        roads.add(roadToPlace);
        e.setRoad(roadToPlace);
    }

    /**
     * Method to place a settlement, assumes that all the prerequisites have been met (resources available,
     * settlement available, node selected, node empty, no adjacent building)
     * @param n the node to place the settlement on
     */
    public void placeSettlement(Node n) {
        Settlement settlementToPlace = new Settlement(this, n);
        settlements.add(settlementToPlace);
        n.setBuilding(settlementToPlace);
        victoryPoints++;

        if (n.isAdjacentToHarbor()) {
            harborsSettledOn.add(n.getAdjacentHarbor());
        }
    }

    /**
     * Method to place a City, assumes that all the prerequisites have been met (resources available, city available,
     * node selected, node has settlement)
     * @param n the node to place the city on
     */
    public void placeCity(Node n) {
        City cityToPlace = new City(this, n);
        cities.add(cityToPlace);
        settlements.remove((Settlement) n.getBuilding());
        n.setBuilding(cityToPlace);
        victoryPoints++;
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

    void addVictoryPoints(int victoryPoints){
        this.victoryPoints += victoryPoints;
    }
}
