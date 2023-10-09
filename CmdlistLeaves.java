public class CmdlistLeaves implements Command {

    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound {

        if (cmdParts.length == 1) {
            Company c = Company.getInstance();
            c.listEmployeesLeaves();
        }

        else if (cmdParts.length == 2) {
            Company c = Company.getInstance();

            String name = cmdParts[1];
            Employee e = c.findEmployee(name);

            if (e == null) {
                throw new ExEmployeeNotFound();
            }

            LeaveRecord.listLeaves(e);

        } else {
            throw new ExInsufficientArguments();
        }

    }
}
