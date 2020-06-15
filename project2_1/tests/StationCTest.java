package student;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for StationC object
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class StationCTest {

    @Test
    public void getName() {
        StationC station = new StationC(1, 2, "myStation");
        assertEquals("myStation", station.getName());
        assertEquals(1, station.getRow());
        assertEquals(2, station.getCol());
    }
}