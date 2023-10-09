public class ExTeamMemberNotFound extends Exception {
    public ExTeamMemberNotFound() {
        super("Team member not found!");
    }

    public ExTeamMemberNotFound(String message) {
        super(message);
    }
}