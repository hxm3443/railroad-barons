package student;

import model.*;

/**
 * Class represents a single Computer Player in the RailroadBarons Lonely Edition game
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class ComputerPlayer extends student.Player {
    /**
     * Constructor
     *
     * @param baron the baron the player is playing as
     * @param r     object of RailroadBarons
     * @param dealt the hand of cards dealt to the player at game start
     */
    public ComputerPlayer(Baron baron, RailroadBarons r, Card... dealt) {
        super(baron, r, dealt);
    }

    /**
     * Gets the Player set up for the start of their turn
     *
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    @Override
    public void startTurn(model.Pair dealt) {
        //This method will need to handle the entire computer turn
        if (this.hasClaimed == true) {
            this.hasClaimed = false;
        }
        this.pair = dealt;
        if (!dealt.getFirstCard().equals(Card.NONE)) {
            if (this.cards.containsKey(dealt.getFirstCard()))
                this.cards.replace(dealt.getFirstCard(), this.cards.get(dealt.getFirstCard()) + 1);
            else
                this.cards.put(dealt.getFirstCard(), 1);
        } else if (!dealt.getSecondCard().equals(Card.NONE)) {
            if (this.cards.containsKey(dealt.getFirstCard()))
                this.cards.replace(dealt.getSecondCard(), this.cards.get(dealt.getSecondCard()) + 1);
            else
                this.cards.put(dealt.getSecondCard(), 1);
        }
        claimRouteCPlayer();
    }

    /**
     * This method is responsible for the decisions that the Computer Player makes about what move to make based on the
     * cards currently in hand and the number of train pieces remaining. This method uses the "dumb" player implementation
     * that simply claims the first unclaimed route that it is capable of claiming.
     */
    public void claimRouteCPlayer() {
        int maxNumOfCards = this.maxCards();

        for (Route route : this.r.map.getRoutes()) {
            if (route.getBaron().equals(Baron.UNCLAIMED) && route.getLength() == maxNumOfCards) {
                try {
                    claimRoute(route);
                } catch (RailroadBaronsException e) {
                    e.printStackTrace();
                }
                this.r.map.routeClaimed(route);
                break;
            }
        }
    }
}
