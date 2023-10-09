
public class ExInsufficientAnnualLeaves extends Exception {
    public ExInsufficientAnnualLeaves() {
        super("Insufficient annual leaves!");
    }

    public ExInsufficientAnnualLeaves(String message) {
        super(message);
    }
}
