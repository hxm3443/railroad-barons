package student;

import model.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a train route in the Railroad Barons game. A route comprises
 * {@linkplain Track tracks} between two {@linkplain Station stations} on the
 * map. Valid routes must include two distinct (non-equal) stations, must be
 * either {@linkplain Orientation#HORIZONTAL horizontal} or
 * {@linkplain Orientation#VERTICAL vertical}, and the origin station must be
 * north of or to the west of the destination station (this simplifies some of
 * the route methods).
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class RouteC implements model.Route {
    private model.Station origin;
    private model.Station destination;
    private Baron claimant;
    private Orientation orientation;
    private List<Track> tracksList;
    private int numTracks;
    private int pointValue;

    /**
     * Constructor
     *
     * @param origin      the station at the beginning of this route
     * @param destination the station at the end of this route
     * @param claimant    the Baron that has claimed this route
     */
    public RouteC(model.Station origin, model.Station destination, Baron claimant) {
        this.origin = origin;
        this.destination = destination;
        this.claimant = claimant;
        tracksList = new ArrayList<>();

        //determining orientation
        int startRow = origin.getRow();
        int startCol = origin.getCol();
        int endRow = destination.getRow();
        int endCol = destination.getCol();

        if (startRow == endRow) {
            orientation = Orientation.HORIZONTAL;
            //computing the number of tracks
            numTracks = endCol - startCol - 1;

            //creating a list of tracks for a single horizontal route
            for (int i = 1; i <= numTracks; i++) {
                model.Track track = new TrackC(startRow, startCol + i, Orientation.HORIZONTAL, this);
                tracksList.add(track);
            }
        } else if (startCol == endCol) {
            orientation = Orientation.VERTICAL;
            //computing the number of tracks
            numTracks = endRow - startRow - 1;

            //creating a list of tracks for a single vertical route
            for (int i = 1; i <= numTracks; i++) {
                model.Track track = new TrackC(startRow + i, startCol, Orientation.VERTICAL, this);
                tracksList.add(track);
            }
        }

        //computing the pointValue corresponding to the length of the route
        if (numTracks == 1) {
            pointValue = 1;
        } else if (numTracks == 2) {
            pointValue = 2;
        } else if (numTracks == 3) {
            pointValue = 4;
        } else if (numTracks == 4) {
            pointValue = 7;
        } else if (numTracks == 5) {
            pointValue = 10;
        } else if (numTracks == 6) {
            pointValue = 15;
        } else if (numTracks >= 7) {
            pointValue = 5 * (numTracks - 3);
        }
    }

    /**
     * Returns the {@linkplain Baron} that has claimed this route. Note that
     * this route may be {@linkplain Baron#UNCLAIMED unclaimed}.
     *
     * @return The {@link Baron} that has claimed this route.
     */
    @Override
    public Baron getBaron() {
        return claimant;
    }

    /**
     * Returns the {@linkplain Station station} at the beginning of this
     * route. The origin must be directly north of or to the west of the
     * destination.
     *
     * @return The {@link Station} at the beginning of this route.
     */
    @Override
    public Station getOrigin() {
        return origin;
    }

    /**
     * Returns the {@linkplain Station station} at the end of this route. The
     * destination must be directly south of or to the east of the origin.
     *
     * @return The {@link Station} at the end of this route.
     */
    @Override
    public Station getDestination() {
        return destination;
    }

    /**
     * Returns the {@linkplain Orientation orientation} of this route; either
     * {@linkplain Orientation#HORIZONTAL horizontal} or
     * {@linkplain Orientation#VERTICAL vertical}.
     *
     * @return The {@link Orientation} of this route.
     */
    @Override
    public Orientation getOrientation() {
        return orientation;
    }

    /**
     * The set of {@linkplain Track tracks} that make up this route.
     *
     * @return The {@link List} of {@link Track tracks} that make up this
     * route.
     */
    @Override
    public List<Track> getTracks() {
        return tracksList;
    }

    /**
     * Returns the length of the route, not including the {@linkplain Station
     * stations} at the end points.
     *
     * @return The number of {@link Track Tracks} comprising this route.
     */
    @Override
    public int getLength() {
        return numTracks;
    }

    /**
     * Returns the number of points that this {@linkplain Route route} is
     * worth according to the following algorithm:
     * <ul>
     * <li>1 - 1 point</li>
     * <li>2 - 2 points</li>
     * <li>3 - 4 points</li>
     * <li>4 - 7 points</li>
     * <li>5 - 10 points</li>
     * <li>6 - 15 points</li>
     * <li>7 (or more) - 5 * (length - 3) points</li>
     * </ul>
     *
     * @return The number of points that this route is worth.
     */
    @Override
    public int getPointValue() {
        return pointValue;
    }

    /**
     * Returns true if the route covers the ground at the location of the
     * specified {@linkplain Space space} and false otherwise.
     *
     * @param space The {@link Space} that may be in this route.
     * @return True if the {@link Space Space's} coordinates are a part of
     * this route, and false otherwise.
     */
    @Override
    public boolean includesCoordinate(Space space) {
        boolean checkTrackListIncludesCoordinates = false;
        for (Track track : tracksList) {
            checkTrackListIncludesCoordinates = track.collocated(space);
            if (checkTrackListIncludesCoordinates) {
                return true;
            }
        }

        if (origin.collocated(space)) {
            return true;
        }

        if (destination.collocated(space)) {
            return true;
        }
        return false;
    }

    /**
     * Attempts to claim the route on behalf of the specified
     * {@linkplain Baron}. Only unclaimed routes may be claimed.
     *
     * @param claimant The {@link Baron} attempting to claim the route. Must
     *                 not be null or {@link Baron#UNCLAIMED}.
     * @return True if the route was successfully claimed. False otherwise.
     */
    @Override
    public boolean claim(Baron claimant) {
        if (claimant != Baron.UNCLAIMED) {
            this.claimant = claimant;
            return true;
        }
        return false;
    }
}
