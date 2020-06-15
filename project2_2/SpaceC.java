package student;

/**
 * Represents a space on the Railroad Barons map.
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class SpaceC implements model.Space {
    private int row;
    private int col;

    /**
     * Constructor
     *
     * @param row represents the row coordinate of the space's location in the map
     * @param col represents the column coordinate of the space's location in the map
     */
    public SpaceC(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * Returns the row of the space's location in the map.
     *
     * @return The row of the space's location in the map.
     */
    @Override
    public int getRow() {
        return row;
    }

    /**
     * Returns the column of the space's location in the map.
     *
     * @return The column of the space's location in the map.
     */
    @Override
    public int getCol() {
        return col;
    }

    /**
     * Returns true if the other space is occupying the same physical location
     * in the map as this space.
     *
     * @param other The other space to which this space is being compared for
     *              collocation.
     * @return True if the two spaces are in the same physical location (row
     * and column) in the map; false otherwise.
     */
    @Override
    public boolean collocated(model.Space other) {
        return this.row == other.getRow() && this.col == other.getCol();
    }


}
