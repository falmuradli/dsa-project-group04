package model;

import java.util.*;

public class CampusGraph {

    private Map<String, List<Road>> adjacencyList = new HashMap<>();

    public void addVertex(String name) {
        if(adjacencyList.containsKey(name)) throw new IllegalArgumentException("This building already exists");
        adjacencyList.put(name, new ArrayList<>());
    }

    public void addRoad(String from, String to, int travelTime) {
        if (travelTime < 0) throw new IllegalArgumentException("Travel time can't be negative");


        List<Road> fromRoads = adjacencyList.get(from);
        List<Road> toRoads = adjacencyList.get(to);

        if (fromRoads == null || toRoads == null) throw new IllegalArgumentException("Both buildings must already exist");

        fromRoads.add(new Road(to, travelTime));
        toRoads.add(new Road(from, travelTime));

    }

    public long shortestTravelTime(String from, String to) {
        if (from == null || to == null
                || !adjacencyList.containsKey(from)
                || !adjacencyList.containsKey(to)) {
            throw new IllegalArgumentException("Both buildings must exist");
        }

        Map<String, Long> distance = new HashMap<>();
        for (String building : adjacencyList.keySet()) {
            distance.put(building, Long.MAX_VALUE);
        }
        distance.put(from, 0L);

        Set<String> visited = new HashSet<>();

        while (true) {
            String current = null;
            long bestDistance = Long.MAX_VALUE;

            for (String building : distance.keySet()) {
                long cost = distance.get(building);
                if (!visited.contains(building) && cost < bestDistance) {
                    current = building;
                    bestDistance = cost;
                }
            }

            if (current == null) {
                return -1;
            }
            if (current.equals(to)) {
                return bestDistance;
            }

            visited.add(current);

            for (Road road : adjacencyList.get(current)) {
                long newDistance = bestDistance + road.travelTime;
                if (newDistance < distance.get(road.destination)) {
                    distance.put(road.destination, newDistance);
                }
            }
        }
    }
}
