package main.java.model;

import java.util.ArrayList;
import java.util.List;

public class VehicleList {
    private static class Node {
        Vehicle vehicle;
        Node next;
        Node(Vehicle vehicle){
            this.vehicle = vehicle;
        }
    }

    private Node head,tail;
    private int size;
    int size(){
        return size;
    }

    public boolean insert(Vehicle vehicle){
        if(find(vehicle.getId()) != null) return false;

        Node newVehicle = new Node(vehicle);

        if(head == null){
            head = tail= newVehicle;
        } else {
            tail.next = newVehicle;
            tail = newVehicle;
        }
        size++;
        return true;
    }

    public Vehicle find(String id) {
        for (Node vehicle = head; vehicle != null; vehicle = vehicle.next){
            if(vehicle.vehicle.getId().equals(id)){
                return vehicle.vehicle;
            }
        }
        return null;
    }


    public boolean assign(String id, String requestId) {
        Vehicle vehicle = find(id);
        if(vehicle == null) {
            return false;
        }
        vehicle.assign(requestId);
        return true;
    }


    public boolean delete(String id) {
        Node previous = null;
        for(Node current = head; current!=null; previous=current,current = current.next) {
            if(current.vehicle.getId().equals(id)){
                if(previous == null) {
                    head = current.next;
                } else{
                    previous.next = current.next;
                }
                if(current == tail){
                    tail = previous;
                }
                size--;
                return true;
            }
        }
        return false;
    }


    public List<Vehicle> toList() {
        List<Vehicle> result = new ArrayList<>();
        for(Node current = head; current != null; current = current.next) {
            result.add(current.vehicle);
        }
        return result;
    }

}

