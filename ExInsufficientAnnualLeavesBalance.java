
public class ExInsufficientAnnualLeavesBalance extends Exception {
    public ExInsufficientAnnualLeavesBalance() {
        super("Insufficient balance of annual leaves.");
    }

    public ExInsufficientAnnualLeavesBalance(String message) {
        super(message);
    }
}
