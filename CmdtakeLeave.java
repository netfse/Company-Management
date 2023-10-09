import java.util.ArrayList;

public class CmdtakeLeave extends RecordedCommand {

    private LeaveRecord leave;
    private Employee e;

    private int originalAnnualLeaves;
    private int originalSickLeaves;

    private int annualLeavesChange = 0;
    private int sickLeavesChange = 0;

    private int daysRange;

    Day leaveStartDay;
    Day leaveEndDay;

    ArrayList<String> teamName;
    ArrayList<String> actingHeadName;

    ArrayList<ActingHead> actingHeadModify;

    @Override
    public void execute(String[] cmdParts)
            throws ExInsufficientArguments, ExEmployeeNotFound, ExDateHasAlreadyPassed, ExLeaveOverLapped,
            ExInsufficientLeaveType, ExInsufficientAnnualLeavesBalance, ExInsufficientAnnualLeaves, ExTeamNotFound,
            ExTeamMemberNotFound, ExNotAbleToTakeLeaves {

        if (cmdParts.length < 5)
            throw new ExInsufficientArguments();

        Company co = Company.getInstance();
        SystemDate d = SystemDate.getInstance();

        String name = cmdParts[1];
        String leaveType = cmdParts[2];

        leaveStartDay = new Day(cmdParts[3]);
        leaveEndDay = new Day(cmdParts[4]);
        daysRange = Day.daysRange(leaveStartDay, leaveEndDay);

        teamName = new ArrayList<>();
        actingHeadName = new ArrayList<>();
        actingHeadModify = new ArrayList<>();

        if (cmdParts.length > 5) {
            for (int i = 5; i < cmdParts.length - 1; i += 2) {
                teamName.add(cmdParts[i]);
                actingHeadName.add(cmdParts[i + 1]);
            }
        }

        e = co.findEmployee(name);

        if (e == null)
            throw new ExEmployeeNotFound();

        originalAnnualLeaves = e.getAnnualLeaves();
        originalSickLeaves = e.getSickLeaves();

        // Check date is later than SystemDate
        // leaveStartDay < SystemDate || leaveEndDay < SystemDate
        if (Day.daysComparison(leaveStartDay, d) == 1 || Day.daysComparison(leaveEndDay, d) == 1) {
            throw new ExDateHasAlreadyPassed("Wrong Date. Leave start date must not be earlier than the system date ("
                    + d.toString() + ")!");
        }

        // Check actingHead if header takes leave

        for (Team t : e.getJoinedTeams()) {
            boolean actingHeadForThisTeam = false;
            Member m = t.searchMemberNameInTeam(name);
            if (m.getRole() instanceof RLeader) {
                for (String s : teamName) {
                    if (t.getTeamName().equals(s)) {
                        actingHeadForThisTeam = true;
                        break;
                    }
                }
                if (!actingHeadForThisTeam) {
                    throw new ExInsufficientArguments(
                            "Missing input:  Please give the name of the acting head for " + t.getTeamName());
                }
            }
        }

        // Modify acting head for some teams
        actingHeadModify = co.ActingHead(leaveStartDay, leaveEndDay, teamName, actingHeadName);

        // Check acting head take leave
        for (Team t : e.getJoinedTeams()) {
            ActingHead aH = t.searchActingHeadNameInTeam(name);

            if (aH != null) {
                if (Day.IsLeaveOverLapped(leaveStartDay, leaveEndDay, aH.getDayStart(), aH.getDayEndDay())) {
                    throw new ExNotAbleToTakeLeaves("Cannot take leave.  " +
                            aH.getPerson().getName() +
                            " is the acting head of " +
                            aH.getactingHeadOfTeam().getTeamName() +
                            " during " + aH.getDayStart() + " to " +
                            aH.getDayEndDay() + "!");
                }
            }
        }

        // Check Leave Over Lapped
        boolean IsLeaveOverLapped = false;
        String line = "";

        for (LeaveRecord l : e.getleaveRecords()) {
            Day anotherleaveStartDay = l.getDayStart();
            Day anotherleaveEndDay = l.getDayEnd();

            // (StartA <= EndB) and (EndA >= StartB)
            if (Day.IsLeaveOverLapped(leaveStartDay, leaveEndDay, anotherleaveStartDay, anotherleaveEndDay)) {
                line = l.toString();
                IsLeaveOverLapped = true;
                break;
            }
        }

        if (IsLeaveOverLapped) {
            throw new ExLeaveOverLapped("Leave overlapped: The leave period " + line + " is found!");
        }

        if (leaveType.equals("AL")) {

            boolean takenBlockLeave = false;

            for (LeaveRecord lr : e.getleaveRecords()) {
                if (lr instanceof LeaveRecord_BL) {
                    takenBlockLeave = true;
                    break;
                }
            }

            if (!takenBlockLeave && originalAnnualLeaves - daysRange < 7) {
                throw new ExInsufficientAnnualLeaves(
                        "The annual leave is invalid.\n" +
                                "The current balance is " + originalAnnualLeaves + " days only.\n" +
                                "The employee has not taken any block leave yet.\n" +
                                "The employee can take at most " + (originalAnnualLeaves - 7)
                                + " days of non-block annual leave\n" +
                                "because of the need to reserve 7 days for a block leave.");
            }

            if (daysRange >= 7) {
                throw new ExInsufficientLeaveType(
                        "To apply annual leave for 7 days or more, please use the Block Leave (BL) type.");
            } else if (originalAnnualLeaves < daysRange) {
                throw new ExInsufficientAnnualLeavesBalance(
                        "Insufficient balance of annual leaves. " + originalAnnualLeaves + " days left only!");
            } else {
                leave = new LeaveRecord_AL(e, leaveStartDay, leaveEndDay);
                e.AnnualLeavesChange(-daysRange);
                annualLeavesChange = -daysRange;
            }
        }

        else if (leaveType.equals("BL")) {
            if (daysRange <= 6) {
                throw new ExInsufficientLeaveType(
                        "To apply annual leave for 6 days or less, you should use the normal Annual Leave (AL) type.");
            } else if (originalAnnualLeaves < daysRange) {
                throw new ExInsufficientAnnualLeavesBalance(
                        "Insufficient balance of annual leaves. " + originalAnnualLeaves + " days left only!");
            } else {
                leave = new LeaveRecord_BL(e, leaveStartDay, leaveEndDay);
                e.AnnualLeavesChange(-daysRange);
                annualLeavesChange = -daysRange;
            }
        }

        else if (leaveType.equals("SL")) {
            if (originalSickLeaves < daysRange) {
                throw new ExInsufficientAnnualLeavesBalance(
                        "Insufficient balance of sick leaves. " + originalSickLeaves + " days left only!");
            } else {
                leave = new LeaveRecord_SL(e, leaveStartDay, leaveEndDay);
                e.SickLeavesChange(-daysRange);
                sickLeavesChange = -daysRange;
            }
        }

        else if (leaveType.equals("NL")) {
            leave = new LeaveRecord_NL(e, leaveStartDay, leaveEndDay);
        }

        e.addLeaveRecord(leave);

        addUndoCommand(this);
        clearRedoList();

        System.out.println("Done.");
    }

    @Override
    public void undoMe() {

        e.removeLeaveRecord(leave);
        e.AnnualLeavesChange(-annualLeavesChange);
        e.SickLeavesChange(-sickLeavesChange);

        Company co = Company.getInstance();

        for (int i = 0; i < teamName.size(); i++) {
            co.removeActingHead(teamName.get(i), actingHeadModify.get(i));
        }

        addRedoCommand(this);
    }

    @Override
    public void redoMe() {

        e.addLeaveRecord(leave);
        e.AnnualLeavesChange(+annualLeavesChange);
        e.SickLeavesChange(+sickLeavesChange);

        Company co = Company.getInstance();

        for (int i = 0; i < teamName.size(); i++) {
            co.addActingHead(teamName.get(i), actingHeadModify.get(i));
        }

        addUndoCommand(this);
    }
}
