package sk.uniba.fmph.dcs.terra_futura;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class Pile {
    //placeholder class
    private final List<Card> deck;
    private final List<Card> visibleCards;
    private final int randomSeed;
    private final Random random;

    public static final int VISIBLE_CARDS_LOWER_BOUND = 1;
    public static final int VISIBLE_CARDS_UPPER_BOUND = 4;
    public Pile(List<Card> deck) {
        this.deck = deck;
        this.visibleCards = new ArrayList<>();
        this.randomSeed = ThreadLocalRandom.current().nextInt();
        random = new Random(this.randomSeed);
    }

    public Pile(List<Card> deck, int randomSeed) {
        this.deck = deck;
        this.visibleCards = new ArrayList<>();
        this.randomSeed = randomSeed;
        this.random = new Random(this.randomSeed);
    }

    public final Card getCard(final int index) {
        if(index < VISIBLE_CARDS_LOWER_BOUND || index > Math.min(VISIBLE_CARDS_UPPER_BOUND,  visibleCards.size())){
            throw new RuntimeException("Invalid index");
        }
        return visibleCards.get(index-1);
    }

    public final void takeCard(final int index){
        if(index < VISIBLE_CARDS_LOWER_BOUND || index > Math.min(VISIBLE_CARDS_UPPER_BOUND,  visibleCards.size())){
            throw new RuntimeException("Invalid index");
        }

        visibleCards.remove(index-1);

        if(!deck.isEmpty()){
            visibleCards.addFirst( deck.getFirst());
            deck.removeFirst();
        }


    }

    public final void removeLastCard() {
        if(!visibleCards.isEmpty()){
            visibleCards.removeLast();
        }
        if(!deck.isEmpty()){
            visibleCards.addFirst( deck.getFirst());
            deck.removeFirst();
        };
    }

    public final String state() {
        throw new RuntimeException("Not implemented");
    }
}
