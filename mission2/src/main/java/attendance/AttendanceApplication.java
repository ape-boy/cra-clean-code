package attendance;

public class AttendanceApplication {

    public static void main(String[] args) {
        ScoringStrategy scoringStrategy = ScoringStrategyFactory.createStrategy("baseball");
        GradingPolicy gradingPolicy = GradingPolicyFactory.createPolicy("baseball");

        AttendanceService service = new AttendanceService(scoringStrategy, gradingPolicy);

        service.loadAttendanceData("../attendance_weekday_500.txt");
        service.printResults();
        service.printRemovalCandidates();
    }
}