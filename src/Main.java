import mandelbrot.MandelbrotCalculator;
import visualization.IMandelbrotCalculator;
import visualization.IterationGridPrinter;
import visualization.RenderWindow;
import visualization.colormapper.BlackWhiteMapper;
import visualization.colormapper.RainbowMapper;
import visualization.Visualization;
import visualization.colormapper.IColorMapper;


public class Main
{
    static final int            NUM_THREADS     = 2;
    static final double         MAX_DISTANCE    = 2.0;
    static final int            MAX_ITERATIONS  = 1000;
    static final RenderWindow   RENDER_WINDOW   = new RenderWindow(1500, 1000, -2, 1, -1, 1);
    static final boolean        FULLSCREEN      = false;

    static final IMandelbrotCalculator  MANDELBROT_CALCULATOR   = new MandelbrotCalculator();
    static final IColorMapper           COLOR_MAPPER            = new RainbowMapper(10);
    //static final IColorMapper           COLOR_MAPPER            = new BlackWhiteMapper(5, false);

    public static void main(String[] args)
    {
        // measure speedup
        long durationSerial = StopWatch.measure(
                () -> MANDELBROT_CALCULATOR.calculate(1, MAX_DISTANCE, MAX_ITERATIONS, RENDER_WINDOW));
        long durationParallel = StopWatch.measure(
                () -> MANDELBROT_CALCULATOR.calculate(NUM_THREADS, MAX_DISTANCE, MAX_ITERATIONS, RENDER_WINDOW));

        System.out.printf("Serial execution duration: %d ms\n", durationSerial);
        System.out.printf("Parallel execution duration: %d ms\n", durationParallel);
        System.out.printf("Speedup: %f\n", (double)durationSerial / (double)durationParallel);
        System.out.printf("Efficency: %f\n", ((double)durationSerial / (double)durationParallel) / NUM_THREADS * 100);

        // display mandelbrot set in console
        //int[][] iterationGrid = MANDELBROT_CALCULATOR.calculate(NUM_THREADS, MAX_DISTANCE, MAX_ITERATIONS, RENDER_WINDOW);
        //IterationGridPrinter.print(iterationGrid, MAX_ITERATIONS, true);

        // start interactive visualization
        Visualization visualization = new Visualization();
        visualization.setParameters(
                NUM_THREADS,
                MAX_ITERATIONS,
                MAX_DISTANCE,
                RENDER_WINDOW,
                MANDELBROT_CALCULATOR,
                COLOR_MAPPER);
        visualization.run(FULLSCREEN);
    }
}
