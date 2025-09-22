import java.io.*;
import java.util.*;

public class Attendance {

    // 시스템 제약사항
    private static final int MAX_PLAYERS = 100;
    private static final int MAX_ATTENDANCE_RECORDS = 500;
    private static final int DAYS_IN_WEEK = 7;
    private static final int PLAYER_ID_START = 1;

    // 점수 시스템
    private static final int WEDNESDAY_POINTS = 3;
    private static final int WEEKEND_POINTS = 2;
    private static final int WEEKDAY_POINTS = 1;

    // 보너스 시스템
    private static final int BONUS_POINTS = 10;
    private static final int BONUS_THRESHOLD = 10;

    // 등급 기준
    private static final int GOLD_THRESHOLD = 50;
    private static final int SILVER_THRESHOLD = 30;

    // 등급 코드
    private static final int GRADE_NORMAL = 0;
    private static final int GRADE_GOLD = 1;
    private static final int GRADE_SILVER = 2;

    static Map<String, Integer> playerIdMap = new HashMap<>();
    static int playerCount = 0;

    static int[][] attendanceByDay = new int[MAX_PLAYERS][DAYS_IN_WEEK];
    static int[] totalPoints = new int[MAX_PLAYERS];
    static int[] playerGrade = new int[MAX_PLAYERS];
    static String[] playerNames = new String[MAX_PLAYERS];

    static int[] wednesdayCount = new int[MAX_PLAYERS];
    static int[] weekendCount = new int[MAX_PLAYERS];

    public static void main(String[] args) {
        processAttendanceData();
    }

    private static void processAttendanceData() {
        readAttendanceFile();
        calculateBonusPoints();
        determineGrades();
        printResults();
        printRemovalCandidates();
    }

    private static void readAttendanceFile() {
        try (BufferedReader br = new BufferedReader(new FileReader("../attendance_weekday_500.txt"))) {
            for (int i = 0; i < MAX_ATTENDANCE_RECORDS; i++) {
                String line = br.readLine();
                if (line == null) break;
                processLine(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void processLine(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length == 2) {
            recordAttendance(parts[0], parts[1]);
        }
    }

    private static void recordAttendance(String name, String day) {
        int playerId = getPlayerId(name);
        int dayIndex = convertDayToIndex(day);
        int points = calculatePoints(day);

        if (dayIndex != -1) {
            attendanceByDay[playerId][dayIndex]++;
            totalPoints[playerId] += points;
            updateSpecialAttendance(playerId, day);
        }
    }

    private static int getPlayerId(String name) {
        if (!playerIdMap.containsKey(name)) {
            playerIdMap.put(name, ++playerCount);
            playerNames[playerCount] = name;
        }
        return playerIdMap.get(name);
    }

    private static int convertDayToIndex(String day) {
        switch (day) {
            case "monday":    return 0;
            case "tuesday":   return 1;
            case "wednesday": return 2;
            case "thursday":  return 3;
            case "friday":    return 4;
            case "saturday":  return 5;
            case "sunday":    return 6;
            default:          return -1;
        }
    }

    private static int calculatePoints(String day) {
        if (day.equals("wednesday")) {
            return WEDNESDAY_POINTS;
        } else if (day.equals("saturday") || day.equals("sunday")) {
            return WEEKEND_POINTS;
        } else {
            return WEEKDAY_POINTS;
        }
    }

    private static void updateSpecialAttendance(int playerId, String day) {
        if (day.equals("wednesday")) {
            wednesdayCount[playerId]++;
        } else if (day.equals("saturday") || day.equals("sunday")) {
            weekendCount[playerId]++;
        }
    }

    private static void calculateBonusPoints() {
        for (int i = PLAYER_ID_START; i <= playerCount; i++) {
            if (isEligibleForWednesdayBonus(i)) {
                totalPoints[i] += BONUS_POINTS;
            }
            if (isEligibleForWeekendBonus(i)) {
                totalPoints[i] += BONUS_POINTS;
            }
        }
    }

    private static boolean isEligibleForWednesdayBonus(int playerId) {
        return wednesdayCount[playerId] >= BONUS_THRESHOLD;
    }

    private static boolean isEligibleForWeekendBonus(int playerId) {
        return weekendCount[playerId] >= BONUS_THRESHOLD;
    }

    private static void determineGrades() {
        for (int i = PLAYER_ID_START; i <= playerCount; i++) {
            playerGrade[i] = calculateGrade(totalPoints[i]);
        }
    }

    private static int calculateGrade(int points) {
        if (points >= GOLD_THRESHOLD) {
            return GRADE_GOLD;
        } else if (points >= SILVER_THRESHOLD) {
            return GRADE_SILVER;
        } else {
            return GRADE_NORMAL;
        }
    }

    private static void printResults() {
        for (int i = PLAYER_ID_START; i <= playerCount; i++) {
            printPlayerInfo(i);
        }
    }

    private static void printPlayerInfo(int playerId) {
        System.out.print("NAME : " + playerNames[playerId] + ", ");
        System.out.print("POINT : " + totalPoints[playerId] + ", ");
        System.out.print("GRADE : ");
        printGradeName(playerGrade[playerId]);
    }

    private static void printGradeName(int grade) {
        if (grade == GRADE_GOLD) {
            System.out.println("GOLD");
        } else if (grade == GRADE_SILVER) {
            System.out.println("SILVER");
        } else {
            System.out.println("NORMAL");
        }
    }

    private static void printRemovalCandidates() {
        System.out.println();
        System.out.println("Removed player");
        System.out.println("==============");

        for (int i = PLAYER_ID_START; i <= playerCount; i++) {
            if (shouldRemovePlayer(i)) {
                System.out.println(playerNames[i]);
            }
        }
    }

    private static boolean shouldRemovePlayer(int playerId) {
        return playerGrade[playerId] == GRADE_NORMAL &&
               wednesdayCount[playerId] == 0 &&
               weekendCount[playerId] == 0;
    }
}