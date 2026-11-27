package model;

public class Package {

    private final  String id;

    private final String destination;

    public Package(String id, String destination) {
        this.id = id;
        this.destination = destination;
    }

    public String getId() {
        return id;
    }

    public String getDestination() {
        return destination;
    }


}
