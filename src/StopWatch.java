public class StopWatch
{
    public static long measure(Runnable runnable)
    {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long stopTime = System.currentTimeMillis();
        return stopTime - startTime;
    }
}
