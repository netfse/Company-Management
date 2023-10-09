import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Scanner in = new Scanner(System.in);

        System.out.print("Please input the file pathname: ");
        String filepathname = in.nextLine();

        Scanner inFile = null;

        try {

            inFile = new Scanner(new File(filepathname));

            String cmdLine1 = inFile.nextLine();
            String[] cmdLine1Parts = cmdLine1.split("\\|");
            System.out.println("\n> " + cmdLine1);
            SystemDate.createTheInstance(cmdLine1Parts[1]);

            while (inFile.hasNext()) {
                try {
                    String cmdLine = inFile.nextLine().trim();

                    if (cmdLine.equals(""))
                        continue;
                    System.out.println("\n> " + cmdLine);

                    String[] cmdParts = cmdLine.split("\\|");

                    if (cmdParts[0].equals("hire"))
                        new CmdHire().execute(cmdParts);

                    else if (cmdParts[0].equals("setupTeam"))
                        new CmdsetupTeam().execute(cmdParts);

                    else if (cmdParts[0].equals("startNewDay"))
                        new CmdstartNewDay().execute(cmdParts);

                    else if (cmdParts[0].equals("addTeamMember"))
                        new CmdaddTeamMember().execute(cmdParts);

                    else if (cmdParts[0].equals("takeLeave"))
                        new CmdtakeLeave().execute(cmdParts);

                    else if (cmdParts[0].equals("listLeaves"))
                        new CmdlistLeaves().execute(cmdParts);

                    else if (cmdParts[0].equals("listTeams"))
                        new CmdlistTeams().execute(cmdParts);

                    else if (cmdParts[0].equals("listTeamMembers"))
                        new CmdlistTeamMembers().execute(cmdParts);

                    else if (cmdParts[0].equals("listEmployees"))
                        new CmdListEmployees().execute(cmdParts);

                    else if (cmdParts[0].equals("listRoles"))
                        new CmdlistRoles().execute(cmdParts);

                    else if (cmdParts[0].equals("undo"))
                        RecordedCommand.undoOneCommand();

                    else if (cmdParts[0].equals("redo"))
                        RecordedCommand.redoOneCommand();

                    else
                        throw new ExWrongCommand();

                } catch (InputMismatchException e) {
                    System.out.println("File content problem. Program terminated.");
                } catch (ExWrongCommand e) {
                    System.out.println("Unknown command - ignored.");
                } catch (NumberFormatException e) {
                    System.out.println("Wrong number format.");
                } catch (ExInsufficientArguments e) {
                    System.out.println(e.getMessage());
                } catch (ExEmployeeExists e) {
                    System.out.println(e.getMessage());
                } catch (ExOutOfAnnualLeavesRange e) {
                    System.out.println(e.getMessage());
                } catch (ExEmployeeNotFound e) {
                    System.out.println(e.getMessage());
                } catch (ExTeamNotFound e) {
                    System.out.println(e.getMessage());
                } catch (ExTeamMemberExists e) {
                    System.out.println(e.getMessage());
                } catch (ExTeamExists e) {
                    System.out.println(e.getMessage());
                } catch (ExDateHasAlreadyPassed e) {
                    System.out.println(e.getMessage());
                } catch (ExLeaveOverLapped e) {
                    System.out.println(e.getMessage());
                } catch (ExInsufficientLeaveType e) {
                    System.out.println(e.getMessage());
                } catch (ExInsufficientAnnualLeavesBalance e) {
                    System.out.println(e.getMessage());
                } catch (ExInsufficientAnnualLeaves e) {
                    System.out.println(e.getMessage());
                } catch (ExTeamMemberNotFound e) {
                    System.out.println(e.getMessage());
                } catch (ExNotAbleToTakeLeaves e) {
                    System.out.println(e.getMessage());
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        } finally {
            if (inFile != null)
                inFile.close();
            in.close();
        }
    }
}