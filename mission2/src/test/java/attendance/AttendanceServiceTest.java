package attendance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import java.io.*;
import static org.junit.jupiter.api.Assertions.*;

class AttendanceServiceTest {

    @Test
    @DisplayName("서비스 통합 테스트")
    void testServiceIntegration() throws IOException {
        ScoringStrategy scoringStrategy = new BaseballScoringStrategy();
        GradingPolicy gradingPolicy = new BaseballGradingPolicy();
        AttendanceService service = new AttendanceService(scoringStrategy, gradingPolicy);

        File testFile = File.createTempFile("test", ".txt");
        testFile.deleteOnExit();

        try (FileWriter writer = new FileWriter(testFile)) {
            for (int i = 0; i < 12; i++) {
                writer.write("Alice wednesday\n");
            }
            for (int i = 0; i < 3; i++) {
                writer.write("Bob saturday\n");
                writer.write("Bob sunday\n");
            }
            for (int i = 0; i < 5; i++) {
                writer.write("Bob monday\n");
            }
            writer.write("Ethan tuesday\n");
        }

        service.loadAttendanceData(testFile.getAbsolutePath());

        assertEquals(3, service.getPlayers().size());

        Player alice = service.getPlayers().get(0);
        assertEquals("Alice", alice.getName());
        assertEquals(46, alice.getTotalPoints());

        Player bob = service.getPlayers().get(1);
        assertEquals("Bob", bob.getName());
        assertEquals(17, bob.getTotalPoints());
    }

    @Test
    @DisplayName("빈 파일 처리")
    void testEmptyFile() throws IOException {
        ScoringStrategy scoringStrategy = new BaseballScoringStrategy();
        GradingPolicy gradingPolicy = new BaseballGradingPolicy();
        AttendanceService service = new AttendanceService(scoringStrategy, gradingPolicy);

        File testFile = File.createTempFile("empty", ".txt");
        testFile.deleteOnExit();

        service.loadAttendanceData(testFile.getAbsolutePath());

        assertEquals(0, service.getPlayers().size());
    }
}