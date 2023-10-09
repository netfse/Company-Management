public class Day implements Cloneable {

    private int year;
    private int month;
    private int day;

    private static final String MonthNames = "JanFebMarAprMayJunJulAugSepOctNovDec";

    public Day(String sDay) {
        set(sDay);
    }

    public void set(String sDay) {
        String[] sDayParts = sDay.split("-");
        this.day = Integer.parseInt(sDayParts[0]);
        this.year = Integer.parseInt(sDayParts[2]);
        this.month = MonthNames.indexOf(sDayParts[1]) / 3 + 1;
    }

    static public boolean isLeapYear(int y) {
        if (y % 400 == 0)
            return true;
        else if (y % 100 == 0)
            return false;
        else if (y % 4 == 0)
            return true;
        else
            return false;
    }

    static public boolean valid(int y, int m, int d) {
        if (m < 1 || m > 12 || d < 1)
            return false;
        switch (m) {
            case 1:
            case 3:
            case 5:
            case 7:
            case 8:
            case 10:
            case 12:
                return d <= 31;
            case 4:
            case 6:
            case 9:
            case 11:
                return d <= 30;
            case 2:
                if (isLeapYear(y))
                    return d <= 29;
                else
                    return d <= 28;
        }
        return false;
    }

    public boolean isEndOfAMonth() {

        return valid(year, month, day + 1) ? false : true;

    }

    public Day next() {

        if (isEndOfAMonth())

            if (month == 12) {
                year++;
                month = 1;
                day = 1;
            } else {
                month++;
                day = 1;
            }

        else
            day++;

        return new Day(this.toString());
    }

    public static int daysComparison(Day d1, Day d2) {

        String day1 = String.format("%4d", d1.year).replace(" ", "0") +
                String.format("%2d", d1.month).replace(" ", "0") +
                String.format("%2d", d1.day).replace(" ", "0");

        String day2 = String.format("%4d", d2.year).replace(" ", "0") +
                String.format("%2d", d2.month).replace(" ", "0") +
                String.format("%2d", d2.day).replace(" ", "0");

        if (Integer.parseInt(day1) == Integer.parseInt(day2)) {
            return 0;
        } else if (Integer.parseInt(day1) < Integer.parseInt(day2)) {
            return 1;
        } else {
            return -1;
        }
    }

    public static int daysRange(Day d1, Day d2) {
        int dayCount = 1;
        Day copyOfDay1 = d1.clone();
        while (!copyOfDay1.toString().equals(d2.toString()) && dayCount < 500) {
            copyOfDay1 = copyOfDay1.next();
            dayCount++;
        }
        return dayCount;
    }

    public static Boolean IsLeaveOverLapped(Day d1Start, Day d1End, Day d2Start, Day d2End) {
        // (StartA <= EndB) and (EndA >= StartB)
        return (daysComparison(d1Start, d2End) == 1
                && daysComparison(d1End, d2Start) == -1)
                || (daysComparison(d1Start, d2End) == 0
                        && daysComparison(d1End, d2Start) == 0);
    }

    @Override
    public String toString() {
        return day + "-" + MonthNames.substring((month - 1) * 3, (month) * 3) + "-" + year;
    }

    @Override
    public Day clone() {
        Day copy = null;

        try {
            copy = (Day) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return copy;
    }
}
