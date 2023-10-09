public class CmdsetupTeam extends RecordedCommand {
    private Team t;

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound, ExTeamExists 
    {

        if (cmdParts.length < 3)
            throw new ExInsufficientArguments();
        Company co = Company.getInstance();

        String teamName = cmdParts[1];
        String Name = cmdParts[2];

        if (co.findEmployee(Name) == null) {
            throw new ExEmployeeNotFound();
        }
        if (co.findTeam(teamName) != null) {
            throw new ExTeamExists();
        }

        t = co.createTeam(teamName, Name);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        co.removeTeam(t);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        co.addTeam(t);
        addUndoCommand(this);
    }
}
