package visualization.colormapper;

import processing.core.PApplet;

public interface IColorMapper
{
    public int color(int maxIterations, int iterations, PApplet applet);
}
