package student;

import model.*;
import model.Pair;

import java.util.*;

/**
 * Class represents a single player in the Railroad Barons game
 *
 * @author Ellis Wright exw4938@g.rit.edu
 */
public class Player implements model.Player{
    private final int TRAIN_PIECES = 45;
    /** The number of train pieces the player has */
    protected int trainPieces;

    /** The cards in the players hand */
    protected Map<Card, Integer> cards;

    /** The players current score */
    protected int score;

    /** The routes the player has claimed */
    protected List<Route> routes;

    /** The most recently dealt pair of cards */
    protected Pair pair;

    /** The baron as which the player is playing */
    protected Baron baron;

    /** Tells whether this player has claimed a route this round */
    protected boolean hasClaimed;

    /** The observers of this player */
    private List<model.PlayerObserver> observers;

    /** The rrb class this player belongs to */
    protected RailroadBarons r;

    /**
     * Create a new player with the specified baron and hand
     * @param b the baron the player is playing as
     * @param dealt the hand of cards dealt to the player at game start
     */
    public Player(Baron b, RailroadBarons r, Card... dealt){
        this.baron = b;
        this.observers = new ArrayList<>();
        this.reset(dealt);
        this.hasClaimed = false;
        this.notifyObservers();
        this.r = r;
    }


    /**
     * Create a new player with the specified baron and hand
     * @param b the baron the player is playing as
     * @param dealt the hand of cards dealt to the player at game start
     */
    public Player(Baron b, Card... dealt){
        this.baron = b;
        this.observers = new ArrayList<>();
        this.reset(dealt);
        this.hasClaimed = false;
        this.notifyObservers();
    }

    /**
     * Resets the game in preparation for a new round or the start
     * @param dealt The hand of {@link Card cards} dealt to the player at the
     */
    @Override
    public void reset(Card... dealt) {
        this.trainPieces = TRAIN_PIECES;
        this.cards = new HashMap<>();
        this.score = 0;
        this.routes = new ArrayList<>();
        this.pair = new student.Pair(Card.NONE, Card.NONE);
        for (Card o : dealt){
            if (o.equals(Card.NONE)){
                continue;
            }else if (this.cards.containsKey(o)){
                    this.cards.replace(o, this.cards.get(o) + 1);
            }else {
                this.cards.put(o, 1);
            }
        }
        this.notifyObservers();
    }

    /**
     * Adds the specified cards to the players hands
     * @param dealt the cards dealt
     */
    public void deal(Card... dealt){
        for (Card o : dealt){
            if (this.cards.containsKey(o)){
                this.cards.replace(o, this.cards.get(o) + 1);
            }else{
                this.cards.put(o, 1);
            }
        }
    }

    /**
     * Add the specified observer to the list of observing objects
     * @param observer The new {@link PlayerObserver}.
     */
    @Override
    public void addPlayerObserver(PlayerObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Remove the specified observer from the list of observing objects
     * @param observer The {@link PlayerObserver} to remove.
     */
    @Override
    public void removePlayerObserver(PlayerObserver observer) {
        this.observers.remove(observer);
    }

    /**
     * Notify all the observers when something happens to this player
     */
    public void notifyObservers(){
        this.observers.forEach(x -> x.playerChanged(this));
    }

    /**
     * Gets the baron color for this player
     * @return the baron
     */
    @Override
    public Baron getBaron() {
        return this.baron;
    }

    /**
     * Gets the Player set up for the start of their turn
     * @param dealt a {@linkplain Pair pair of cards} to the player. Note that
     */
    @Override
    public void startTurn(Pair dealt) {
        if (this.hasClaimed == true){
            this.hasClaimed = false;
        }
        this.pair = dealt;
        System.out.println(cards);
        if (!dealt.getFirstCard().equals(Card.NONE)){
            if (this.cards.containsKey(dealt.getFirstCard()))
                this.cards.replace(dealt.getFirstCard(), this.cards.get(dealt.getFirstCard()) + 1);
            else
                this.cards.put(dealt.getFirstCard(), 1);
        }
        if (!dealt.getSecondCard().equals(Card.NONE)){
            if (this.cards.containsKey(dealt.getSecondCard()))
                this.cards.replace(dealt.getSecondCard(), this.cards.get(dealt.getSecondCard()) + 1);
            else
                this.cards.put(dealt.getSecondCard(), 1);
        }
        System.out.println(cards);
        this.notifyObservers();
    }

    /**
     * Gets the last pair of cards dealt to the player
     * @return the last Pair
     */
    @Override
    public Pair getLastTwoCards() {
        return this.pair;
    }

    /**
     * Returns the number of the specified card the player has in their hand
     * @param card The {@link Card} of interest.
     * @return the amount of the specific card
     */
    @Override
    public int countCardsInHand(Card card) {
        return this.cards.containsKey(card) ? this.cards.get(card) : 0;
    }

    /**
     * Gets the number of pieces the player has left
     * @return the number of pieces
     */
    @Override
    public int getNumberOfPieces() {
        return this.trainPieces;
    }

    /**
     * Returns the maximum amount of a single color of cards + the number of wild
     * cards a player has. used to calculate if a route can be claimed.
     * @return the number of cards
     */
    public int maxCards(){
        int maxNum = 0;
        for (Card o : this.cards.keySet()){
            if (this.cards.get(o) > maxNum && !o.equals(Card.WILD)){
                maxNum = this.cards.get(o);
            }
        }
        if (maxNum == 0){
            return 0;
        }else if(this.cards.containsKey(Card.WILD) && this.cards.get(Card.WILD) >= 1){
            return maxNum + 1;
        }else{
            return maxNum;
        }
    }

    /**
     * Tells whether the current player is able to claim a specified route
     * @param route The {@link RouteC} being tested to determine whether or not
     *              the player is able to claim it.
     * @return if the player can claim the route
     */
    @Override
    public boolean canClaimRoute(Route route) {
        return route.getBaron().equals(Baron.UNCLAIMED) && !this.hasClaimed && route.getLength() <= this.maxCards() &&
                this.getNumberOfPieces() >= route.getLength();
    }

    /**
     * Tells if the player can claim a route of a specific length
     * @param length
     * @return
     */
    private boolean canClaimRouteLength(int length){
        return length <= this.maxCards() && this.getNumberOfPieces() >= length;
    }

    /**
     * Will remove the number of cards specified of one color (+ wilds) in order to
     * claim a route
     * @param numberToRemove the number of cards to remove
     */
    protected void removeCards(int numberToRemove){
        int maxNumber = 0;
        Card max = Card.NONE;
        for (Card o : this.cards.keySet()){
            if (this.cards.get(o) > maxNumber && !o.equals(Card.WILD)){
                max = o;
                maxNumber = this.cards.get(o);
            }
        }

        if (maxNumber >= numberToRemove){
            this.cards.replace(max, this.cards.get(max) - numberToRemove);
        }else{
            this.cards.replace(max, 0);
            this.cards.replace(Card.WILD, this.cards.get(Card.WILD) - 1);
        }
    }

    /**
     * Claim the route specified
     * @param route The {@link RouteC} to claim.
     *
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(Route route) throws RailroadBaronsException {
        if (!this.canClaimRoute(route)){
            throw new RailroadBaronsException("The given route cannot be claimed...");
        }

        this.hasClaimed = true;
        this.routes.add(route);
        this.score += route.getPointValue();
        route.claim(this.baron);
        this.removeCards(route.getLength());
        this.trainPieces -= route.getLength();
        this.notifyObservers();
    }

    /**
     * Get the routes that have been claimed by this baron
     * @return the claimed routes
     */
    @Override
    public Collection<Route> getClaimedRoutes() {
        return this.routes;
    }

    /**
     * Get the score of this player
     * @return the score
     */
    @Override
    public int getScore() {
        Graph map = new Graph(this.r.getRailroadMap());
        student.Graph.Vector v = map.bFSLength(new StationC(-1, -1, "North"), new StationC(-1, -1, "South"), this.baron);
        student.Graph.Vector v1 = map.bFSLength(new StationC(-1, -1, "West"), new StationC(-1, -1, "East"), this.baron);
        int score = this.score;
        if (v.getB() && v.getA() >= 3){
            score += this.r.getRailroadMap().getRows() * 5;
        }else if(v1.getB() && v1.getA() >= 3){
            score += this.r.getRailroadMap().getCols() * 5;
        }
        return score;
    }

    /**
     * Tells whether the player can continue playing or not based on their cards and tokens
     * @param shortestUnclaimedRoute The length of the shortest unclaimed
     *                               {@link RouteC} in the current game.
     *
     * @return whether they can play
     */
    @Override
    public boolean canContinuePlaying(int shortestUnclaimedRoute) {
        return this.canClaimRouteLength(shortestUnclaimedRoute);
    }

    /**
     * Gets string representation of the player
     * @return string
     */
    @Override
    public String toString(){
        return this.baron.toString();
    }
}
