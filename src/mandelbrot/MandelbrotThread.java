package mandelbrot;

public class MandelbrotThread extends Thread
{
    private final int[][] iterationGrid;
    private final MandelbrotPayload payload;
    private final double maxDistance;
    private final int maxIterations;

    public MandelbrotThread(int[][] iterationGrid, MandelbrotPayload payload, double maxDistance, int maxIterations)
    {
        this.iterationGrid = iterationGrid;
        this.payload = payload;
        this.maxDistance = maxDistance;
        this.maxIterations = maxIterations;
    }

    @Override
    public void run()
    {
        for (int rowDelta = 0; rowDelta < payload.numRows; rowDelta++)
        {
            for (int colDelta = 0; colDelta < payload.numCols; colDelta++)
            {
                final int row  = payload.startRow + rowDelta;
                final int col  = payload.startCol + colDelta;
                final double imag = payload.startImag - rowDelta * payload.deltaImag;
                final double real = payload.startReal + colDelta * payload.deltaReal;

                iterationGrid[row][col] = Mandelbrot.mandelbrot(real, imag, maxDistance, maxIterations);
            }
        }
    }


}
