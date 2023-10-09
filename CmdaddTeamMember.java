public class CmdaddTeamMember extends RecordedCommand {

    private Member person;
    private Team t;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound, ExTeamNotFound, ExTeamMemberExists 
    {

        if (cmdParts.length < 3)
            throw new ExInsufficientArguments();

        Company co = Company.getInstance();

        String teamName = cmdParts[1];
        String Name = cmdParts[2];

        Employee e = co.findEmployee(Name);
        t = co.findTeam(teamName);

        if (e == null) {
            throw new ExEmployeeNotFound();
        }
        if (t == null) {
            throw new ExTeamNotFound();
        }

        Member m = co.findMemberInTeam(t, e);

        if (m != null) {
            throw new ExTeamMemberExists();
        }

        Role r = new RNormalMember();
        person = new Member(t, e, r);

        t.addTeamMember(person);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");

    }

    @Override
    public void undoMe() {
        t.removeTeamMember(person);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        t.addTeamMember(person);
        addUndoCommand(this);
    }
}
