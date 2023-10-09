import java.util.ArrayList;
import java.util.Collections;

public class Company {

    private ArrayList<Employee> allEmployees;
    private ArrayList<Team> allTeams;

    private static Company instance = new Company();

    private Company() {
        allEmployees = new ArrayList<>();
        allTeams = new ArrayList<>();
    }

    public static Company getInstance() {
        return instance;
    }

    public Employee createEmployee(String n, int a) {
        Employee e = new Employee(n, a);
        allEmployees.add(e);
        Collections.sort(allEmployees);
        return e;
    }

    public Team createTeam(String Tn, String n) {
        try {
            Employee e = findEmployee(n);

            if (e == null) {
                throw new ExEmployeeNotFound();
            }

            Team t = new Team(Tn, e);
            allTeams.add(t);
            Collections.sort(allTeams);

            return t;
        } catch (ExEmployeeNotFound e) {
            System.out.println(e.getMessage());
            return null;
        }
    }

    public void addEmployee(Employee e) {
        allEmployees.add(e);
    }

    public void removeEmployee(Employee e) {
        allEmployees.remove(e);
    }

    public void addTeam(Team t) {
        allTeams.add(t);
    }

    public void removeTeam(Team t) {
        allTeams.remove(t);
    }

    public void listEmployees() {
        Collections.sort(allEmployees);
        for (Employee e : allEmployees) {
            System.out.println(e.toString());
        }
    }

    public void listEmployeesLeaves() {
        Collections.sort(allEmployees);
        for (Employee e : allEmployees) {
            System.out.println(e.getName() + ":");
            LeaveRecord.listLeaves(e);
        }
    }

    public void listTeams() {
        Collections.sort(allTeams);
        Team.list(allTeams);
    }

    public void listTeamMembers() {
        Collections.sort(allTeams);
        Team.TeamMemberList(allTeams);
    }

    public Employee findEmployee(String n) {
        Employee e = Employee.searchEmployee(allEmployees, n);
        return e;
    }

    public Team findTeam(String Tn) {
        Team t = Team.searchTeam(allTeams, Tn);
        return t;
    }

    public Member findMemberInTeam(Team t, Employee e) {
        Member m = t.searchMemberInTeam(e);
        return m;
    }

    public Member findMemberNameInTeam(Team t, String eName) {
        Member m = t.searchMemberNameInTeam(eName);
        return m;
    }

    public void listRoles(Employee e) {

        boolean NoRole = true;

        for (Team t : allTeams) {
            Member m = t.searchMemberInTeam(e);

            if (m != null) {
                NoRole = false;
                System.out.println(t.getTeamName() + " " + m.getRoleName());
            }
        }

        if (NoRole) {
            System.out.println("No role");
        }
    }

    public ArrayList<ActingHead> ActingHead(Day leaveStartDay, Day leaveEndDay, ArrayList<String> teamName,
            ArrayList<String> actingHeadName)
            throws ExEmployeeNotFound, ExTeamNotFound, ExTeamMemberNotFound, ExLeaveOverLapped {

        for (int i = 0; i < teamName.size(); i++) {
            Team t = findTeam(teamName.get(i));
            if (t == null) {
                throw new ExTeamNotFound();
            }
            else {
                Member m = t.searchMemberNameInTeam(actingHeadName.get(i));

                if (m == null) {
                    throw new ExTeamMemberNotFound("Employee (" + actingHeadName.get(i) + ")" + " not found for " + t.getTeamName() + "!");
                }
            }
        }

        ArrayList<ActingHead> aHList = new ArrayList<>();

        for (int i = 0; i < teamName.size(); i++) {
            Team t = findTeam(teamName.get(i));
            Member m = findMemberNameInTeam(t, actingHeadName.get(i));
            ActingHead aH = Team.ActingHead(leaveStartDay, leaveEndDay, t, m);
            aHList.add(aH);
        }
        
        return aHList;
    }

    public void removeActingHead(String teamName, ActingHead aH) {
        Team t = findTeam(teamName);
        Team.removeActingHead(t, aH);
    }

    public void addActingHead(String teamName, ActingHead aH) {
        Team t = findTeam(teamName);
        Team.addActingHead(t, aH);
    }
}
