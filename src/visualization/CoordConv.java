package visualization;

public class CoordConv
{
    public static double[] image2Graph(
            int x, int y,
            int xnum, int ynum,
            double xmin, double xmax,
            double ymin, double ymax)
    {
        double[] graphCoords = new double[2];

        graphCoords[0] = xmin + ((double)x / (double)(xnum)) * (xmax - xmin);
        graphCoords[1] = ymax - ((double)y / (double)(ynum)) * (ymax - ymin);

        return graphCoords;
    }

    public static double[] image2Graph(int x, int y, RenderWindow renderWindow)
    {
        return image2Graph(
                x, y,
                renderWindow.xnum, renderWindow.ynum,
                renderWindow.xmin, renderWindow.xmax,
                renderWindow.ymin, renderWindow.ymax);
    }
}
