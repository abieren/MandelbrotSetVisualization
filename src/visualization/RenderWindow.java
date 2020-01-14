package visualization;

public class RenderWindow
{
    public int xnum;
    public int ynum;
    public double xmin;
    public double xmax;
    public double ymin;
    public double ymax;

    public RenderWindow(int xnum, int ynum, double xmin, double xmax, double ymin, double ymax)
    {
        this.xnum = xnum;
        this.ynum = ynum;
        this.xmin = xmin;
        this.xmax = xmax;
        this.ymin = ymin;
        this.ymax = ymax;
    }
}
