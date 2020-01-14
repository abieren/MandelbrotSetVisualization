package mandelbrot;

public class MandelbrotPayload
{
    public int startRow;
    public int numRows;
    public double startImag;
    public double deltaImag;

    public int startCol;
    public int numCols;
    public double startReal;
    public double deltaReal;

    public MandelbrotPayload(int startRow, int numRows, double startImag, double deltaImag, int startCol, int numCols, double startReal, double deltaReal)
    {
        this.startRow = startRow;
        this.numRows = numRows;
        this.startImag = startImag;
        this.deltaImag = deltaImag;
        this.startCol = startCol;
        this.numCols = numCols;
        this.startReal = startReal;
        this.deltaReal = deltaReal;
    }

    public static MandelbrotPayload[] distributeRows(int numThreads, double ymin, double ymax, int ynum, double xmin, double xmax, int xnum)
    {
        final double xdelta = (xmax - xmin) / ((double)(xnum - 1));
        final double ydelta = (ymax - ymin) / ((double)(ynum - 1));

        if (xdelta < 0) throw new RuntimeException("xdelta cannot be negative");
        if (ydelta < 0) throw new RuntimeException("ydelta cannot be negative");

        MandelbrotPayload[] payloads = new MandelbrotPayload[numThreads];
        int[] rowSplits = splitEqualy(ynum, numThreads);
        int startRow = 0;

        for (int i = 0; i < numThreads; i++)
        {
            final int       numRows     = rowSplits[i];
            final double    startImag   = ymax - startRow * ydelta;  //ymin + startRow * ydelta;
            final double    deltaImag   = ydelta;
            final int       startCol    = 0;
            final int       numCols     = xnum;
            final double    startReal   = xmin;
            final double    deltaReal   = xdelta;

            payloads[i] = new MandelbrotPayload(startRow, numRows, startImag, deltaImag, startCol, numCols, startReal, deltaReal);
            startRow += numRows;
        }

        return payloads;
    }

    public static int[] splitEqualy(int volume, int numSplits)
    {
        final int[] splits = new int[numSplits];

        final int vol = (volume % numSplits) == 0 ? volume : volume + (numSplits - (volume % numSplits));
        final int splitSize = vol / numSplits;

        for (int i = 0; i < numSplits - 1; i++)
        {
            splits[i] = splitSize;
        }
        splits[numSplits - 1] = volume - (numSplits - 1) * splitSize;

        return splits;
    }
}
