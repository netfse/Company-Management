public class ExTeamMemberExists extends Exception {
    public ExTeamMemberExists() {
        super("The employee has joined the team already!");
    }

    public ExTeamMemberExists(String message) {
        super(message);
    }
}
