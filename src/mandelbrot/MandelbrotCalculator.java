package mandelbrot;

import visualization.IMandelbrotCalculator;
import visualization.RenderWindow;

public class MandelbrotCalculator implements IMandelbrotCalculator
{
    public int[][] calculate(int NUM_THREADS, double MAX_DISTANCE, int MAX_ITERATIONS, RenderWindow renderWindow)
    {
        int[][] iterationGrid = new int[renderWindow.ynum][renderWindow.xnum];

        MandelbrotPayload[] windows = MandelbrotPayload.distributeRows(
                NUM_THREADS,
                renderWindow.ymin, renderWindow.ymax, renderWindow.ynum,
                renderWindow.xmin, renderWindow.xmax, renderWindow.xnum);

        // init threads
        MandelbrotThread[] threads = new MandelbrotThread[NUM_THREADS];
        for (int thread = 0; thread < NUM_THREADS; thread++)
        {
            threads[thread] = new MandelbrotThread(iterationGrid, windows[thread], MAX_DISTANCE, MAX_ITERATIONS);
        }
        // start threads
        for (int thread = 0; thread < NUM_THREADS; thread++)
        {
            threads[thread].start();
        }
        // join threads
        for (int thread = 0; thread < NUM_THREADS; thread++)
        {
            try
            {
                threads[thread].join();
            } catch (InterruptedException e)
            {
                throw new RuntimeException(e);
            }
        }

        return iterationGrid;
    }
}
