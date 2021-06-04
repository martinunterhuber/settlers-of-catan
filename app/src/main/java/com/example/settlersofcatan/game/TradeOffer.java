package com.example.settlersofcatan.game;

public class TradeOffer {
    private Player from;
    private Player to;

    private ResourceMap receive; // what the player making the trade offer wants to receive
    private ResourceMap give; // what the player making the trade offer is willing to give

    public TradeOffer(Player from, Player to) {
        this.from = from;
        this.to = to;
    }

    public void addResourceReceive(Resource resource, int number) {
        receive.incrementResourceCount(resource, number);
    }

    public void addResourceGive(Resource resource, int number) {
        give.incrementResourceCount(resource, number);
    }

    public void removeResourceReceive(Resource resource, int number) {
        receive.decrementResourceCount(resource, number);
    }

    public void removeResourceGive(Resource resource, int number) {
        give.decrementResourceCount(resource, number);
    }


    public Player getFrom() {
        return from;
    }

    public Player getTo() {
        return to;
    }

    public ResourceMap getReceive() {
        return receive;
    }

    public ResourceMap getGive() {
        return give;
    }

    public void setGive(ResourceMap give) {
        this.give = give;
    }

    public void setReceive(ResourceMap receive) {
        this.receive = receive;
    }
}
