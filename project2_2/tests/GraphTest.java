package student;


import model.Baron;
import model.RailroadBaronsException;
import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;


public class GraphTest {

    @Test
    public void testGraph(){
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/20x25Asia.rbmap");
            RailroadMapC railroadMap = (RailroadMapC)map.readMap(in);
            Graph g = new Graph(railroadMap);
            g.getVertices().forEach((s, v) -> System.out.println(v));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testBFS(){
        MapMakerC map = new MapMakerC();
        try {
            InputStream in = new FileInputStream("maps/20x25Asia.rbmap");
            RailroadMapC railroadMap = (RailroadMapC)map.readMap(in);
            Graph g = new Graph(railroadMap);
            assert g.bFS(new StationC(-1, -1, "North"), new StationC(-1, -1, "South"), Baron.UNCLAIMED);
            assert !g.bFS(new StationC(-1, -1, "North"), new StationC(-1, -1, "West"), Baron.UNCLAIMED);
            assert g.bFS(new StationC(-1, -1, "West"), new StationC(-1, -1, "East"), Baron.UNCLAIMED);
            assert !g.bFS(new StationC(-1, -1, "North"), new StationC(-1, -1, "South"), Baron.RED);
       } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (RailroadBaronsException e) {
            e.printStackTrace();
        }
    }
}
