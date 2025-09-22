package attendance;

public class BaseballGradingPolicy implements GradingPolicy {

    @Override
    public String determineGrade(int points) {
        if (points >= 50) {
            return "GOLD";
        } else if (points >= 30) {
            return "SILVER";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public boolean isRemovalCandidate(Player player, String grade) {
        return "NORMAL".equals(grade) &&
               player.getWednesdayCount() == 0 &&
               player.getWeekendCount() == 0;
    }
}