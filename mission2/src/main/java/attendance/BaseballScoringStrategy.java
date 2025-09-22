package attendance;

public class BaseballScoringStrategy implements ScoringStrategy {

    @Override
    public int calculateBasePoints(String day) {
        switch (day.toLowerCase()) {
            case "wednesday":
                return 3;
            case "saturday":
            case "sunday":
                return 2;
            default:
                return 1;
        }
    }

    @Override
    public int calculateBonusPoints(Player player) {
        int bonus = 0;

        if (player.getWednesdayCount() >= 10) {
            bonus += 10;
        }

        if (player.getWeekendCount() >= 10) {
            bonus += 10;
        }

        return bonus;
    }
}