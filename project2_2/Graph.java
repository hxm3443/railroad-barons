package student;

import model.Baron;
import model.RailroadMap;
import model.Route;
import model.Station;

import java.util.*;

/**
 * Class representing a graph of the RailroadBarons map
 *
 * @Author Ellis Wright
 */
public class Graph {
    /**
     * Class representing an edge in the graph
     */
    private class Edge {
        /** The route which acts as the edge */
        private Route edge;
        /** The endpoint to connect to {directed}*/
        private Vertex endpoint;

        /**
         * Create a new Edge with the given endpoint and edge
         * @param edge
         * @param endpoint
         */
        public Edge(Route edge, Vertex endpoint){
            this.edge = edge;
            this.endpoint = endpoint;
        }

        /**
         * Gets the station acting as the endpoint
         * @return the vertex of the endpoint
         */
        public Vertex getEndpoint(){
            return this.endpoint;
        }

        /**
         * Gets the route acting as the edge
         * @return the route
         */
        public Route getEdge(){
            return this.edge;
        }
    }

    /**
     * Class representing a vertex in the graph
     */
    public class Vertex {
        /** The station that represents this node in the grap */
        private Station s;
        /** A list of all the neighbors to this edge */
        private List<Edge> neighbors;

        /**
         * Create a new vertex at the given station
         * @param s the station
         */
        public Vertex(Station s){
            this.s = s;
            this.neighbors = new ArrayList<>();
        }

        /**
         * Add a new neighbor to this vertex. Directed
         * @param r
         * @param destination
         */
        public void addNeighbor(Route r, Station destination){
            //I cant help but feel like there is going to be some future problems with accessing graph in this manner
            Edge e = new Edge(r, graph.get(destination));
            this.neighbors.add(e);
        }

        /**
         * Representation of this vertex and its neighbors as a string
         * @return
         */
        public String toString(){
            return this.s.getName();
        }
    }

    /** The map of all the vertices in this graph */
    private Map<Station, Vertex> graph;

    /**
     * Create a new graph from the given railroad map. Automatically adds all stations and edges and
     * adds far endpoints for searches
     * @param map
     */
    public Graph(RailroadMap map){
        Collection<Route> routes = map.getRoutes();
        Set<Station> stations = new HashSet<>();
        routes.forEach(r -> {
            stations.add(r.getOrigin());
            stations.add(r.getDestination());
        });
        graph = new HashMap<>();
        Station[] s = {new StationC(-1, -1, "West"),
                new StationC(-1, -1, "East"),
                new StationC(-1, -1, "North"),
                new StationC(-1, -1, "South")};

        for (int i = 0; i < 4; i++){
            graph.put(s[i], new Vertex(s[i]));
        }

        Edge e;
        stations.forEach(st -> graph.put(st, new Vertex(st)));
        for (Route route : routes){
            e = new Edge(route, graph.get(route.getDestination()));
            graph.get(route.getOrigin()).addNeighbor(route, route.getDestination());
            graph.get(route.getDestination()).addNeighbor(route, route.getOrigin());
        }

        stations.forEach(station -> {
            if (station.getCol() == 0){
                graph.get(s[0]).addNeighbor(null, station);
            }else if (station.getCol() == map.getCols() - 1){
                graph.get(station).addNeighbor(null, s[1]);
            }
            if (station.getRow() == 0){
                graph.get(s[2]).addNeighbor(null, station);
            }else if (station.getRow() == map.getRows() - 1){
                graph.get(station).addNeighbor(null, s[3]);
            }
        });

    }

    /**
     * Get the vertices of this graph
     * @return list of vertices
     */
    public Map<Station, Vertex> getVertices(){
        return this.graph;
    }

    /**
     * Helper function for the bFS method. Performs a bfs search on the graph based on what the given baron
     * owns.
     * @param vertices
     * @param goal
     * @param b
     * @return a map of the path taken to the goal
     */
    private Map<Vertex, Vertex> visitBFS(Queue<Vertex> vertices, Station goal, Baron b){
        Map<Vertex, Vertex> path = new HashMap<>();
        path.put(vertices.peek(), null);
        Set<Vertex> visited = new HashSet<>();
        visited.add(vertices.peek());
        //Might cause trouble?
        while (vertices.size() != 0 && !vertices.peek().s.equals(goal)){
            Vertex next = vertices.poll();
            for (Edge e : next.neighbors){
                if ((e.getEdge() == null || e.getEdge().getBaron().equals(b)) && !visited.contains(e.getEndpoint())){
                    vertices.add(e.getEndpoint());
                    visited.add(e.getEndpoint());
                    path.put(e.getEndpoint(), next);
                }
            }
        }
        return path;
    }

    /**
     * Complete a bfs search on the map with the given starting point and goal. The baron tells the
     * algorithm whether the edge is part of that specific baron's claimed routes map by checking if they
     * own the edge before adding it to the neighbors list.
     * @param start
     * @param goal
     * @param b
     * @return if the start and goal connect
     */
    public boolean bFS(Station start, Station goal, Baron b){
        Queue<Vertex> vertices = new LinkedList<>();
        vertices.add(graph.get(start));
        Map<Vertex, Vertex> bfs = visitBFS(vertices, goal, b);
        return bfs.containsKey(graph.get(goal));
    }

    /**
     * Class used to store two pieces of info
     */
    public class Vector{
        private int a;
        private boolean b;
        public Vector(int a, boolean b){
            this.a = a;
            this.b = b;
        }
        public int getA(){return this.a;}
        public boolean getB(){return this.b;}
    }

    /**
     * Performs a BFS and returns the length of the route and whether the goal was there
     * @param start
     * @param goal
     * @param b
     * @return the length and whether the goal was connected
     */
    public Vector bFSLength(Station start, Station goal, Baron b){
        Queue<Vertex> vertices = new LinkedList<>();
        vertices.add(graph.get(start));
        Map<Vertex, Vertex> bfs = visitBFS(vertices, goal, b);
        if (!bfs.containsKey(graph.get(goal))){
            return new Vector(-1, false);
        }
        Vertex tmp = graph.get(goal);
        int counter = 0;
        while (tmp != null){
            tmp = bfs.get(tmp);
            counter ++;
        }
        return new Vector(counter, true);
    }
}
