public class ExEmployeeExists extends Exception {
    public ExEmployeeExists() {
        super("Employee already exists!");
    }

    public ExEmployeeExists(String message) {
        super(message);
    }
}
