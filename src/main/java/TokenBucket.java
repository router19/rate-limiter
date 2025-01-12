public class TokenBucket {

    private long tokens;
    private long lastRefillTimestamp;
    private final long maxToken;
    private final long refillInterval;
    private final long refillToken;


    public TokenBucket(long maxToken, long refillInterval, long refillToken) {
        this.maxToken = maxToken;
        this.refillInterval = refillInterval;
        this.refillToken = refillToken;
        this.tokens = maxToken;
        this.lastRefillTimestamp = System.nanoTime();
    }

    public synchronized boolean allowRequest(){
        refill();
        if(tokens > 0){
            tokens--;
            return true;
        }
        return false;
    }
    private void refill(){
        long now = System.nanoTime();
        long elapsedTime = now - lastRefillTimestamp;
        long tokensToAdd = (elapsedTime/refillInterval) * refillToken;
        if(tokensToAdd > 0)
        {
            tokens = Math.min(tokens + tokensToAdd, maxToken);
            lastRefillTimestamp = now;
        }


    }
}
