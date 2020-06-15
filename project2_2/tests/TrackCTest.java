package student;

import model.Baron;
import model.Orientation;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Test class for TrackC object
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class TrackCTest {

    @Test
    public void getOrientation() {
        //test with horizontal orientation
        StationC originStationH = new StationC(2, 0, "OriginH");
        StationC destinationStationH = new StationC(2, 4, "DestinationH");
        RouteC routeH = new RouteC(originStationH, destinationStationH, Baron.UNCLAIMED);
        TrackC trackH = new TrackC(2, 1, Orientation.HORIZONTAL, routeH);

        assertEquals(Orientation.HORIZONTAL, trackH.getOrientation());


        //test with vertical orientation
        StationC originStationV = new StationC(1, 3, "OriginV");
        StationC destinationStationV = new StationC(4, 3, "DestinationV");
        RouteC routeV = new RouteC(originStationV, destinationStationV, Baron.UNCLAIMED);
        TrackC trackV = new TrackC(2, 3, Orientation.VERTICAL, routeV);

        assertEquals(Orientation.VERTICAL, trackV.getOrientation());
    }

    @Test
    public void getBaron() {
        StationC originStationH = new StationC(2, 0, "OriginH");
        StationC destinationStationH = new StationC(2, 4, "DestinationH");
        RouteC routeH = new RouteC(originStationH, destinationStationH, Baron.UNCLAIMED);
        TrackC trackH = new TrackC(2, 1, Orientation.HORIZONTAL, routeH);

        assertEquals(Baron.UNCLAIMED, trackH.getBaron());

    }

    @Test
    public void getRoute() {
        StationC originStationH = new StationC(2, 0, "OriginH");
        StationC destinationStationH = new StationC(2, 4, "DestinationH");
        RouteC routeH = new RouteC(originStationH, destinationStationH, Baron.UNCLAIMED);
        TrackC trackH = new TrackC(2, 1, Orientation.HORIZONTAL, routeH);

        assertEquals(routeH, trackH.getRoute());
    }
}