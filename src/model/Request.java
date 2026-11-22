package model;

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

    static void main(String[] args) {
        Request routine = new Request("R1", "C", "P1", 1L, 0, RequestType.ROUTINE);
        System.out.println(routine.getState());
        PackageTree packageTree = new PackageTree();
        System.out.println(packageTree.insert(new Package("P1", "root")));

        Request routine1 = new Request("R2", "B", "P2", 2L, 0, RequestType.ROUTINE);
        System.out.println(packageTree.insert(new Package("P2", "B")));
    }
}
