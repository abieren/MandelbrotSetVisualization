package visualization;

public interface IMandelbrotCalculator
{
    public int[][] calculate(int NUM_THREADS, double MAX_DISTANCE, int MAX_ITERATIONS, RenderWindow renderWindow);
}
