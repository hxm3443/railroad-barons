package student;

import java.io.*;

import model.RailroadBaronsException;
import model.RailroadMap;

import static org.junit.Assert.*;

/**
 * Test class for MapMakerC object
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class MapMakerCTest {
    MapMakerC map;
    RailroadMap railroadMap;

    public MapMakerCTest () {
        map = new MapMakerC();
    }

    @org.junit.Test
    public void readMap() {
        try {
            InputStream in = new FileInputStream("maps/20x25Asia.rbmap");
            railroadMap = map.readMap(in);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @org.junit.Test
    public void writeMap() {
        try {
            InputStream in = new FileInputStream("maps/20x25Asia.rbmap");
            railroadMap = map.readMap(in);
            OutputStream out = new FileOutputStream("MyMap.rbmap");
            map.writeMap(railroadMap, out);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }
}