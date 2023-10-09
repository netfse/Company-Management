interface Command {
    void execute(String[] cmdParts) throws NumberFormatException, ExInsufficientArguments, ExEmployeeExists, ExOutOfAnnualLeavesRange, ExEmployeeNotFound, ExTeamNotFound, ExTeamMemberExists, ExTeamExists, ExLeaveOverLapped, ExDateHasAlreadyPassed, ExInsufficientLeaveType, ExInsufficientAnnualLeavesBalance, ExInsufficientAnnualLeaves, ExTeamMemberNotFound, ExNotAbleToTakeLeaves;
}

