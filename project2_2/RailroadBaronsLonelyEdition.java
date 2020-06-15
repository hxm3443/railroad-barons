package student;

import model.*;
import model.Deck;

/**
 * Main class to run the RailroadBarons Lonely Edition game: 1 human vs. 3 computer players.
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class RailroadBaronsLonelyEdition extends student.RailroadBarons {
    /**
     * Constructor
     * <p>
     * Makes call to RailroadBarons constructor
     * Also adds one human player and 3 computer players to the players list
     */
    public RailroadBaronsLonelyEdition() {
        super();
        this.players.clear();

        for (int i = 0; i < 4; i++) {
            if (i != 0) {
                ComputerPlayer computerPlayer = new student.ComputerPlayer(this.b[i], this);
                this.players.add(computerPlayer);
            } else {
                this.players.add(new student.Player(this.b[i], this));
            }
        }
    }

    /**
     * Start a game with a fresh deck and the specified map
     *
     * @param map The {@link RailroadMap} on which the game will be played.
     */
    @Override
    public void startAGameWith(RailroadMap map) {
        this.map = map;
        this.deck = new student.Deck();
        this.deck.reset();
        for (student.Player p : this.players) {
            p.deal(this.deck.drawACard(), this.deck.drawACard(), this.deck.drawACard(), this.deck.drawACard());
        }
        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));

        if (!(this.players.get(this.player) instanceof student.ComputerPlayer)) {
            this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
        }
    }

    /**
     * Start a game with the specified map and deck
     *
     * @param map  The {@link RailroadMap} on which the game will be played.
     * @param deck The {@link Deck} of cards used to play the game. This may
     *             be ANY implementation of the {@link Deck} interface,
     *             meaning that a valid implementation of the
     */
    @Override
    public void startAGameWith(RailroadMap map, Deck deck) {
        this.map = map;
        this.deck = deck;
        for (student.Player p : this.players) {
            p.deal(this.deck.drawACard(), this.deck.drawACard(), this.deck.drawACard(), this.deck.drawACard());
        }
        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));

        if (!(this.players.get(this.player) instanceof student.ComputerPlayer)) {
            this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
        }
    }

    /**
     * Cycles the game to the next players turn
     */
    @Override
    public void endTurn() {
        if (!(this.players.get(this.player) instanceof ComputerPlayer)) {
            this.observers.forEach(o -> o.turnEnded(this, this.players.get(this.player)));
        }

        this.player = (this.player + 1) % this.players.size();

        while (players.get(this.player) instanceof ComputerPlayer) {
            this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
            this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));
            this.observers.forEach(o -> o.turnEnded(this, this.players.get(this.player)));
            this.player = (this.player + 1) % this.players.size();
        }

        this.players.get(this.player).startTurn(new Pair(this.deck.drawACard(), this.deck.drawACard()));


        if (!(this.players.get(this.player) instanceof ComputerPlayer)) {
            this.observers.forEach(o -> o.turnStarted(this, this.players.get(this.player)));
        }
    }
}
