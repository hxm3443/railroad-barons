package student;

import model.*;
import model.Route;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Represents a Railroad Barons map, which consists of empty
 * {@linkplain Space spaces}, {@linkplain Station stations},
 * {@linkplain Track tracks}, and {@linkplain Route routes}.
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class RailroadMapC implements model.RailroadMap {
    private Space[][] map;
    private int maxRows;
    private int maxCols;
    private int numRows;
    private int numCols;
    private List<RailroadMapObserver> observersList;
    private List<Station> stationList;
    private List<Route> routeList;

    /**
     * Constructor: Building the map
     *
     * @param stationList array list containing stations
     * @param routeList   array list containing routes
     */
    public RailroadMapC(List<Station> stationList, List<Route> routeList) {
        maxRows = 0;
        maxCols = 0;
        observersList = new ArrayList<>();
        this.stationList = stationList;
        this.routeList = routeList;

        //Computing number of rows and columns for the map
        for (Station station : stationList) {
            if (station.getRow() > maxRows) {
                maxRows = station.getRow();
            }
            if (station.getCol() > maxCols) {
                maxCols = station.getCol();
            }
        }
        numRows = maxRows + 1;
        numCols = maxCols + 1;
        map = new SpaceC[numRows][numCols];

        //Adding stations, tracks, and empty spaces onto the map
        for (Route route : routeList) {
            Station origin = route.getOrigin();
            int originStationRow = route.getOrigin().getRow();
            int originStationCol = route.getOrigin().getCol();
            map[originStationRow][originStationCol] = origin;

            Station destination = route.getDestination();
            int destinationStationRow = route.getDestination().getRow();
            int destinationStationCol = route.getDestination().getCol();
            map[destinationStationRow][destinationStationCol] = destination;

            for (Track track : route.getTracks()) {
                int trackRow = track.getRow();
                int trackCol = track.getCol();
                map[trackRow][trackCol] = track;
            }
        }

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                if (map[i][j] == null) {
                    map[i][j] = new SpaceC(i, j);
                }
            }
        }
    }

    /**
     * Adds the specified {@linkplain RailroadMapObserver observer} to the
     * map. The observer will be notified of significant events involving this
     * map such as when a {@linkplain Route route} has been claimed by a
     * {@linkplain Baron}.
     *
     * @param observer The {@link RailroadMapObserver} being added to the map.
     */
    @Override
    public void addObserver(RailroadMapObserver observer) {
        observersList.add(observer);
    }

    /**
     * Removes the specified {@linkplain RailroadMapObserver observer} from
     * the map. The observer will no longer be notified of significant events
     * involving this map.
     *
     * @param observer The observer to remove from the collection of
     *                 registered observers that will be notified of
     *                 significant events involving this map.
     */
    @Override
    public void removeObserver(RailroadMapObserver observer) {
        observersList.remove(observer);
    }

    /**
     * Returns the number of rows in the map. This is determined by the
     * location of the southernmost {@linkplain Station station} on the map.
     *
     * @return The number of rows in the map.
     */
    @Override
    public int getRows() {
        return numRows;
    }

    /**
     * Returns the number of columns in the map. This is determined by the
     * location of the easternmost {@linkplain Station station} on the map.
     *
     * @return The number of columns in the map.
     */
    @Override
    public int getCols() {
        return numCols;
    }

    /**
     * Returns the {@linkplain Space space} located at the specified
     * coordinates.
     *
     * @param row The row of the desired {@link Space}.
     * @param col The column of the desired {@link Space}.
     * @return The {@link Space} at the specified location, or null if the
     * location doesn't exist on this map.
     */
    @Override
    public Space getSpace(int row, int col) {
        return map[row][col];
    }

    /**
     * Returns the {@linkplain Route route} that contains the
     * {@link Track track} at the specified location (if such a route exists}.
     *
     * @param row The row of the location of one of the {@link Track tracks}
     *            in the route.
     * @param col The column of the location of one of the
     *            {@link Track tracks} in the route.
     * @return The {@link Route} that contains the {@link Track} at the
     * specified location, or null if there is no such {@link Route}.
     */
    @Override
    public Route getRoute(int row, int col) {
        for (Route route : routeList) {
            for (Track track : route.getTracks()) {
                int getRow = track.getRow();
                int getCol = track.getCol();

                if (getRow == row && getCol == col) {
                    return route;
                }
            }
        }
        return null;
    }

    /**
     * Called to update the {@linkplain RailroadMap map} when a
     * {@linkplain Baron} has claimed a {@linkplain Route route}.
     *
     * @param route The {@link Route} that has been claimed.
     */
    @Override
    public void routeClaimed(Route route) {
        for (RailroadMapObserver observer : observersList) {
            observer.routeClaimed(this, route);
        }
    }

    /**
     * Returns the length of the shortest unclaimed {@linkplain Route route}
     * in the map.
     *
     * @return The length of the shortest unclaimed {@link Route}.
     */
    @Override
    public int getLengthOfShortestUnclaimedRoute() {
        int minLength = Integer.MAX_VALUE;
        for (Route route : routeList) {
            if (route.getBaron() == Baron.UNCLAIMED) {
                if (route.getLength() < minLength) {
                    minLength = route.getLength();
                }
            }
        }
        return minLength;
    }

    /**
     * Returns all of the {@link Route Routes} in this map.
     *
     * @return A {@link Collection} of all of the {@link Route Routes} in this
     * RailroadMap.
     */
    @Override
    public Collection<Route> getRoutes() {
        return routeList;
    }
}
