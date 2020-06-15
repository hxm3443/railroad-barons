package student;

import model.Baron;
import model.Orientation;
import model.Track;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Test class for RouteC object
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class RouteCTest {

    @Test
    public void getBaron() {
        StationC originStation = new StationC(2, 0, "Origin");
        StationC destinationStation = new StationC(2, 4, "Destination");
        RouteC route = new RouteC(originStation, destinationStation, Baron.RED);

        assertEquals(Baron.RED, route.getBaron());
    }

    @Test
    public void getOrigin() {
        StationC originStation = new StationC(2, 0, "Origin");
        StationC destinationStation = new StationC(2, 4, "Destination");
        RouteC route = new RouteC(originStation, destinationStation, Baron.RED);

        assertEquals(originStation, route.getOrigin());
    }

    @Test
    public void getDestination() {
        StationC originStation = new StationC(2, 0, "Origin");
        StationC destinationStation = new StationC(2, 4, "Destination");
        RouteC route = new RouteC(originStation, destinationStation, Baron.RED);

        assertEquals(destinationStation, route.getDestination());
    }

    @Test
    public void getOrientation() {
        //test for horizontal orientation
        StationC originStationH = new StationC(2, 0, "OriginH");
        StationC destinationStationH = new StationC(2, 4, "DestinationH");
        RouteC routeH = new RouteC(originStationH, destinationStationH, Baron.UNCLAIMED);

        assertEquals(Orientation.HORIZONTAL, routeH.getOrientation());

        //test for vertical orientation
        StationC originStationV = new StationC(1, 3, "OriginV");
        StationC destinationStationV = new StationC(4, 3, "DestinationV");
        RouteC routeV = new RouteC(originStationV, destinationStationV, Baron.UNCLAIMED);

        assertEquals(Orientation.VERTICAL, routeV.getOrientation());

    }

    @Test
    public void getTracks() {
        //test with horizontal orientation
        StationC originStationH = new StationC(2, 0, "OriginH");
        StationC destinationStationH = new StationC(2, 4, "DestinationH");
        List<Track> trackListH = new ArrayList<>();
        RouteC routeH = new RouteC(originStationH, destinationStationH, Baron.UNCLAIMED);
        TrackC trackH1 = new TrackC(2, 1, Orientation.HORIZONTAL, routeH);
        TrackC trackH2 = new TrackC(2, 2, Orientation.HORIZONTAL, routeH);
        TrackC trackH3 = new TrackC(2, 3, Orientation.HORIZONTAL, routeH);
        trackListH.add(trackH1);
        trackListH.add(trackH2);
        trackListH.add(trackH3);

        assertEquals(trackListH, routeH.getTracks());

        //test with vertical orientation
        StationC originStationV = new StationC(1, 3, "OriginV");
        StationC destinationStationV = new StationC(4, 3, "DestinationV");
        List<Track> trackListV = new ArrayList<>();
        RouteC routeV = new RouteC(originStationV, destinationStationV, Baron.UNCLAIMED);
        TrackC trackV1 = new TrackC(2, 3, Orientation.VERTICAL, routeV);
        TrackC trackV2 = new TrackC(3, 3, Orientation.VERTICAL, routeV);
        trackListV.add(trackV1);
        trackListV.add(trackV2);

        assertEquals(trackListV, routeV.getTracks());
    }

    @Test
    public void getLength() {
        StationC originStation = new StationC(2, 0, "OriginH");
        StationC destinationStation = new StationC(2, 4, "DestinationH");
        RouteC route = new RouteC(originStation, destinationStation, Baron.UNCLAIMED);

        assertEquals(3, route.getLength());
        }

    @Test
    public void getPointValue() {
        StationC originStation = new StationC(2, 0, "OriginH");
        StationC destinationStation = new StationC(2, 4, "DestinationH");
        RouteC route = new RouteC(originStation, destinationStation, Baron.UNCLAIMED);

        assertEquals(4, route.getPointValue());
    }

    @Test
    public void includesCoordinate() {
        StationC originStation = new StationC(2, 0, "OriginH");
        StationC destinationStation = new StationC(2, 4, "DestinationH");
        RouteC route = new RouteC(originStation, destinationStation, Baron.UNCLAIMED);
        SpaceC space1 = new SpaceC(2, 1);
        SpaceC space2 = new SpaceC(3, 1);

        assertEquals(true, route.includesCoordinate(space1));
        assertEquals(false, route.includesCoordinate(space2));

    }

    @Test
    public void claim() {
        StationC originStation = new StationC(2, 0, "OriginH");
        StationC destinationStation = new StationC(2, 4, "DestinationH");
        RouteC route1 = new RouteC(originStation, destinationStation, Baron.UNCLAIMED);
        RouteC route2 = new RouteC(originStation, destinationStation, Baron.RED);

        assertEquals(Baron.UNCLAIMED, route1.getBaron());
        assertEquals(Baron.RED, route2.getBaron());
    }
}