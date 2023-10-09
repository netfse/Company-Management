public class CmdstartNewDay extends RecordedCommand {
    private Day PrevdateSetup;
    private String NewdateSetup;

    @Override
    public void execute(String[] cmdParts) {
        NewdateSetup = cmdParts[1];

        PrevdateSetup = SystemDate.getInstance().clone();
        SystemDate.getInstance().set(NewdateSetup);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {
        SystemDate.getInstance().set(PrevdateSetup.toString());
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        SystemDate.getInstance().set(NewdateSetup);
        addUndoCommand(this);
    }
}
