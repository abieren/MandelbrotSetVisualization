package mandelbrot;

public class Mandelbrot
{
    public static int mandelbrot(final double creal, final double cimag, double maxDistance, int maxIterations)
    {
        final double maxDistSquared = maxDistance * maxDistance;
        double real = 0;
        double imag = 0;

        for (int i = 0; i < maxIterations; i++)
        {
            // check if outside range
            if (real*real + imag*imag > maxDistSquared) return i;

            // square current coordinate
            double tmp_real = real*real - imag*imag;
            double tmp_imag = 2*imag*real;

            // add c coordinate
            real = tmp_real + creal;
            imag = tmp_imag + cimag;
        }

        return maxIterations;
    }
}
