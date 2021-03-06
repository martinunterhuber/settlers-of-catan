package com.example.settlersofcatan.game.development_cards;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DevelopmentCardDeckTest {
    private DevelopmentCardDeck deck;

    @Before
    public void setUp() {
        deck = DevelopmentCardDeck.getInstance();
    }

    @Test
    public void testReturnsValidDevelopmentCard(){
        Assert.assertEquals(25, deck.getNumberOfCards());
        Assert.assertNotNull(deck.drawDevelopmentCard());
    }

    @Test
    public void testReturnsNullAfterDrawingAllCards(){
        for (int i = 0; i < 25; i++) {
            deck.drawDevelopmentCard();
        }
        Assert.assertNull(deck.drawDevelopmentCard());
    }

    @After
    public void tearDown(){
        deck = null;
        DevelopmentCardDeck.setInstance(null);
    }
}
