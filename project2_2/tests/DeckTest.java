package student;

import model.Card;
import org.junit.jupiter.api.Test;

/**
 * Test class for Deck object
 *
 * @author Ellis Wright exw4938@g.rit.edu
 */
class DeckTest {

    @Test
    void reset() {
        Deck d1 = new Deck();
        Deck d2 = new Deck();
        assert !d1.equals(d2);
        d1.reset();
        assert !d1.equals(d2);
    }

    @Test
    void drawACard() {
        Deck d1 = new Deck();
        int len = d1.numberOfCardsRemaining();
        Card c;
        for (int i = 0; i < len; i++){
            c = d1.drawACard();
            assert !c.equals(Card.NONE);
        }
        c = d1.drawACard();
        assert c.equals(Card.NONE);
        assert d1.numberOfCardsRemaining() == 0;
    }

    @Test
    void numberOfCardsRemaining() {
        Deck d1 = new student.Deck();
        System.out.println(d1.numberOfCardsRemaining());
        assert d1.numberOfCardsRemaining() == 180;
        d1.drawACard();
        assert d1.numberOfCardsRemaining() == 179;
        for (int i = 0; i < 10;i++){
            d1.drawACard();
        }
        assert d1.numberOfCardsRemaining() == 169;
    }
}