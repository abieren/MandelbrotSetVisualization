package visualization.colormapper;

import processing.core.PApplet;

public class BlackWhiteMapper implements IColorMapper
{
    private final int numColorWraps;
    private final boolean invert;

    public BlackWhiteMapper(int numColorWraps, boolean invert)
    {
        this.numColorWraps = numColorWraps;
        this.invert = invert;
    }

    @Override
    public int color(int maxIterations, int iterations, PApplet applet)
    {
        // total color range:
        // 255 w->b
        // 255 b->w

        int totalColorRange = 2 * 255;
        int progression = (int) (totalColorRange * ((double)iterations / (double)maxIterations));
        progression = (progression * numColorWraps) % totalColorRange;

        int gray;
        switch (progression / 255)
        {
            case 0:
                gray = progression % 255;
                break;
            case 1:
                gray = 255 - (progression % 255);
                break;
            default:
                throw new RuntimeException("unexpected case");
        }
        gray = invert ? 255 - gray : gray;

        return applet.color(gray, gray, gray);
    }
}
