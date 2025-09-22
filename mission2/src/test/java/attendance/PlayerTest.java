package attendance;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {
    private Player player;

    @BeforeEach
    void setUp() {
        player = new Player(1, "TestPlayer");
    }

    @Test
    @DisplayName("플레이어 생성 및 초기값")
    void testPlayerCreation() {
        Player newPlayer = new Player(2, "NewPlayer");

        assertEquals(2, newPlayer.getId());
        assertEquals("NewPlayer", newPlayer.getName());
        assertEquals(0, newPlayer.getTotalPoints());
        assertEquals(0, newPlayer.getWednesdayCount());
        assertEquals(0, newPlayer.getWeekendCount());
    }

    @Test
    @DisplayName("포인트 추가")
    void testAddPoints() {
        player.addPoints(10);
        player.addPoints(5);

        assertEquals(15, player.getTotalPoints());
    }

    @Test
    @DisplayName("수요일 출석 카운트")
    void testWednesdayAttendance() {
        player.recordAttendance("wednesday");
        player.recordAttendance("WEDNESDAY");
        player.recordAttendance("Wednesday");

        assertEquals(3, player.getWednesdayCount());
    }

    @Test
    @DisplayName("주말 출석 카운트")
    void testWeekendAttendance() {
        player.recordAttendance("saturday");
        player.recordAttendance("SATURDAY");
        player.recordAttendance("sunday");
        player.recordAttendance("SUNDAY");

        assertEquals(4, player.getWeekendCount());
    }

    @Test
    @DisplayName("평일 출석은 특별 카운트 안함")
    void testWeekdayAttendance() {
        player.recordAttendance("monday");
        player.recordAttendance("tuesday");
        player.recordAttendance("thursday");
        player.recordAttendance("friday");

        assertEquals(0, player.getWednesdayCount());
        assertEquals(0, player.getWeekendCount());
    }
}