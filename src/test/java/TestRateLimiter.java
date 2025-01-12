import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.concurrent.TimeUnit;

public class TestRateLimiter {

    @Test
    public void testAllowRequestWithinLimit() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, TimeUnit.SECONDS.toNanos(1), 1);
        String clientId = "client1";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allowRequest(clientId));
        }
    }

    @Test
    public void testDenyRequestExceedingLimit() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, TimeUnit.SECONDS.toNanos(1), 1);
        String clientId = "client1";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allowRequest(clientId));
        }
        assertFalse(rateLimiter.allowRequest(clientId));
    }

    @Test
    public void testRefillTokens() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, TimeUnit.SECONDS.toNanos(1), 1);
        String clientId = "client1";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allowRequest(clientId));
        }
        assertFalse(rateLimiter.allowRequest(clientId));

        TimeUnit.SECONDS.sleep(2);

        assertTrue(rateLimiter.allowRequest(clientId));
        assertTrue(rateLimiter.allowRequest(clientId));
        assertFalse(rateLimiter.allowRequest(clientId));
    }

    @Test
    public void testMultipleClients() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, TimeUnit.SECONDS.toNanos(1), 1);
        String client1 = "client1";
        String client2 = "client2";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allowRequest(client1));
            assertTrue(rateLimiter.allowRequest(client2));
        }

        assertFalse(rateLimiter.allowRequest(client1));
        assertFalse(rateLimiter.allowRequest(client2));
    }

    @Test
    public void testAllowRequestWithSleep() throws InterruptedException {
        RateLimiter rateLimiter = new RateLimiter(5, TimeUnit.SECONDS.toNanos(1), 1);
        String clientId = "client1";

        for (int i = 0; i < 5; i++) {
            assertTrue(rateLimiter.allowRequest(clientId));
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }
}
