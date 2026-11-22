package model;

public class Vehicle {

    private final String id;

    private String location;

    private final boolean canHandleRoutine;

    private final boolean canHandleEmergency;

    private String assignedRequestId;

    public Vehicle(String id, String location, boolean canHandleRoutine, boolean canHandleEmergency) {
        this.id = id;
        this.location = location;
        this.canHandleRoutine = canHandleRoutine;
        this.canHandleEmergency = canHandleEmergency;
        this.assignedRequestId = null;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }

    public boolean isAvailable() {
        return assignedRequestId == null;
    }
}
