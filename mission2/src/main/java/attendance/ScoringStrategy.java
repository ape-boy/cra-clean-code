package attendance;

public interface ScoringStrategy {
    int calculateBasePoints(String day);
    int calculateBonusPoints(Player player);
}