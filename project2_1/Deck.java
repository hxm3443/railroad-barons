package student;

import model.Card;

import java.util.*;

/**
 * Class to represent a deck of cards and methods to work with them
 *
 * @author Ellis Wright exw4938
 */
public class Deck implements model.Deck{
    /** Queue to represent the actual deck */
    private Queue<Card> deck;

    /**
     * Create a new deck and populate the queue with cards
     */
    public Deck(){
        this.deck = new LinkedList<Card>();
        this.reset();
    }

    /**
     * Shuffles the deck
     */
    private void shuffle(){
        List<Card> cards = new ArrayList<>();
        cards.addAll(this.deck);
        this.deck.clear();
        Collections.shuffle(cards, new Random(System.currentTimeMillis()));
        this.deck.addAll(cards);
    }

    /**
     * Adds twenty of each type of card to the deck
     */
    private void addCardsToDeck(){
        for (Card o : Card.values()){
            if (o.equals(Card.NONE) || o.equals(Card.BACK)){
                continue;
            }

            for (int i = 0; i < 20; i ++){
                this.deck.add(o);
            }
        }
    }

    /**
     * Clears the deck, adds new cards to it, and shuffles them
     */
    @Override
    public void reset() {
        this.deck.clear();
        this.addCardsToDeck();
        this.shuffle();
    }

    /**
     * Gets the card from the front of the deck and removes it from the deck
     * @return the front card
     */
    @Override
    public Card drawACard() {
        return this.deck.size() == 0 ? Card.NONE : this.deck.poll();
    }

    /**
     * Gets the current number of cards in the deck
     * @return the current number of cards
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.deck.size();
    }

    public boolean equals(Deck deck){
        return this.deck.equals(deck);
    }
}
