package com.example.settlersofcatan.server_client.networking.dto;


public class TradeReplyMessage extends BaseMessage {
    public boolean acceptedTrade;


    public TradeReplyMessage(){}

    public TradeReplyMessage(Boolean acceptedTrade) {
        this.acceptedTrade = acceptedTrade;
    }

    @Override
    public String toString() {
        return String.valueOf(acceptedTrade);
    }
}
