import java.util.concurrent.TimeUnit;

public class RateLimiterMainApplication {
    private static  final int MAX_TOKEN = 5;
    public static void main(String[] args) throws InterruptedException{
        //refill rate 3 token per sec

        //means rate limiting 3 request per second
        RateLimiter rateLimiter = new RateLimiter(MAX_TOKEN, TimeUnit.SECONDS.toNanos(1),3);
        String clientId = "client1";
        for(int requestNumber =0; requestNumber < 13; requestNumber++){

            boolean isAllowed = rateLimiter.allowRequest(clientId);
            System.out.println("Request -> " + requestNumber + " |  allowed -> " + isAllowed);
//            assertTrue(isAllowed,"Request " + requestNumber + " should be allowed");
            TimeUnit.MILLISECONDS.sleep(200);
        }
    }

    private static void assertTrue(boolean condition,String message){
        if(!condition)
            throw new AssertionError(message);
    }
}
