package attendance;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final int id;
    private final String name;
    private int totalPoints;
    private int wednesdayCount;
    private int weekendCount;
    private final Map<String, Integer> attendanceByDay;

    public Player(int id, String name) {
        this.id = id;
        this.name = name;
        this.totalPoints = 0;
        this.wednesdayCount = 0;
        this.weekendCount = 0;
        this.attendanceByDay = new HashMap<>();
    }

    public void addPoints(int points) {
        this.totalPoints += points;
    }

    public void recordAttendance(String day) {
        attendanceByDay.merge(day, 1, Integer::sum);

        if ("wednesday".equalsIgnoreCase(day)) {
            wednesdayCount++;
        } else if ("saturday".equalsIgnoreCase(day) || "sunday".equalsIgnoreCase(day)) {
            weekendCount++;
        }
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public int getWednesdayCount() {
        return wednesdayCount;
    }

    public int getWeekendCount() {
        return weekendCount;
    }
}