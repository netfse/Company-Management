import java.util.ArrayList;
import java.util.Collections;

public class Team implements Comparable<Team> {
    private String teamName;
    private Employee head;
    private Day dateSetup;

    private ArrayList<Member> teamMembers;
    private ArrayList<ActingHead> actingHeads;

    public Team(String n, Employee hd) {
        teamName = n;
        head = hd;
        dateSetup = SystemDate.getInstance().clone();

        teamMembers = new ArrayList<>();
        actingHeads = new ArrayList<>();

        Role r = new RLeader();
        Member m = new Member(this, head, r);
        this.addTeamMember(m);
    }

    public static void list(ArrayList<Team> list) {

        System.out.printf("%-30s%-10s%-13s\n", "Team Name", "Leader", "Setup Date");
        for (Team t : list)
            System.out.printf("%-30s%-10s%-13s\n", t.teamName, t.head.getName(), t.dateSetup.toString());

    }

    public static void TeamMemberList(ArrayList<Team> list) {

        for (Team t : list) {
            Collections.sort(t.teamMembers);

            System.out.println(t.teamName + ":");
            for (Member m : t.teamMembers) {
                System.out.println(m.toString());
            }

            if (t.actingHeads.size() > 0) {
                Collections.sort(t.actingHeads);

                System.out.println("Acting heads:");
                for (ActingHead aH : t.actingHeads) {
                    System.out.println(aH.toString());
                }
            }
        }
    }

    public int compareTo(Team another) {
        return this.teamName.compareTo(another.teamName);
    }

    public static Team searchTeam(ArrayList<Team> list, String nameToSearch) {
        for (Team t : list) {
            if (nameToSearch.equals(t.teamName))
                return t;
        }
        return null;
    }

    public Member searchMemberInTeam(Employee e) {

        for (Member member : teamMembers) {
            if (e == member.getPerson())
                return member;
        }
        return null;
    }

    public Member searchMemberNameInTeam(String eName) {

        for (Member member : teamMembers) {
            if (eName.equals(member.getPerson().getName()))
                return member;
        }
        return null;
    }

    public ActingHead searchActingHeadNameInTeam(String eName) {

        for (ActingHead aH : actingHeads) {
            if (eName.equals(aH.getPerson().getName()))
                return aH;
        }
        return null;
    }

    public void addTeamMember(Member e) {
        teamMembers.add(e);
        e.getPerson().addJoinedTeam(this);
    }

    public void removeTeamMember(Member e) {
        teamMembers.remove(e);
        e.getPerson().removeJoinedTeam(this);
    }

    public String getTeamName() {
        return teamName;
    }

    public static ActingHead ActingHead(Day leaveStartDay,Day leaveEndDay,Team t, Member teamMember)
            throws ExTeamMemberNotFound, ExLeaveOverLapped {

        for (LeaveRecord lr : teamMember.getPerson().getleaveRecords()) {
            if (Day.IsLeaveOverLapped(leaveStartDay, leaveEndDay, lr.getDayStart(), lr.getDayEnd())) {
                throw new ExLeaveOverLapped(teamMember.getPerson().getName() + " is on leave during " + lr.toString() + "!");
            }
        }

        ActingHead aH = new ActingHead(leaveStartDay, leaveEndDay, teamMember.getPerson(), t);
        t.actingHeads.add(aH);

        return aH;
    }

    public static void removeActingHead(Team t, ActingHead aH) {
        t.actingHeads.remove(aH);
    }

    public static void addActingHead(Team t, ActingHead aH) {
        t.actingHeads.add(aH);
    }

}
