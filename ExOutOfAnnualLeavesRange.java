public class ExOutOfAnnualLeavesRange extends Exception {
    public ExOutOfAnnualLeavesRange() {
        super("Out of range (Entitled Annual Leaves should be within 0-300)!");
    }

    public ExOutOfAnnualLeavesRange(String message) {
        super(message);
    }
}