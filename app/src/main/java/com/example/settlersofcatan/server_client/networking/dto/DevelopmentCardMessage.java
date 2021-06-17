package com.example.settlersofcatan.server_client.networking.dto;

import com.example.settlersofcatan.game.development_cards.DevelopmentCardDeck;

public class DevelopmentCardMessage extends BaseMessage{
    public DevelopmentCardDeck deck;

    public DevelopmentCardMessage() {
    }

    public DevelopmentCardMessage(DevelopmentCardDeck deck) {
        this.deck = deck;
    }

    @Override
    public String toString() {
        return deck.toString();
    }
}
