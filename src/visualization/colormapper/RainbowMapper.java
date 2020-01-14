package visualization.colormapper;

import processing.core.PApplet;

public class RainbowMapper implements IColorMapper
{
    private final int numColorWraps;

    public RainbowMapper(int numColorWraps)
    {
        this.numColorWraps = numColorWraps;
    }

    @Override
    public int color(int maxIterations, int iterations, PApplet applet)
    {
        if (iterations == maxIterations) return applet.color(0, 0, 0);

        // total color range:
        // 255 r
        // 255 r, g
        // 255 g
        // 255 g, b
        // 255 b
        // 255 b, r

        final int totalColorRange = 6 * 255;
        int progression = (int) (totalColorRange * ((double)iterations / (double)maxIterations));
        progression = (progression * numColorWraps) % totalColorRange;

        int r = 0, g = 0, b = 0;
        switch (progression / 255)
        {
            case 0:
                r = 255;
                g = progression % 255;
                break;
            case 1:
                r = 255 - (progression % 255);
                g = 255;
                break;
            case 2:
                g = 255;
                b = progression % 255;
                break;
            case 3:
                g = 255 - (progression % 255);
                b = 255;
                break;
            case 4:
                b = 255;
                r = progression % 255;
                break;
            case 5:
                b = 255 - (progression % 255);
                break;
            default:
                throw new RuntimeException("unexpected case");
        }

        return applet.color(r, g, b);
    }
}
