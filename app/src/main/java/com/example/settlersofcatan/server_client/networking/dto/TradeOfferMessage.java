package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.trade.TradeOffer;

public class TradeOfferMessage extends BaseMessage {
    public TradeOffer tradeOffer;


    public TradeOfferMessage(){}

    public TradeOfferMessage(TradeOffer tradeOffer) {
        this.tradeOffer = tradeOffer;
    }

    @Override
    public String toString() {
        return tradeOffer.toString();
    }
}
