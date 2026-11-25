package model;

import java.lang.ref.ReferenceQueue;

public class Request {

    private final String id;

    private final String destination;

    private final String packageId;

    private final long arrivalSequence;

    private final int urgency;

    private final RequestType type;

    private RequestState state;

    public Request(String id,
                    String destination,
                        String packageId,
                            long arrivalSequence,
                                int urgency,
                                    RequestType type) {
        this.id = id;
        this.destination = destination;
        this.packageId = packageId;
        this.arrivalSequence = arrivalSequence;
        this.urgency = urgency;
        this.type = type;
        this.state = RequestState.PENDING;
    }

    public RequestState getState() {
        return state;
    }

    public String getId() {
        return id;
    }

    static void main(String[] args) {

        Request routine = new Request("R1", "C", "P1", 1L, 0, RequestType.ROUTINE);
        Request routine1 = new Request("R2", "B", "P2", 2L, 0, RequestType.ROUTINE);
        Request routine2 = new Request("R3", "B", "P3", 3L, 0, RequestType.ROUTINE);
        Request routine3 = new Request("R3", "B", "P3", 4L, 0, RequestType.ROUTINE);

        PackageTree packageTree = new PackageTree();
        packageTree.insert(new Package("P2", "B"));
        packageTree.insert(new Package("P1", "root"));
        packageTree.insert(new Package("P4", "B"));
        //packageTree.insert(new Package("P3", "B"));


        System.out.println(packageTree.findById("P3"));
        System.out.println(packageTree.findById("P4"));
        System.out.println(packageTree.deleteById("P2"));

    }
}
