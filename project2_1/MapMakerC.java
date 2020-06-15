package student;

import model.*;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * A class that can load and save {@linkplain RailroadMap maps}.
 * Maps files are in the format:
 * <ol>
 * <li>&lt;station number&gt; &lt;row&gt; &lt;col&gt; &lt;station
 * name&gt;</li>
 * <li>##ROUTES##</li>
 * <li>&lt;origin station number&gt; &lt;destination station number&gt;
 * &lt;owner&gt;</li>
 * </ol>
 *
 * @author Himani Munshi hxm3443@g.rit.edu
 */
public class MapMakerC implements MapMaker {

    /**
     * Loads a {@linkplain RailroadMap map} using the data in the given
     * {@linkplain InputStream input stream}.
     *
     * @param in The {@link InputStream} used to read the {@link RailroadMap
     *           map} data.
     * @return The {@link RailroadMap map} read from the given
     * {@link InputStream}.
     * @throws RailroadBaronsException If there are any problems reading the
     *                                 data from the {@link InputStream}.
     */
    @Override
    public RailroadMap readMap(InputStream in) throws RailroadBaronsException {
        List<Station> stationList = new ArrayList<>();
        List<Route> routeList = new ArrayList<>();
        RailroadMap railroadMap;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(in))) {
            String line = reader.readLine();
            while (!line.equals("##ROUTES##")) {
                String[] stationInfo = line.split(" ");
                String result = "";
                for (int i = 3; i < stationInfo.length; i++) {
                    if (i == stationInfo.length - 1) {
                        result += stationInfo[i];
                    } else {
                        result += stationInfo[i] + " ";
                    }
                }
                StationC station = new StationC(Integer.parseInt(stationInfo[1]), Integer.parseInt(stationInfo[2]), result);
                stationList.add(station);
                line = reader.readLine();
            }
            line = reader.readLine();
            while (line != null) {
                String[] routeInfo = line.split(" ");
                Station origin = stationList.get(Integer.parseInt(routeInfo[0]));
                Station destination = stationList.get(Integer.parseInt(routeInfo[1]));
                if (routeInfo[2].equals("UNCLAIMED")) {
                    RouteC route = new RouteC(origin, destination, Baron.UNCLAIMED);
                    routeList.add(route);
                } else if (routeInfo[2].equals("RED")) {
                    RouteC route = new RouteC(origin, destination, Baron.RED);
                    routeList.add(route);
                } else if (routeInfo[2].equals("BLUE")) {
                    RouteC route = new RouteC(origin, destination, Baron.BLUE);
                    routeList.add(route);
                } else if (routeInfo[2].equals("YELLOW")) {
                    RouteC route = new RouteC(origin, destination, Baron.YELLOW);
                    routeList.add(route);
                } else if (routeInfo[2].equals("GREEN")) {
                    RouteC route = new RouteC(origin, destination, Baron.GREEN);
                    routeList.add(route);
                }
                line = reader.readLine();
            }
            railroadMap = new RailroadMapC(stationList, routeList);
            return railroadMap;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Writes the specified {@linkplain RailroadMap map} in the Railroad
     * Barons map file format to the given {@linkplain OutputStream output
     * stream}. The written map should include an accurate record of any
     * routes that have been claimed, and by which {@linkplain Baron}.
     *
     * @param map The {@link RailroadMap map} to write out to the
     *            {@link OutputStream}.
     * @param out The {@link OutputStream} to which the
     *            {@link RailroadMap map} data should be written.
     * @throws RailroadBaronsException If there are any problems writing the
     *                                 data to the {@link OutputStream}.
     */
    @Override
    public void writeMap(RailroadMap map, OutputStream out) throws RailroadBaronsException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(out));
        HashMap<Integer, Station> stationInfo = new HashMap<>();
        HashSet<Station> trackStationName = new HashSet<>();
        int i = 0;
        //HashMap storing station ID number as key and StationName as value
        for (Route route : map.getRoutes()) {
            Station originStation = route.getOrigin();
            if (!stationInfo.containsValue(originStation)) {
                stationInfo.put(i, originStation);
                i++;
            }

            Station destinationName = route.getDestination();
            if (!stationInfo.containsValue(destinationName)) {
                stationInfo.put(i, destinationName);
                i++;
            }
        }

        //collecting information for stations and writing stations to the file
        for (Route route : map.getRoutes()) {
            int originRow = route.getOrigin().getRow();
            int originCol = route.getOrigin().getCol();
            Station originStation = route.getOrigin();
            int originStationNum = 0;

            for (int key : stationInfo.keySet()) {
                if (stationInfo.get(key).equals(originStation)) {
                    originStationNum = key;
                    break;
                }
            }

            if (!trackStationName.contains(originStation)) {
                writer.print(originStationNum + " ");
                writer.print(originRow + " ");
                writer.print(originCol + " ");
                writer.print(originStation.getName());
                trackStationName.add(originStation);
                writer.println();
            }


            int destinationRow = route.getDestination().getRow();
            int destinationCol = route.getDestination().getCol();
            Station destinationStation = route.getDestination();
            int destinationStationNum = 0;

            for (int key : stationInfo.keySet()) {
                if (stationInfo.get(key).equals(destinationStation)) {
                    destinationStationNum = key;
                    break;
                }
            }

            if (!trackStationName.contains(destinationStation)) {
                writer.print(destinationStationNum + " ");
                writer.print(destinationRow + " ");
                writer.print(destinationCol + " ");
                writer.print(destinationStation.getName());
                writer.println();
                trackStationName.add(destinationStation);
            }

        }

        writer.println("##ROUTES##");

        for (Route route : map.getRoutes()) {
            Station originStation = route.getOrigin();
            Station destinationStation = route.getDestination();

            //<owner>
            Baron claimant = route.getBaron();

            //<origin station number>
            int originStationNum = 0;
            for (int key : stationInfo.keySet()) {
                if (stationInfo.get(key).equals(originStation)) {
                    originStationNum = key;
                    break;
                }
            }

            //<destination station number>
            int destinationStationNum = 0;
            for (int key : stationInfo.keySet()) {
                if (stationInfo.get(key).equals(destinationStation)) {
                    destinationStationNum = key;
                    break;
                }
            }

            writer.print(originStationNum + " ");
            writer.print(destinationStationNum + " ");
            writer.print(claimant);

            writer.println();

        }

        writer.close();
    }
}
