public class LeaveRecord_SL extends LeaveRecord {
    
    public LeaveRecord_SL(Employee e, Day dstart, Day dend) {
        super(e, dstart, dend);
    }

    @Override
    public String toString() {
        return dayStart.toString() + " to " + dayEnd.toString() + " [SL]";
    }

}
