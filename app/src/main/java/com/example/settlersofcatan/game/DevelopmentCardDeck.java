package com.example.settlersofcatan.game;

import android.util.Log;

import java.security.SecureRandom;

/**
 * A shuffled deck of development cards from which you can draw.
 */
public class DevelopmentCardDeck {
    private static DevelopmentCardDeck instance;
    private DevelopmentCard[] deck;
    private int numberOfCards = 25;


    private DevelopmentCardDeck(){
        this.deck = new DevelopmentCard[]{
                new Knights(),
                new VictoryPoints(),
                new Monopoly(),
                new RoadBuilding(),
                new YearOfPlenty()
        };
    }

    public static DevelopmentCardDeck getInstance() {
        if (instance == null){
            instance = new DevelopmentCardDeck();
        }
        return instance;
    }

    public static void setInstance(DevelopmentCardDeck instance) {
        DevelopmentCardDeck.instance = instance;
    }

    public DevelopmentCard drawDevelopmentCard(){
        SecureRandom rand = new SecureRandom();
        int random = rand.nextInt(5);
        numberOfCards--;

        if (deck[random].getCount() > 0) {
            deck[random].getCard();
            Log.i("DEVELOPMENT","Card was drawn.");
            return deck[random];

        }else {
            for (int i = 0; i < 5; i++){
                if (deck[i].getCount() > 0) {
                    deck[i].getCard();
                    Log.i("DEVELOPMENT","Card was drawn.");
                    return deck[i];
                }
            }
        }

        return null;
    }

    public int getNumberOfCards() {
        return numberOfCards;
    }

    public DevelopmentCard getDevelopmentCard(int index){
        return deck[index];
    }
}
