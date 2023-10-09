public class ExDateHasAlreadyPassed extends Exception {
    public ExDateHasAlreadyPassed() {
        super("Date Has Already Passed!");
    }

    public ExDateHasAlreadyPassed(String message) {
        super(message);
    }
}
