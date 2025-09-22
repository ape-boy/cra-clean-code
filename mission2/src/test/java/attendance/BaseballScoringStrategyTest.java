package attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class BaseballScoringStrategyTest {
    private BaseballScoringStrategy strategy;

    @BeforeEach
    void setUp() {
        strategy = new BaseballScoringStrategy();
    }

    @Test
    @DisplayName("수요일 3점")
    void testWednesdayPoints() {
        assertEquals(3, strategy.calculateBasePoints("wednesday"));
        assertEquals(3, strategy.calculateBasePoints("WEDNESDAY"));
    }

    @Test
    @DisplayName("주말 2점")
    void testWeekendPoints() {
        assertEquals(2, strategy.calculateBasePoints("saturday"));
        assertEquals(2, strategy.calculateBasePoints("SATURDAY"));
        assertEquals(2, strategy.calculateBasePoints("sunday"));
        assertEquals(2, strategy.calculateBasePoints("SUNDAY"));
    }

    @Test
    @DisplayName("평일 1점")
    void testWeekdayPoints() {
        assertEquals(1, strategy.calculateBasePoints("monday"));
        assertEquals(1, strategy.calculateBasePoints("tuesday"));
        assertEquals(1, strategy.calculateBasePoints("thursday"));
        assertEquals(1, strategy.calculateBasePoints("friday"));
    }

    @Test
    @DisplayName("보너스 없음 - 10회 미만")
    void testNoBonusPoints() {
        Player player = new Player(1, "Test");
        for (int i = 0; i < 9; i++) {
            player.recordAttendance("wednesday");
        }
        for (int i = 0; i < 9; i++) {
            player.recordAttendance("saturday");
        }

        assertEquals(0, strategy.calculateBonusPoints(player));
    }

    @Test
    @DisplayName("수요일 보너스 10점")
    void testWednesdayBonus() {
        Player player = new Player(1, "Test");
        for (int i = 0; i < 10; i++) {
            player.recordAttendance("wednesday");
        }

        assertEquals(10, strategy.calculateBonusPoints(player));
    }

    @Test
    @DisplayName("주말 보너스 10점")
    void testWeekendBonus() {
        Player player = new Player(1, "Test");
        for (int i = 0; i < 5; i++) {
            player.recordAttendance("saturday");
            player.recordAttendance("sunday");
        }

        assertEquals(10, strategy.calculateBonusPoints(player));
    }

    @Test
    @DisplayName("수요일 + 주말 보너스 20점")
    void testBothBonuses() {
        Player player = new Player(1, "Test");
        for (int i = 0; i < 10; i++) {
            player.recordAttendance("wednesday");
        }
        for (int i = 0; i < 10; i++) {
            player.recordAttendance("saturday");
        }

        assertEquals(20, strategy.calculateBonusPoints(player));
    }
}