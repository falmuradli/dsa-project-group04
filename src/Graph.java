import java.util.*;

public class Graph {

    private Map<String, List<Road>> adjacencyList = new HashMap<>();

    public void addVertex(String name) {
        adjacencyList.putIfAbsent(name, new ArrayList<>());
    }

    public void addRoad(String from, String to, int travelTime) {
        if(travelTime < 0) {
            throw new IllegalArgumentException("Travel time can not be negative");
        }

        List<Road> fromRoads = adjacencyList.get(from);
        List<Road> toRoads = adjacencyList.get(to);

        if(fromRoads == null || toRoads == null) {
            throw new IllegalArgumentException("Both buildings must already exist");
        }

        fromRoads.add(new Road(to, travelTime));
        toRoads.add(new Road(from, travelTime));
    }

    public boolean hasVertex(String name) {
        return adjacencyList.containsKey(name);
    }
}
