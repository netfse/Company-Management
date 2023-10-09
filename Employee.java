import java.util.ArrayList;

public class Employee implements Comparable<Employee> {
    private String name;
    private int annualLeaves;
    private int sickLeaves;

    private ArrayList<LeaveRecord> leaveRecords;
    private ArrayList<Team> joinedTeams;

    public Employee(String n, int yle) {
        name = n;
        annualLeaves = yle;
        sickLeaves = 135;

        leaveRecords = new ArrayList<>();
        joinedTeams = new ArrayList<>();
    }

    public void addLeaveRecord(LeaveRecord lR) {
        leaveRecords.add(lR);
    }

    public void removeLeaveRecord(LeaveRecord lR) {
        leaveRecords.remove(lR);
    }

    public String getName() {
        return name;
    }

    public int getAnnualLeaves() {
        return annualLeaves;
    }

    public int getSickLeaves() {
        return sickLeaves;
    }

    public ArrayList<LeaveRecord> getleaveRecords() {
        return leaveRecords;
    }

    public ArrayList<Team> getJoinedTeams() {
        return joinedTeams;
    }

    @Override
    public int compareTo(Employee another) {
        return this.name.compareTo(another.name);
    }

    @Override
    public String toString() {
        return name + " (Entitled Annual Leaves: " + annualLeaves + " days)";
    }

    public static Employee searchEmployee(ArrayList<Employee> list, String nameToSearch) {
        for (Employee e : list) {
            if (nameToSearch.equals(e.getName()))
                return e;
        }
        return null;
    }

    public void AnnualLeavesChange(int alc) {
        annualLeaves += alc;
    }

    public void SickLeavesChange(int slc) {
        sickLeaves += slc;
    }

    public void addJoinedTeam(Team team) {
        joinedTeams.add(team);
    }

    public void removeJoinedTeam(Team team) {
        joinedTeams.remove(team);
    }

}
