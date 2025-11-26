package sk.uniba.fmph.dcs.terra_futura;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.junit.Assert.*;



public class PileTest {
    private static int NOT_RANDOM_RANDOM_SEED = 474747;
    private List<Card> standardDeck;
    private List<Card> shuffledStandardDeck;
    private List<Card> deckWithFewCards;
    private List<Card> emptyDeck;
    @Before
    public void setUp() {
        standardDeck = new ArrayList<>();
        deckWithFewCards = new ArrayList<>();
        emptyDeck = new ArrayList<>();

        // add 20 empty cards, from the perspective of the pile it just needs to be a card
        for(int i =0; i < 20;i++){
            if(i < 3){
                deckWithFewCards.add(new Card());
            }
            standardDeck.add(new Card());
        }
        shuffledStandardDeck = new ArrayList<>(standardDeck);
        Collections.shuffle(shuffledStandardDeck, new Random(NOT_RANDOM_RANDOM_SEED));

    }

    @Test
    public void testGetCardValid() {

        int specificIndexInDeck = 3;
        int specificIndexInPile = 2; // same card when laid down, counting rom 1

        Pile pile = new Pile(standardDeck, NOT_RANDOM_RANDOM_SEED);

        Card cardFromDeck = shuffledStandardDeck.get(specificIndexInDeck-1);
        Card cardFromPile = pile.getCard(specificIndexInPile);

        Assert.assertEquals(cardFromDeck, cardFromPile);

    }

    @Test(expected=RuntimeException.class)
    public void testGetCardWithInvalidIndexLow() {
        Pile pile = new Pile(standardDeck, NOT_RANDOM_RANDOM_SEED);

        pile.getCard(0);

    }

    @Test(expected=RuntimeException.class)
    public void testGetCardWithInvalidIndexHigh() {
        Pile pile = new Pile(standardDeck, NOT_RANDOM_RANDOM_SEED);

        pile.getCard(5);

    }

    @Test(expected=RuntimeException.class)
    public void testGetCardWithInvalidIndexNotEnoughCards() {

        Pile pile = new Pile(deckWithFewCards, NOT_RANDOM_RANDOM_SEED);

        pile.getCard(4);

    }

    @Test
    public void testRemoveLastCard(){
        Pile pile = new Pile(standardDeck, NOT_RANDOM_RANDOM_SEED);

        Card lastCardBeforeRemove = pile.getCard(4);

        pile.removeLastCard();
        Card lastCardAfterRemove = pile.getCard(4);

        assertNotEquals(lastCardBeforeRemove, lastCardAfterRemove);

    }

    @Test
    public void testRemoveFromEmptyDeck(){
        Pile pile = new Pile(emptyDeck, NOT_RANDOM_RANDOM_SEED);

        pile.removeLastCard();

    }

    @Test(expected=RuntimeException.class)
    public void testMultipleTakeUntilNoMore(){
        Pile pile = new Pile(deckWithFewCards, NOT_RANDOM_RANDOM_SEED);

        pile.takeCard(3);
        pile.takeCard(2);
        pile.takeCard(1);
        pile.takeCard(1);
    }

    @Test
    public void testMultipleTakeUntilZeroLeft(){
        Pile pile = new Pile(deckWithFewCards, NOT_RANDOM_RANDOM_SEED);

        pile.takeCard(3);
        pile.takeCard(2);
        pile.takeCard(1);

    }

}
