import java.util.ArrayList;
import java.util.Collections;

public abstract class LeaveRecord implements Comparable<LeaveRecord> {

    private Employee employee;
    protected Day dayStart;
    protected Day dayEnd;

    public LeaveRecord(Employee e, Day dstart, Day dend) {
        employee = e;
        dayStart = dstart;
        dayEnd = dend;
    }

    public Employee getEmployee() {
        return employee;
    }

    public Day getDayStart() {
        return dayStart;
    }

    public Day getDayEnd() {
        return dayEnd;
    }

    public String toString() {
        return dayStart.toString() + " to " + dayEnd.toString();
    }

    @Override
    public int compareTo(LeaveRecord another) {

        return (Day.daysComparison(dayStart, another.dayStart) == 1) ? -1 : 1;

    }

    public static void listLeaves(Employee e) {

        ArrayList<LeaveRecord> leaveRecords = e.getleaveRecords();

        Collections.sort(leaveRecords);

        if (leaveRecords.size() > 0) {
            for (LeaveRecord l : leaveRecords) {
                System.out.println(l.toString());
            }
        } else {
            System.out.println("No leave record");
        }

    }

}
