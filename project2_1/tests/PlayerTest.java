package student;

import model.*;
import org.junit.jupiter.api.Test;

/**
 * Test class for Player object
 *
 * @author Ellis Wright exw4938@g.rit.edu
 */
class PlayerTest {
    @Test
    void reset(){
        Player p = new Player(Baron.BLUE);
        p.reset(Card.BACK, Card.BLUE, Card.BACK, Card.GREEN);
        assert p.countCardsInHand(Card.BACK) == 2;
        assert p.countCardsInHand(Card.BLUE) == 1;
        assert p.countCardsInHand(Card.GREEN) == 1;

        assert p.getLastTwoCards().getFirstCard().equals(Card.NONE);
        assert p.getLastTwoCards().getSecondCard().equals(Card.NONE);

        p.reset(Card.BLUE, Card.BLUE, Card.BLUE, Card.RED, Card.ORANGE, Card.NONE);
        assert p.countCardsInHand(Card.BACK) == 0;
        assert p.countCardsInHand(Card.BLUE) == 3;
        assert p.countCardsInHand(Card.ORANGE) == 1;
        assert p.countCardsInHand(Card.RED) == 1;
        assert p.countCardsInHand(Card.NONE) == 0;
    }

    @Test
    void canClaimRoute(){
        Route r = new RouteC(new StationC(0, 0, "Test1"), new StationC(0, 3, "Test2"), Baron.UNCLAIMED);
        Route r2 = new RouteC(new StationC(0, 0, "Test3"), new StationC(0, 5, "Test4"), Baron.UNCLAIMED);

        Player p = new Player(Baron.BLUE, Card.BACK, Card.BLUE, Card.BACK, Card.GREEN);
        Player p2 = new Player(Baron.BLUE, Card.BLACK, Card.WILD, Card.GREEN);

        assert p.canClaimRoute(r);
        assert p2.canClaimRoute(r);
        assert !p2.canClaimRoute(r2);
        assert !p.canClaimRoute(r2);
    }

    @Test
    void claimRoute(){
        Route r = new RouteC(new StationC(0, 0, "Test1"), new StationC(0, 3, "Test2"), Baron.UNCLAIMED);
        Route r3 = new RouteC(new StationC(1, 0, "Test5"), new StationC(1, 3, "Test6"), Baron.UNCLAIMED);
        Route r2 = new RouteC(new StationC(0, 0, "Test3"), new StationC(0, 5, "Test4"), Baron.UNCLAIMED);

        Player p = new Player(Baron.BLUE, Card.BLACK, Card.BLUE, Card.BLACK, Card.GREEN);
        Player p2 = new Player(Baron.BLUE, Card.BLACK, Card.WILD, Card.GREEN);

        try{
            p.claimRoute(r2);
        }catch(RailroadBaronsException e) {
            //Expected
        }
        try {
            p.claimRoute(r);
        }catch (RailroadBaronsException e){
            System.exit(1);
        }
        assert p.getClaimedRoutes().contains(r);
        assert !p.canClaimRoute(r3);

        try{
            p2.claimRoute(r);
        }catch (RailroadBaronsException e){
            //Expected
        }
        assert !p2.getClaimedRoutes().contains(r);
    }

    @Test
    void startTurn(){
        student.Player p = new Player(Baron.BLUE);
        p.reset(Card.BACK, Card.BLUE, Card.BACK, Card.GREEN);
        p.startTurn(new Pair(Card.BACK, Card.NONE));

        assert p.countCardsInHand(Card.BACK) == 3;
        assert p.countCardsInHand(Card.NONE) == 0;
    }

    @Test
    void canContinuePlaying(){
        Route r = new RouteC(new StationC(0, 0, "Test1"), new StationC(0, 3, "Test2"), Baron.UNCLAIMED);
        Player p = new Player(Baron.BLUE, Card.BLACK, Card.BLUE, Card.BLACK, Card.GREEN);

        try{
            p.claimRoute(r);
        }catch(RailroadBaronsException e){
            System.exit(1);
        }

        assert !p.canContinuePlaying(2);
    }
}