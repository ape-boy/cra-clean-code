package attendance;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

class GradingPolicyFactoryTest {

    @Test
    @DisplayName("팩토리 생성 테스트")
    void testCreatePolicy() {
        GradingPolicy policy1 = GradingPolicyFactory.createPolicy("baseball");
        GradingPolicy policy2 = GradingPolicyFactory.createPolicy("unknown");

        assertNotNull(policy1);
        assertNotNull(policy2);
        assertTrue(policy1 instanceof BaseballGradingPolicy);
        assertTrue(policy2 instanceof BaseballGradingPolicy);
    }
}