public class LeaveRecord_AL extends LeaveRecord {

    public LeaveRecord_AL(Employee e, Day dstart, Day dend) {
        super(e, dstart, dend);
    }

    @Override
    public String toString() {
        return dayStart.toString() + " to " + dayEnd.toString() + " [AL]";
    }

}
