package com.example.settlersofcatan.game.development_cards;

import com.example.settlersofcatan.game.Game;

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
        if (numberOfCards == 0){
            return null;
        }
        int randomCard = Game.random.nextInt(numberOfCards--);
        int current = 0;
        for (DevelopmentCard card : deck){
            for (int i = 0; i < card.getCount(); i++, current++){
                if (current == randomCard){
                    card.getCard();
                    return card;
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
