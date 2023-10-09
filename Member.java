public class Member implements Comparable<Member> {

    private Team joinedTeam;
    private Employee person;
    private Role role;

    public Member(Team jT, Employee e, Role aState) {
        joinedTeam = jT;
        person = e;
        role = aState;
    }

    public void setRole(Role r) {
        role = r;
    }

    public Employee getPerson() {
        return person;
    }

    public Role getRole() {
        return role;
    }

    public String getRoleName() {
        return role.getRoleName();
    }

    public Team getJoinedTeam() {
        return joinedTeam;
    }

    @Override
    public int compareTo(Member another) {
        return this.person.getName().compareTo(another.person.getName());
    }

    @Override
    public String toString() {
        return getPerson().getName() + getRoleName();
    }
}
