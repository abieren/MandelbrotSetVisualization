package visualization;

import visualization.colormapper.IColorMapper;
import processing.core.PApplet;
import processing.event.MouseEvent;

public class Visualization extends PApplet
{
    // parameters to be initialized by setParameters(...)
    private int NUM_THREADS;
    private int MAX_ITERATIONS;
    private double MAX_DISTANCE;
    private RenderWindow RENDER_WINDOW;
    private IMandelbrotCalculator MANDELBROT_CALCULATOR;
    private IColorMapper COLOR_MAPPER;
    private boolean runInFullscreen;

    // variables
    private int[] calculatedPixels;
    private MouseEvent mousePressedEvent = null;
    private MouseEvent mouseDraggedEvent = null;
    private int windowWidth;
    private int windowHeight;

    /**
     * Method to set the parameters after creation.
     */
    public void setParameters(
            int numThreads,
            int maxIterations,
            double maxDistance,
            RenderWindow renderWindow,
            IMandelbrotCalculator mandelbrotCalculator,
            IColorMapper colorMapper)
    {
        this.NUM_THREADS = numThreads;
        this.MAX_ITERATIONS = maxIterations;
        this.MAX_DISTANCE = maxDistance;
        this.RENDER_WINDOW = renderWindow;
        this.MANDELBROT_CALCULATOR = mandelbrotCalculator;
        this.COLOR_MAPPER = colorMapper;
    }

    /**
     * Method to run the sketch (the visualization) after creation.
     */
    public void run(boolean runInFullscreen)
    {
        this.runInFullscreen = runInFullscreen;
        this.runSketch();
    }

    @Override
    public void settings()
    {
        size(RENDER_WINDOW.xnum, RENDER_WINDOW.ynum);
        if (runInFullscreen)
        {
            fullScreen();
            updateRenderWindowAfterResize();
        }
        noLoop();
    }

    @Override
    public void setup()
    {
        calculatedPixels = calculateMandelbrotPixels();
    }

    @Override
    public void draw()
    {
        System.arraycopy(calculatedPixels, 0, pixels, 0, pixels.length);
        updatePixels();

        if (mousePressedEvent != null && mouseDraggedEvent != null)
        {
            drawZoomWindow();
        }
    }

    @Override
    public void mousePressed(MouseEvent event)
    {
        mousePressedEvent = event;
        redraw();
    }

    @Override
    public void mouseDragged(MouseEvent event)
    {
        mouseDraggedEvent = event;
        redraw();
    }

    @Override
    public void mouseReleased(MouseEvent event)
    {
        updateRenderWindowAfterZoom();
        mousePressedEvent = null;
        mouseDraggedEvent = null;

        setup();
        redraw();
    }

    private void drawZoomWindow()
    {
        // set style
        noFill();
        stroke(color(255, 255, 255));

        // get width and height
        int width = 2 * Math.abs(mouseDraggedEvent.getX() - mousePressedEvent.getX());
        int height = 2 * Math.abs(mouseDraggedEvent.getY() - mousePressedEvent.getY());

        // respect aspect ratio
        final double widthToHeight = (double) RENDER_WINDOW.xnum / (double) RENDER_WINDOW.ynum;
        // limit width
        width = (int) Math.max(width, widthToHeight * height);
        // limit height
        height = (int) Math.max(height, (1 / widthToHeight) * width);
        windowWidth = width;
        windowHeight = height;

        rectMode(CENTER);
        rect(mousePressedEvent.getX(), mousePressedEvent.getY(), width, height);
    }

    private void updateRenderWindowAfterZoom()
    {
        // update boundaries of render window without changing the aspect ratio of the window
        final int IGNORE = 0;
        double xmin = CoordConv.image2Graph(mousePressedEvent.getX() - windowWidth / 2, IGNORE, RENDER_WINDOW)[0];
        double xmax = CoordConv.image2Graph(mousePressedEvent.getX() + windowWidth / 2, IGNORE, RENDER_WINDOW)[0];
        double ymin = CoordConv.image2Graph(IGNORE, mousePressedEvent.getY() + windowHeight / 2, RENDER_WINDOW)[1];
        double ymax = CoordConv.image2Graph(IGNORE, mousePressedEvent.getY() - windowHeight / 2, RENDER_WINDOW)[1];

        System.out.printf("UPDATED RENDER WINDOW FROM %f %f %f %f TO %f %f %f %f\n",
                RENDER_WINDOW.xmin, RENDER_WINDOW.xmax, RENDER_WINDOW.ymin, RENDER_WINDOW.ymax, xmin, xmax, ymin, ymax);

        // update window
        RENDER_WINDOW.xmin = xmin;
        RENDER_WINDOW.xmax = xmax;
        RENDER_WINDOW.ymin = ymin;
        RENDER_WINDOW.ymax = ymax;
    }

    private void updateRenderWindowAfterResize()
    {
        // fit render window to aspect ratio of gui
        final double guiAspectRatio = (double) displayWidth / (double) displayHeight;
        final double windowAspectRatio = (double) RENDER_WINDOW.xnum / (double) RENDER_WINDOW.ynum;

        RENDER_WINDOW.xnum = displayWidth;
        RENDER_WINDOW.ynum = displayHeight;

        double xcenter = RENDER_WINDOW.xmin + (RENDER_WINDOW.xmax - RENDER_WINDOW.xmin) / 2;
        double ycenter = RENDER_WINDOW.ymin + (RENDER_WINDOW.ymax - RENDER_WINDOW.ymin) / 2;

        double width = Math.min(RENDER_WINDOW.xmax - RENDER_WINDOW.xmin, windowAspectRatio * displayHeight);
        double height = Math.min(RENDER_WINDOW.ymax - RENDER_WINDOW.ymin, (1 / windowAspectRatio) * displayWidth);

        RENDER_WINDOW.xmin = xcenter - width / 2;
        RENDER_WINDOW.xmax = xcenter + width / 2;

        RENDER_WINDOW.ymin = ycenter - height / 2;
        RENDER_WINDOW.ymax = ycenter + height / 2;
    }

    private int[] calculateMandelbrotPixels()
    {
        int[] arr = new int[RENDER_WINDOW.xnum * RENDER_WINDOW.ynum];
        int[][] iterationGrid = MANDELBROT_CALCULATOR.calculate(NUM_THREADS, MAX_DISTANCE, MAX_ITERATIONS, RENDER_WINDOW);

        loadPixels();

        for (int i = 0; i < RENDER_WINDOW.xnum * RENDER_WINDOW.ynum; i++)
        {
            int iteration = iterationGrid[i / RENDER_WINDOW.xnum][i % RENDER_WINDOW.xnum];
            int color = COLOR_MAPPER.color(MAX_ITERATIONS, iteration, this);
            arr[i] = color;
        }

        return arr;
    }
}
