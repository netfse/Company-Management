public class ActingHead implements Comparable<ActingHead> {

    private Employee person;
    private Team actingHeadOfTeam;
    private Day dayStart;
    private Day dayEndDay;

    public ActingHead(Day dstart, Day dEnd, Employee e, Team t) {
        person = e;
        dayStart = dstart;
        dayEndDay = dEnd;
        actingHeadOfTeam = t;
    }

    public Employee getPerson() {
        return person;
    }

    public Day getDayStart() {
        return dayStart;
    }

    public Day getDayEndDay() {
        return dayEndDay;
    }

    public Team getactingHeadOfTeam() {
        return actingHeadOfTeam;
    }

    @Override
    public String toString() {
        return dayStart.toString() + " to " + dayEndDay.toString() + ": " + person.getName();
    }

    @Override
    public int compareTo(ActingHead another) {
        return Day.daysComparison(another.dayStart, dayStart);
    }
}
