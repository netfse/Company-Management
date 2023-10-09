public class ExOverlappedLeaves extends Exception {
    public ExOverlappedLeaves() {
        super("Leave overlapped: ");
    }

    public ExOverlappedLeaves(String message) {
        super(message);
    }
}
