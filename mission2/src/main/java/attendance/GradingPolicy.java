package attendance;

public interface GradingPolicy {
    String determineGrade(int points);
    boolean isRemovalCandidate(Player player, String grade);
}