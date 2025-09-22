package attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BaseballGradingPolicyTest {
    private BaseballGradingPolicy policy;

    @BeforeEach
    void setUp() {
        policy = new BaseballGradingPolicy();
    }

    @Test
    @DisplayName("GOLD 등급 - 50점 이상")
    void testGoldGrade() {
        assertEquals("GOLD", policy.determineGrade(50));
        assertEquals("GOLD", policy.determineGrade(100));
        assertEquals("GOLD", policy.determineGrade(51));
    }

    @Test
    @DisplayName("SILVER 등급 - 30~49점")
    void testSilverGrade() {
        assertEquals("SILVER", policy.determineGrade(30));
        assertEquals("SILVER", policy.determineGrade(49));
        assertEquals("SILVER", policy.determineGrade(35));
    }

    @Test
    @DisplayName("NORMAL 등급 - 30점 미만")
    void testNormalGrade() {
        assertEquals("NORMAL", policy.determineGrade(0));
        assertEquals("NORMAL", policy.determineGrade(29));
        assertEquals("NORMAL", policy.determineGrade(15));
    }

    @Test
    @DisplayName("탈락 후보 - NORMAL이고 수요일/주말 출석 없음")
    void testIsRemovalCandidate() {
        Player player = new Player(1, "Player");
        player.recordAttendance("monday");
        player.recordAttendance("tuesday");

        assertTrue(policy.isRemovalCandidate(player, "NORMAL"));
    }

    @Test
    @DisplayName("탈락 후보 아님 - GOLD/SILVER")
    void testNotRemovalCandidateHighGrade() {
        Player player = new Player(1, "Player");

        assertFalse(policy.isRemovalCandidate(player, "GOLD"));
        assertFalse(policy.isRemovalCandidate(player, "SILVER"));
    }

    @Test
    @DisplayName("탈락 후보 아님 - NORMAL이지만 수요일 출석")
    void testNotRemovalCandidateWithWednesday() {
        Player player = new Player(1, "Player");
        player.recordAttendance("wednesday");

        assertFalse(policy.isRemovalCandidate(player, "NORMAL"));
    }

    @Test
    @DisplayName("탈락 후보 아님 - NORMAL이지만 주말 출석")
    void testNotRemovalCandidateWithWeekend() {
        Player player1 = new Player(1, "Player1");
        player1.recordAttendance("saturday");

        Player player2 = new Player(2, "Player2");
        player2.recordAttendance("sunday");

        assertFalse(policy.isRemovalCandidate(player1, "NORMAL"));
        assertFalse(policy.isRemovalCandidate(player2, "NORMAL"));
    }
}