package visualization;

public class IterationGridPrinter
{
    static final String ANSI_RESET = "\u001B[0m";
    static final String ANSI_BLACK = "\u001B[30m";
    static final String ANSI_RED = "\u001B[31m";
    static final String ANSI_GREEN = "\u001B[32m";
    static final String ANSI_YELLOW = "\u001B[33m";
    static final String ANSI_BLUE = "\u001B[34m";
    static final String ANSI_PURPLE = "\u001B[35m";
    static final String ANSI_CYAN = "\u001B[36m";
    static final String ANSI_WHITE = "\u001B[37m";

    static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
    static final String ANSI_RED_BACKGROUND = "\u001B[41m";
    static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
    static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
    static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
    static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

    static final String[] colorMapping = new String[]{
            ANSI_WHITE_BACKGROUND,
            ANSI_RED_BACKGROUND,
            ANSI_GREEN_BACKGROUND,
            ANSI_YELLOW_BACKGROUND,
            ANSI_BLUE_BACKGROUND,
            ANSI_PURPLE_BACKGROUND,
            ANSI_CYAN_BACKGROUND,
            ANSI_GREEN_BACKGROUND,
            ANSI_YELLOW_BACKGROUND,
            ANSI_BLUE_BACKGROUND
    };

    static final String[] colorMappingWithFont = new String[]{
            ANSI_WHITE_BACKGROUND + ANSI_WHITE,
            ANSI_RED_BACKGROUND + ANSI_RED,
            ANSI_GREEN_BACKGROUND + ANSI_GREEN,
            ANSI_YELLOW_BACKGROUND + ANSI_YELLOW,
            ANSI_BLUE_BACKGROUND + ANSI_BLUE,
            ANSI_PURPLE_BACKGROUND + ANSI_PURPLE,
            ANSI_CYAN_BACKGROUND + ANSI_CYAN,
            ANSI_GREEN_BACKGROUND + ANSI_GREEN,
            ANSI_YELLOW_BACKGROUND + ANSI_YELLOW,
            ANSI_BLUE_BACKGROUND + ANSI_BLUE
    };

    public static void print(int[][] grid, int maxIterations, boolean colorFont)
    {
        for (int row = 0; row < grid.length; row++)
        {
            for (int col = 0; col < grid[0].length; col++)
            {
                int iterations = grid[row][col];
                String formatString = "%1d";
                if (iterations == maxIterations)
                {
                    formatString = ANSI_BLACK_BACKGROUND + (colorFont ? ANSI_BLACK : "") + formatString + ANSI_RESET;
                }
                else
                {
                    formatString = (colorFont ? colorMappingWithFont : colorMapping)[iterations % 10] + "%1d" + ANSI_RESET;
                }

                System.out.printf(formatString, iterations % 10);
            }
            System.out.println();
        }
    }
}
