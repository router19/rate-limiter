import java.util.concurrent.ConcurrentHashMap;

public class RateLimiter {

    private final long maxTokens;
    private final long refillInterval;
    private final long refillTokens;
    private final ConcurrentHashMap<String, TokenBucket> buckets = new ConcurrentHashMap<String, TokenBucket>();
    public RateLimiter(long maxTokens, long refillInterval, long refillTokens) {
        this.maxTokens = maxTokens;
        this.refillInterval = refillInterval;
        this.refillTokens = refillTokens;
    }

    public boolean allowRequest(String clientId){
        TokenBucket bucket = buckets.computeIfAbsent(clientId, t -> new TokenBucket(maxTokens, refillInterval, refillTokens));
        return bucket.allowRequest();

    }


}
