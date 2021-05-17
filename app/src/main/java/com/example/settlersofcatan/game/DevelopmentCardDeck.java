package com.example.settlersofcatan.game;

import android.util.Log;

import java.util.Random;

/**
 * A shuffled deck of development cards from which you can draw.
 */
public class DevelopmentCardDeck {
    private DevelopmentCard[] deck;
    private int numberOfCards = 25;
    private Random rand = new Random();

    public DevelopmentCardDeck() {
        this.deck = new DevelopmentCard[]{
                new Knights(),
                new VictoryPoints(),
                new Monopoly(),
                new RoadBuilding(),
                new YearOfPlenty()
        };
    }

    public DevelopmentCard drawDevelopmentCard(){
        int random = rand.nextInt(5);
        numberOfCards--;

        if (deck[random].getCount() > 0) {
            deck[random].getCard();
            Log.i("DEVELOPMENT","Card was drawn.");
            return deck[random];

        }else {
            for (int i = 0; i < 5; i++){
                if (deck[random].getCount() > 0) {
                    deck[random].getCard();
                    Log.i("DEVELOPMENT","Card was drawn.");
                    return deck[random];
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
