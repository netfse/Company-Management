public class LeaveRecord_NL extends LeaveRecord {

    public LeaveRecord_NL(Employee e, Day dstart, Day dend) {
        super(e, dstart, dend);
    }

    @Override
    public String toString() {
        return dayStart.toString() + " to " + dayEnd.toString() + " [NL]";
    }

}
