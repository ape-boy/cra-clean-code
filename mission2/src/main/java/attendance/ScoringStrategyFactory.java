package attendance;

public class ScoringStrategyFactory {

    public static ScoringStrategy createStrategy(String type) {
        if ("baseball".equalsIgnoreCase(type)) {
            return new BaseballScoringStrategy();
        }
        return new BaseballScoringStrategy();
    }
}