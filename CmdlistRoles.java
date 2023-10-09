public class CmdlistRoles implements Command {
    @Override
    public void execute(String[] cmdParts) throws ExInsufficientArguments, ExEmployeeNotFound {

        if (cmdParts.length < 2)
            throw new ExInsufficientArguments();

        Company co = Company.getInstance();
        String name = cmdParts[1];

        Employee e = co.findEmployee(name);

        if (e == null) {
            throw new ExEmployeeNotFound();
        }

        co.listRoles(e);

    }
}
