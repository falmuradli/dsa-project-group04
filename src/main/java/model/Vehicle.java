package main.java.model;

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
    public String getAssignedRequestId(){
        return assignedRequestId;
    }
    public void assign(String requestId){
        this.assignedRequestId=requestId;
    }
    public void release(String newLocation){
        this.assignedRequestId =null;
        this.location = newLocation;
    }
    public boolean canHandle(RequestType type){
        return type == RequestType.ROUTINE ? canHandleRoutine : canHandleEmergency;
    }
    @Override 
    public String toString(){
        String caps = canHandleRoutine && canHandleEmergency ? "BOTH" : canHandleRoutine ? "ROUTINE" : "EMERGENCY";
        return id+ "at=" +location+" caps="+caps+" "+(isAvailable() ? "AVAILABLE" : "BUSY("+assignedRequestId+")"); 
    }
}
