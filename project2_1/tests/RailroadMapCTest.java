package student;

import model.*;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Test class for RailroadMapC object
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class RailroadMapCTest {

    @Test
    public void getRows() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            assertEquals(13, railroadMap.getRows());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getCols() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            assertEquals(11, railroadMap.getCols());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getSpace() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            Space space1 = railroadMap.getSpace(2, 2);
            assertEquals(2, space1.getRow());
            assertEquals(2, space1.getCol());
            assertEquals(true, space1 instanceof Station);

            Space space2 = railroadMap.getSpace(2, 3);
            assertEquals(2, space2.getRow());
            assertEquals(3, space2.getCol());
            assertEquals(true, space2 instanceof Track);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRoute() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            Route route1 = railroadMap.getRoute(2, 3);
            assertEquals("Crux Station", route1.getOrigin().getName());
            assertEquals("East End Station", route1.getDestination().getName());

            //empty space
            Route route2 = railroadMap.getRoute(3, 1);
            assertEquals(null, route2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getLengthOfShortestUnclaimedRoute() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            assertEquals(7, railroadMap.getLengthOfShortestUnclaimedRoute());
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getRoutes() {
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/simple.rbmap");
            RailroadMap railroadMap = map.readMap(in);
            //Size check
            int routeListSize = railroadMap.getRoutes().size();
            assertEquals(2, routeListSize);

            //check for routes
            Collection<Route> routeList = railroadMap.getRoutes();
            int c = 0;
            for (Route route : routeList) {
                if (c > 0) {
                    assertEquals("Crux Station", route.getOrigin().getName());
                    assertEquals("South Bend Station", route.getDestination().getName());
                } else {
                    assertEquals("Crux Station", route.getOrigin().getName());
                    assertEquals("East End Station", route.getDestination().getName());
                    c++;
                }
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }
}