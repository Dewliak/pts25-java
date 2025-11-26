package sk.uniba.fmph.dcs.terra_futura;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
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
        this(deck,ThreadLocalRandom.current().nextInt());
    }

    public Pile(List<Card> deck, int randomSeed) {
        this.deck = new ArrayList<>(deck);
        this.visibleCards = new ArrayList<>();
        this.randomSeed = randomSeed;
        this.random = new Random(this.randomSeed);

        //Shuffles the deck
        shufflePile();

        //inital load of the visible cards
        for(int i =0; i < Math.min(VISIBLE_CARDS_UPPER_BOUND, deck.size()); i++) {
            drawCardAFromPile();
        }

    }



    public final Card getCard(final int index) {
        if(index < VISIBLE_CARDS_LOWER_BOUND || index > Math.min(VISIBLE_CARDS_UPPER_BOUND,  visibleCards.size())){
            throw new RuntimeException("Invalid index");
        }
        return visibleCards.get(index-1);
    }

    private void shufflePile(){
        Collections.shuffle(deck,random);
    }

    private void drawCardAFromPile() {
        visibleCards.addFirst( deck.getFirst());
        deck.removeFirst();
    }

    public final void takeCard(final int index){
        if(index < VISIBLE_CARDS_LOWER_BOUND || index > Math.min(VISIBLE_CARDS_UPPER_BOUND,  visibleCards.size())){
            throw new RuntimeException("Invalid index");
        }

        visibleCards.remove(index-1);

        if(!deck.isEmpty()){
            drawCardAFromPile();
        }


    }

    public final void removeLastCard() {
        if(!visibleCards.isEmpty()){
            visibleCards.removeLast();
        }
        if(!deck.isEmpty()){
            drawCardAFromPile();
        };
    }

    public final String state() {

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("visibleCard1", (visibleCards.size() >= 1) ? (visibleCards.get(0)) : ("NoCard"));
        jsonObject.put("visibleCard2", (visibleCards.size() >= 2) ? (visibleCards.get(1)) : ("NoCard"));
        jsonObject.put("visibleCard3", (visibleCards.size() >= 3) ? (visibleCards.get(2)) : ("NoCard"));
        jsonObject.put("visibleCard4", (visibleCards.size() >= 4) ? (visibleCards.get(3)) : ("NoCard"));

        return jsonObject.toString(4);
    }
}
