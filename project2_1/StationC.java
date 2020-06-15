package student;

import model.Space;

import java.util.Objects;

/**
 * Represents a train station on the map. A train station is a
 * {@linkplain Space space} that has a name and is at one end (origin) or the
 * other (destination) of at least one train route.
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class StationC extends SpaceC implements model.Station {
    private int row;
    private int col;
    private String stationName;

    /**
     * Constructor
     *
     * @param row         represents the row coordinate of the space's location in the map
     * @param col         represents the column coordinate of the space's location in the map
     * @param stationName the name of the station
     */
    public StationC(int row, int col, String stationName) {
        super(row, col);
        this.row = row;
        this.col = col;
        this.stationName = stationName;
    }

    /**
     * The name of the station, e.g. "TrainsVille Station".
     *
     * @return The name of the station.
     */
    @Override
    public String getName() {
        return stationName;
    }

    /**
     * This equals() method compares two Station objects and checks if they are equal by comparing their row value,
     * column value, and stationName.
     *
     * @param other the Station to compare with
     * @return true if the two Station objects are equal, otherwise false
     */
    @Override
    public boolean equals(Object other) {
        if (!(other instanceof StationC)) {
            return false;
        }
        StationC station = (StationC) other;
        return this.row == station.getRow() && this.col == station.getCol() && this.stationName.equals(station.getName());
    }

    /**
     * Computes the hashCode for the given Station object based on its row value, column value, and stationName.
     *
     * @return distinct integers for distinct objects
     */
    @Override
    public int hashCode() {
        return Objects.hash(row, col, stationName);
    }
}
