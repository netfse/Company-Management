public class ExInsufficientLeaveType extends Exception {
    public ExInsufficientLeaveType() {
        super("Insufficient leave type!");
    }

    public ExInsufficientLeaveType(String message) {
        super(message);
    }
}
