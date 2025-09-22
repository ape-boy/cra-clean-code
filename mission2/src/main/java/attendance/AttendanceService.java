package attendance;

import java.io.*;
import java.util.*;

public class AttendanceService {
    private static final int MAX_RECORDS = 500;

    private final Map<String, Player> playerMap;
    private final List<Player> playerList;
    private final ScoringStrategy scoringStrategy;
    private final GradingPolicy gradingPolicy;
    private int playerIdCounter;

    public AttendanceService(ScoringStrategy scoringStrategy, GradingPolicy gradingPolicy) {
        this.playerMap = new HashMap<>();
        this.playerList = new ArrayList<>();
        this.scoringStrategy = scoringStrategy;
        this.gradingPolicy = gradingPolicy;
        this.playerIdCounter = 0;
    }

    public void loadAttendanceData(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int count = 0;

            while ((line = reader.readLine()) != null && count < MAX_RECORDS) {
                AttendanceRecord record = parseRecord(line);
                if (record != null) {
                    processAttendance(record);
                    count++;
                }
            }

            applyBonusPoints();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private AttendanceRecord parseRecord(String line) {
        if (line == null || line.trim().isEmpty()) {
            return null;
        }

        String[] parts = line.trim().split("\\s+");
        if (parts.length != 2) {
            return null;
        }

        return new AttendanceRecord(parts[0], parts[1]);
    }

    private void processAttendance(AttendanceRecord record) {
        Player player = getOrCreatePlayer(record.getPlayerName());
        String day = record.getDay();

        int points = scoringStrategy.calculateBasePoints(day);
        player.addPoints(points);
        player.recordAttendance(day);
    }

    private Player getOrCreatePlayer(String name) {
        if (!playerMap.containsKey(name)) {
            playerIdCounter++;
            Player newPlayer = new Player(playerIdCounter, name);
            playerMap.put(name, newPlayer);
            playerList.add(newPlayer);
        }
        return playerMap.get(name);
    }

    private void applyBonusPoints() {
        for (Player player : playerList) {
            int bonus = scoringStrategy.calculateBonusPoints(player);
            player.addPoints(bonus);
        }
    }

    public void printResults() {
        for (Player player : playerList) {
            String grade = gradingPolicy.determineGrade(player.getTotalPoints());
            System.out.printf("NAME : %s, POINT : %d, GRADE : %s%n",
                    player.getName(),
                    player.getTotalPoints(),
                    grade);
        }
    }

    public void printRemovalCandidates() {
        System.out.println();
        System.out.println("Removed player");
        System.out.println("==============");

        for (Player player : playerList) {
            String grade = gradingPolicy.determineGrade(player.getTotalPoints());
            if (gradingPolicy.isRemovalCandidate(player, grade)) {
                System.out.println(player.getName());
            }
        }
    }

    public List<Player> getPlayers() {
        return new ArrayList<>(playerList);
    }
}