package attendance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class ScoringStrategyFactoryTest {

    @Test
    @DisplayName("팩토리 생성 테스트")
    void testCreateStrategy() {
        ScoringStrategy strategy1 = ScoringStrategyFactory.createStrategy("baseball");
        ScoringStrategy strategy2 = ScoringStrategyFactory.createStrategy("unknown");

        assertNotNull(strategy1);
        assertNotNull(strategy2);
        assertTrue(strategy1 instanceof BaseballScoringStrategy);
        assertTrue(strategy2 instanceof BaseballScoringStrategy);
    }
}