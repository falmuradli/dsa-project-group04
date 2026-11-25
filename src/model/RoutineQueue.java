package model;

public class RoutineQueue {

    static class Node {

        Request request;
        Node next;
        Node(Request request) {
            this.request = request;
        }

    }

    Node head;
    Node tail;

    public void enqueue(Request request) {

        if(head == null && tail == null) {
            head = tail = new Node(request);
        } else {
            tail.next = new Node(request);
            tail = tail.next;
        }

    }

    public Request peek() {
        if(head == null) return null;

        return head.request;
    }

    public Request dequeue() {

        if(head == null) return null;

        Request requestToDequeue = head.request;
        head = head.next;
        if(head == null) {
            tail = null;
        }

        return requestToDequeue;
    }

    public boolean removeById(String id) {
        Node current = head;
        Node previous = null;

        while(current != null) {

            if(current.request.getId().equals(id)) {
                if(previous == null) {
                    dequeue();
                    return true;
                }
                previous.next = current.next;
                if(current == tail) tail = previous;
                return true;
            }

            previous = current;
            current = current.next;

        }
        return false;
    }

}
