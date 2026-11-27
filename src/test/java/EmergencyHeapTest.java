
import model.EmergencyHeap;
import model.Request;
import model.RequestType;

public class EmergencyHeapTest {
    private static Request emergency(String id, int urgency, long arrival) {
        return new Request(id, "D", null, arrival, urgency, RequestType.EMERGENCY);
    }

    private static void check(boolean condition, String message) {
        if (!condition) {
            throw new AssertionError(message);
        }
    }

    private static void expectOrder(EmergencyHeap heap, String... ids) {
        for (String id : ids) {
            Request actual = heap.removeHighest();
            check(actual != null && actual.getId().equals(id), "Expected " + id);
        }
        check(heap.isEmpty() && heap.size() == 0, "Heap should be empty.");
        check(heap.removeHighest() == null, "Empty removal should return null.");
    }

    private static void expectInvalid(Runnable action) {
        boolean rejected = false;
        try {
            action.run();
        } catch (IllegalArgumentException exception) {
            rejected = true;
        }
        check(rejected, "Invalid input should be rejected.");
    }

    public static void main(String[] args) {
        EmergencyHeap empty = new EmergencyHeap();
        check(empty.isEmpty() && empty.size() == 0, "New heap should be empty.");
        check(empty.peek() == null, "Empty peek should return null.");
        check(empty.removeHighest() == null, "Empty removal should return null.");
        check(!empty.removeById("missing"), "Unknown ID should return false.");

        EmergencyHeap ordering = new EmergencyHeap();
        ordering.insert(emergency("E1", 3, 1));
        ordering.insert(emergency("E3", 5, 3));
        ordering.insert(emergency("E2", 5, 2));
        check(ordering.peek().getId().equals("E2"), "E2 should be the root.");
        check(ordering.peek().getId().equals("E2") && ordering.size() == 3,
                "Peek must not remove a request.");
        expectOrder(ordering, "E2", "E3", "E1");

        EmergencyHeap duplicates = new EmergencyHeap();
        Request original = emergency("E1", 2, 1);
        check(duplicates.insert(original), "First insertion should succeed.");
        check(!duplicates.insert(emergency("E1", 5, 2)), "Duplicate should fail.");
        check(duplicates.size() == 1 && duplicates.peek() == original,
                "Duplicate must not change the heap.");
        expectOrder(duplicates, "E1");

        EmergencyHeap single = new EmergencyHeap();
        single.insert(emergency("E1", 1, 1));
        check(single.removeById("E1") && single.isEmpty(), "Remove only request.");
        single.insert(emergency("E2", 2, 2));
        expectOrder(single, "E2");

        EmergencyHeap upward = new EmergencyHeap();
        for (int arrival : new int[]{1, 4, 2, 5, 6, 3}) {
            upward.insert(emergency("E" + arrival, 5, arrival));
        }
        check(upward.removeById("E5"), "E5 should be removed.");
        expectOrder(upward, "E1", "E2", "E3", "E4", "E6");

        EmergencyHeap downward = new EmergencyHeap();
        for (int arrival = 1; arrival <= 7; arrival++) {
            downward.insert(emergency("E" + arrival, 5, arrival));
        }
        check(downward.removeById("E2"), "E2 should be removed.");
        expectOrder(downward, "E1", "E3", "E4", "E5", "E6", "E7");

        EmergencyHeap removal = new EmergencyHeap();
        for (int arrival = 1; arrival <= 4; arrival++) {
            removal.insert(emergency("E" + arrival, 5, arrival));
        }
        check(removal.removeById("E4"), "Last entry should be removed.");
        check(!removal.removeById("missing") && removal.size() == 3,
                "Unknown ID must not change the heap.");
        check(removal.removeById("E1"), "Root should be removed by ID.");
        expectOrder(removal, "E2", "E3");

        EmergencyHeap growing = new EmergencyHeap();
        for (int arrival = 30; arrival >= 1; arrival--) {
            growing.insert(emergency("E" + arrival, 4, arrival));
        }
        check(growing.size() == 30, "All requests should fit after resizing.");
        for (int arrival = 1; arrival <= 30; arrival++) {
            Request actual = growing.removeHighest();
            check(actual != null && actual.getId().equals("E" + arrival),
                    "Incorrect order after resizing.");
        }
        check(growing.isEmpty(), "Growing heap should now be empty.");

        EmergencyHeap invalid = new EmergencyHeap();
        expectInvalid(() -> invalid.insert(null));
        expectInvalid(() -> invalid.insert(emergency(null, 3, 1)));
        expectInvalid(() -> invalid.insert(emergency(" ", 3, 1)));
        expectInvalid(() -> invalid.insert(emergency("E1", 0, 1)));
        expectInvalid(() -> invalid.insert(emergency("E1", 6, 1)));
        expectInvalid(() -> invalid.insert(
                new Request("R1", "C", "P1", 1L, 0, RequestType.ROUTINE)));
        expectInvalid(() -> invalid.removeById(null));
        expectInvalid(() -> invalid.removeById(""));
        check(invalid.isEmpty(), "Invalid inputs must not add requests.");

        System.out.println("All emergency heap tests passed.");
    }
}