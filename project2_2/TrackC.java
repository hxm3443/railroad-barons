package student;

import model.Baron;
import model.Orientation;
import model.Route;
import model.Track;

import java.util.Objects;

/**
 * Represents a track segment on the map. Tracks combine to form
 * {@linkplain Route routes}.
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class TrackC extends SpaceC implements model.Track {
    private Orientation orientation;
    private Route route;
    private int row;
    private int col;

    /**
     * Constructor
     *
     * @param row         represents the row coordinate of the space's location in the map
     * @param col         represents the column coordinate of the space's location in the map
     * @param orientation orientation of the track (Horizontal or Vertical)
     * @param route       the route to which this track belongs
     */
    public TrackC(int row, int col, Orientation orientation, Route route) {
        super(row, col);
        this.row = row;
        this.col = col;
        this.orientation = orientation;
        this.route = route;
    }

    /**
     * Returns the orientation of the track; either
     * {@linkplain Orientation#HORIZONTAL horizontal} or
     * {@linkplain Orientation#VERTICAL vertical}. This is based on the
     * {@linkplain Orientation orientation} of the {@linkplain Route route}
     * that contains the track.
     *
     * @return The {@link Orientation} of the {@link Track}; this is the same
     * as the {@link Orientation} of the {@link Route} that contains the
     * track.
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * Returns the current {@link Baron owner} of this track, either
     * {@linkplain Baron#UNCLAIMED unclaimed} if the track has not been
     * claimed, or the {@linkplain Baron owner} that corresponds with the
     * color of the player that successfully claimed the
     * {@linkplain Route route} of which this track is a part.
     *
     * @return The {@link Baron} that has claimed the route of which this
     * track is a part.
     */
    @Override
    public Baron getBaron() {
        return route.getBaron();
    }

    /**
     * Returns the {@linkplain Route route} of which this
     * {@linkplain Track track} is a part.
     *
     * @return The {@link Route} that contains this track.
     */
    @Override
    public Route getRoute() {
        return route;
    }

    /**
     * This equals() method compares two Track objects and checks if they are equal by comparing their row value,
     * column value, orientation, and route.
     *
     * @param other the Track to compare with
     * @return true if the two Track objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof TrackC)) {
            return false;
        }
        TrackC track = (TrackC) other;
        return this.orientation == track.getOrientation() && this.route.equals(track.getRoute()) && this.row == track.getRow()
                && this.col == track.getCol();
    }

    /**
     *
     * @return
     */
    @Override
    public int hashCode() {
        return Objects.hash(orientation, route, row, col);
    }
}
