package attendance;

public class GradingPolicyFactory {

    public static GradingPolicy createPolicy(String type) {
        if ("baseball".equalsIgnoreCase(type)) {
            return new BaseballGradingPolicy();
        }
        return new BaseballGradingPolicy();
    }
}