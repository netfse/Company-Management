public class CmdHire extends RecordedCommand {

    private Employee e;

    @Override
    public void execute(String[] cmdParts) throws NumberFormatException, ExInsufficientArguments, ExEmployeeExists, ExOutOfAnnualLeavesRange {

        if (cmdParts.length < 3)
            throw new ExInsufficientArguments();

        Company co = Company.getInstance();

        String name = cmdParts[1];
        int annualLeaves = Integer.parseInt(cmdParts[2]);

        if (annualLeaves < 0 || annualLeaves > 300) {
            throw new ExOutOfAnnualLeavesRange();
        }

        if (co.findEmployee(name) != null) {
            throw new ExEmployeeExists();
        }

        e = new Employee(name, annualLeaves);
        co.addEmployee(e);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");

    }

    @Override
    public void undoMe() {
        Company co = Company.getInstance();
        co.removeEmployee(e);
        addRedoCommand(this);
    }

    @Override
    public void redoMe() {
        Company co = Company.getInstance();
        co.addEmployee(e);
        addUndoCommand(this);
    }

}
