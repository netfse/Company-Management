public class LeaveRecord_BL extends LeaveRecord {

    public LeaveRecord_BL(Employee e, Day dstart, Day dend) {
        super(e, dstart, dend);
    }

    @Override
    public String toString() {
        return dayStart.toString() + " to " + dayEnd.toString() + " [BL]";
    }

}
