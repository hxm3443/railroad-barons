package student;

import model.*;
import model.Deck;
import model.Player;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Main class to run the RailroadBarons game
 *
 * @author: Ellis Wright
 */
public class RailroadBarons implements model.RailroadBarons{
    /** The list of observers of this RailroadBarons class */
    private List<RailroadBaronsObserver> observers;

    /** The RailroadMap for this instance of the game */
    private RailroadMap map;

    /** The deck this game is being played with */
    private Deck deck;

    /** The players currently in the game */
    private List<student.Player> players;

    /** Integer representing the player whose turn it currently is */
    private int player;

    /** List of Barons */
    private Baron[] b = {Baron.BLUE, Baron.GREEN, Baron.RED, Baron.YELLOW};

    /**
     * Create a new RailroadBarons instance
     */
    public RailroadBarons(){
        this.observers = new ArrayList<>();
        this.map = null;
        this.deck = null;
        this.players = new ArrayList<>();
        this.player = 0;

        for (int i = 0; i < 4; i++){
            this.players.add(new student.Player(b[i]));
        }
    }

    /**
     * Adds an observer to this railroad barons instance
     * @param observer The {@link RailroadBaronsObserver} to add to the
     */
    @Override
    public void addRailroadBaronsObserver(RailroadBaronsObserver observer) {
        this.observers.add(observer);
    }

    /**
     * Remove the given observer from the observer list
     * @param observer The {@link RailroadBaronsObserver} to remove.
     */
    @Override
    public void removeRailroadBaronsObserver(RailroadBaronsObserver observer) {
        if (this.observers.contains(observer)){
            this.observers.remove(observer);
        }
    }

    /**
     * Start a game with a fresh deck and the specified map
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map) {
        this.map = map;
        this.deck = new student.Deck();
        this.deck.reset();
        for (student.Player p : this.players){
            p.deal(this.deck.drawACard(), this.deck.drawACard(),this.deck.drawACard(),this.deck.drawACard());
        }
        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));
        this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
    }

    /**
     * Start a game with the specified map and deck
     * @param map The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     *             {@link RailroadBarons} interface should use only the
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        this.map = map;
        this.deck = deck;
        for (student.Player p : this.players){
            p.deal(this.deck.drawACard(), this.deck.drawACard(),this.deck.drawACard(),this.deck.drawACard());
        }
        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));
        this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
    }

    /**
     * Gets the map for the current game
     * @return the map
     */
    @Override
    public RailroadMap getRailroadMap() {
        return this.map;
    }

    /**
     * Gets the number of cards remaining in the deck
     * @return the number of cards
     */
    @Override
    public int numberOfCardsRemaining() {
        return this.deck.numberOfCardsRemaining();
    }

    /**
     * Checks if the current player can claim the given route
     * @param row The row of a {@link Track} in the {@link Route} to check.
     * @param col The column of a {@link Track} in the {@link Route} to check.
     * @return
     */
    @Override
    public boolean canCurrentPlayerClaimRoute(int row, int col) {
        Route r = this.map.getRoute(row, col);
        return this.players.get(this.player).canClaimRoute(r);
    }

    /**
     * Has the current player claim the route at the given coordinates
     * @param row The row of a {@link Track} in the {@link Route} to claim.
     * @param col The column of a {@link Track} in the {@link Route} to claim.
     * @throws RailroadBaronsException
     */
    @Override
    public void claimRoute(int row, int col) throws RailroadBaronsException {
        this.players.get(this.player).claimRoute(this.map.getRoute(row, col));
        this.map.routeClaimed(this.map.getRoute(row, col));
    }

    /**
     * Cycles the game to the next players turn
     */
    @Override
    public void endTurn() {
        this.observers.forEach(o -> o.turnEnded(this, this.players.get(this.player)));
        this.player = (this.player + 1) % this.players.size();
        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));
        this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
    }

    /**
     * Gets the current player whose turn it is
     * @return the player
     */
    @Override
    public Player getCurrentPlayer() {
        return this.players.get(this.player);
    }

    /**
     * Get the current players
     * @return
     */
    @Override
    public Collection<Player> getPlayers() {
        List<Player> p = new ArrayList<>();
        for(student.Player o : this.players){
            p.add(o);
        }
        return p;
    }

    /**
     * Returns the baron with the highest score
     * @return the baron
     */
    private Player getWinner(){
        int max = 0;
        Player mPlayer = null;
        for (Player o : this.players){
            if (o.getScore() > max){
                max = o.getScore();
                mPlayer = o;
            }
        }
        return mPlayer;
    }


    /**
     * Checks if the current game is over
     * @return if the game is over
     */
    @Override
    public boolean gameIsOver() {
        boolean routesLeft = false;
        for (Route r : this.map.getRoutes()){
            if (r.getBaron().equals(Baron.UNCLAIMED)){
                routesLeft = true;
            }
        }
        for (Player p : this.players){
            if (!p.canContinuePlaying(this.map.getLengthOfShortestUnclaimedRoute())){
                this.observers.forEach(o -> o.gameOver(this, getWinner()));
                return false;
            }
        }
        if (!routesLeft){
            this.observers.forEach(o -> o.gameOver(this, getWinner()));
        }
        return routesLeft;
    }
}
