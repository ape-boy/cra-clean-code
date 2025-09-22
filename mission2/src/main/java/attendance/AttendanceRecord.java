package attendance;

public class AttendanceRecord {
    private final String playerName;
    private final String day;

    public AttendanceRecord(String playerName, String day) {
        this.playerName = playerName;
        this.day = day;
    }

    public String getPlayerName() {
        return playerName;
    }

    public String getDay() {
        return day;
    }
}