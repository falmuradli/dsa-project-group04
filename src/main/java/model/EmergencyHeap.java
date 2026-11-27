package model;

public class EmergencyHeap {
    private Request[] heap;
    private int size;

    public EmergencyHeap() {
        heap = new Request[10];
        size = 0;
    }

    public int size() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public boolean insert(Request request) {
        validate(request);

        if (findIndex(request.getId()) != -1) {
            return false;
        }

        ensureCapacity();
        heap[size] = request;
        size++;
        siftUp(size - 1);
        return true;
    }

    public Request peek() {
        if (isEmpty()) {
            return null;
        }
        return heap[0];
    }

    public Request removeHighest() {
        if (isEmpty()) {
            return null;
        }
        return removeAt(0);
    }

    public boolean removeById(String id) {
        if (id == null || id.isBlank()) {
            throw new IllegalArgumentException("Request ID cannot be empty.");
        }

        int index = findIndex(id);
        if (index == -1) {
            return false;
        }

        removeAt(index);
        return true;
    }

    private void validate(Request request) {
        if (request == null) {
            throw new IllegalArgumentException("Request cannot be null.");
        }
        if (request.getId() == null || request.getId().isBlank()) {
            throw new IllegalArgumentException("Request ID cannot be empty.");
        }
        if (request.getType() != RequestType.EMERGENCY) {
            throw new IllegalArgumentException("Only emergency requests are allowed.");
        }
        if (request.getState() != RequestState.PENDING) {
            throw new IllegalArgumentException("Only pending requests are allowed.");
        }
        if (request.getUrgency() < 1 || request.getUrgency() > 5) {
            throw new IllegalArgumentException("Urgency must be between 1 and 5.");
        }
    }

    private boolean higherPriority(Request a, Request b) {
        if (a.getUrgency() != b.getUrgency()) {
            return a.getUrgency() > b.getUrgency();
        }
        return a.getArrivalSequence() < b.getArrivalSequence();
    }

    private void siftUp(int index) {
        while (index > 0) {
            int parent = (index - 1) / 2;

            if (!higherPriority(heap[index], heap[parent])) {
                break;
            }

            swap(index, parent);
            index = parent;
        }
    }

    private void siftDown(int index) {
        while (index < size / 2) {
            int left = 2 * index + 1;
            int right = left + 1;
            int bestChild = left;

            if (right < size && higherPriority(heap[right], heap[left])) {
                bestChild = right;
            }

            if (!higherPriority(heap[bestChild], heap[index])) {
                break;
            }

            swap(index, bestChild);
            index = bestChild;
        }
    }

    private Request removeAt(int index) {
        Request removed = heap[index];

        heap[index] = heap[size - 1];
        heap[size - 1] = null;
        size--;

        if (index < size) {
            int parent = (index - 1) / 2;

            if (index > 0 && higherPriority(heap[index], heap[parent])) {
                siftUp(index);
            } else {
                siftDown(index);
            }
        }

        return removed;
    }

    private int findIndex(String id) {
        for (int i = 0; i < size; i++) {
            if (heap[i].getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }

    private void ensureCapacity() {
        if (size < heap.length) {
            return;
        }

        Request[] larger = new Request[heap.length * 2];
        for (int i = 0; i < size; i++) {
            larger[i] = heap[i];
        }
        heap = larger;
    }

    private void swap(int first, int second) {
        Request temporary = heap[first];
        heap[first] = heap[second];
        heap[second] = temporary;
    }
}