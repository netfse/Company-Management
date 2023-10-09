public class ExTeamExists extends Exception {
    public ExTeamExists() {
        super("Team already exists!");
    }

    public ExTeamExists(String message) {
        super(message);
    }
}