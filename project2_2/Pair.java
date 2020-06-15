package student;

import model.Card;

/**
 * Class for a pair of cards meant to be dealt to a player.
 *
 * @author Ellis Wright exw4938@g.rit.edu
 */
public class Pair implements model.Pair{
    /** The first card stored in the pair */
    private Card firstCard;
    /** The second card stored in the pair */
    private Card secondCard;

    /**
     * Create a new pair of cards with the specified cards as first and second
     * @param firstCard
     * @param secondCard
     */
    public Pair(Card firstCard, Card secondCard){
        this.firstCard = firstCard;
        this.secondCard = secondCard;
    }

    /**
     * Gets the first card in the pair, returns Card.NONE if there is no card
     * @return the first card
     */
    @Override
    public Card getFirstCard() {
        return this.firstCard.equals(null) ? Card.NONE : this.firstCard;
    }

    /**
     * Gets the second card in the pair, returns Card.NONE if there is no card
     * @return the second card
     */
    @Override
    public Card getSecondCard() {
        return this.secondCard.equals(null) ? Card.NONE : this.secondCard;
    }
}
