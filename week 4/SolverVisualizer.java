import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import java.awt.Color;
import java.awt.Font;
import java.util.Random;

/**
 * Visualizer for the 8-puzzle solver.
 * Animates the solution step by step.
 */
public class SolverVisualizer {

    private static final int DEFAULT_DELAY_MS = 2000;
    private static Random random = new Random();

    public static void main(String[] args) {
        // Parse command line arguments
        if (args.length < 1) {
            StdOut.println("Usage: java SolverVisualizer <puzzle_file> [delay_ms]");
            return;
        }

        int delayMs = DEFAULT_DELAY_MS;
        if (args.length >= 2) {
            delayMs = Integer.parseInt(args[1]);
        }

        // Read puzzle from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                blocks[i][j] = in.readInt();
            }
        }

        Board initial = new Board(blocks);
        int maxTile = n * n - 1;

        // Solve the puzzle
        Solver solver = new Solver(initial);

        // Set up drawing
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-0.5, n + 0.5);
        StdDraw.setYscale(-0.5, n + 0.5);

        // Draw goal state first
        drawGoal(n, "Goal State");

        // Show goal for 2 seconds
        StdDraw.show();
        StdDraw.pause(2000);

        // Draw initial board
        drawBoard(initial, "Initial Board", maxTile);
        StdDraw.show();
        StdDraw.pause(delayMs);

        if (!solver.isSolvable()) {
            drawMessage("No solution possible", n);
            StdDraw.show();
            return;
        }

        // Animate solution
        int moveCount = 0;
        boolean isFirst = true;
        for (Board board : solver.solution()) {
            if (isFirst) {
                isFirst = false;
                continue;  // Skip initial board (already shown)
            }
            moveCount++;
            String title = String.format("Move %d", moveCount);
            drawBoard(board, title, maxTile);
            StdDraw.show();
            StdDraw.pause(delayMs);
        }

        // Draw completion message
        drawSolvedMessage(moveCount, n);
        StdDraw.show();
    }

    private static void drawGoal(int n, String title) {
        double tileRadius = 0.47;

        // Clear the canvas with light gray background
        StdDraw.clear(StdDraw.LIGHT_GRAY);

        // Draw board background (white)
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(n / 2.0, n / 2.0, n / 2.0, n / 2.0);

        // Draw title with larger font
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
        StdDraw.textLeft(0.1, n + 0.3, title);
        StdDraw.setFont();

        // Draw goal state tiles (1, 2, 3, ... in order)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = i * n + j + 1;
                double x = j + 0.5;
                double y = n - i - 0.5;

                if (tile > n * n - 1) {
                    // Empty space
                    StdDraw.setPenColor(new Color(230, 230, 230));
                    StdDraw.filledSquare(x, y, tileRadius);
                    StdDraw.setPenColor(new Color(180, 180, 180));
                    StdDraw.setPenRadius(0.003);
                    StdDraw.square(x, y, tileRadius);
                } else {
                    // Draw colored tile
                    StdDraw.setPenColor(getGoalTileColor(tile, n * n - 1));
                    StdDraw.filledSquare(x, y, tileRadius);

                    // Draw tile border
                    StdDraw.setPenRadius(0.002);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(x, y, tileRadius);

                    // Draw tile number with larger font
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
                    StdDraw.text(x, y, Integer.toString(tile));
                    StdDraw.setFont();
                }
            }
        }

        // Draw grid lines
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(new Color(80, 80, 80));
        for (int j = 1; j < n; j++) {
            StdDraw.line(j, 0, j, n);
        }
        for (int i = 1; i < n; i++) {
            StdDraw.line(0, i, n, i);
        }

        // Draw border
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, n, 0);
        StdDraw.line(0, n, n, n);
        StdDraw.line(0, 0, 0, n);
        StdDraw.line(n, 0, n, n);
    }

    private static void drawBoard(Board board, String title, int maxTile) {
        int n = board.dimension();
        double tileRadius = 0.47;

        // Clear the canvas with light gray background
        StdDraw.clear(StdDraw.LIGHT_GRAY);

        // Draw board background (white)
        StdDraw.setPenColor(StdDraw.WHITE);
        StdDraw.filledRectangle(n / 2.0, n / 2.0, n / 2.0, n / 2.0);

        // Draw title with larger font
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 16));
        StdDraw.textLeft(0.1, n + 0.3, title);
        StdDraw.setFont();

        // Get tiles from board
        int[] tiles = getTiles(board);

        // Draw all tiles
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i * n + j];
                double x = j + 0.5;
                double y = n - i - 0.5;

                if (tile == 0) {
                    // Draw empty space with dashed border effect
                    StdDraw.setPenColor(new Color(230, 230, 230));
                    StdDraw.filledSquare(x, y, tileRadius);
                    StdDraw.setPenColor(new Color(180, 180, 180));
                    StdDraw.setPenRadius(0.003);
                    StdDraw.square(x, y, tileRadius);
                    // Draw inner dashed pattern
                    StdDraw.setPenRadius(0.001);
                    double innerRadius = tileRadius * 0.5;
                    StdDraw.line(x - innerRadius, y - innerRadius, x + innerRadius, y + innerRadius);
                    StdDraw.line(x - innerRadius, y + innerRadius, x + innerRadius, y - innerRadius);
                } else {
                    // Draw colored tile
                    StdDraw.setPenColor(getTileColor(tile, maxTile));
                    StdDraw.filledSquare(x, y, tileRadius);

                    // Draw tile border
                    StdDraw.setPenRadius(0.002);
                    StdDraw.setPenColor(StdDraw.BLACK);
                    StdDraw.square(x, y, tileRadius);

                    // Draw tile number with larger font
                    StdDraw.setPenColor(StdDraw.WHITE);
                    StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
                    StdDraw.text(x, y, Integer.toString(tile));
                    StdDraw.setFont();
                }
            }
        }

        // Draw grid lines
        StdDraw.setPenRadius(0.002);
        StdDraw.setPenColor(new Color(80, 80, 80));
        for (int j = 1; j < n; j++) {
            StdDraw.line(j, 0, j, n);
        }
        for (int i = 1; i < n; i++) {
            StdDraw.line(0, i, n, i);
        }

        // Draw border
        StdDraw.setPenRadius(0.003);
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.line(0, 0, n, 0);
        StdDraw.line(0, n, n, n);
        StdDraw.line(0, 0, 0, n);
        StdDraw.line(n, 0, n, n);
    }

    // Generate shuffled colors so adjacent tiles don't have similar colors
    private static Color getTileColor(int tile, int maxTile) {
        if (tile == 0) return Color.LIGHT_GRAY;

        // Predefined distinct color palette
        Color[] palette = {
            new Color(231, 76, 60),    // red
            new Color(52, 152, 219),   // blue
            new Color(46, 204, 113),  // green
            new Color(155, 89, 182),  // purple
            new Color(241, 196, 15),   // yellow
            new Color(230, 126, 34),   // orange
            new Color(26, 188, 156),   // teal
            new Color(155, 89, 182),   // purple (duplicate for larger puzzles)
            new Color(52, 73, 94),     // dark blue
            new Color(22, 160, 133),   // dark green
            new Color(192, 57, 43),    // dark red
            new Color(142, 68, 173),   // violet
            new Color(211, 84, 0),     // dark orange
            new Color(39, 174, 96),     // emerald
            new Color(41, 128, 185),   // nephritis blue
            new Color(243, 156, 18),   // amber
            new Color(195, 39, 43),    // crimson
            new Color(32, 102, 148),   // darker blue
            new Color(34, 139, 34),     // forest green
            new Color(139, 69, 19),     // saddle brown
            new Color(75, 0, 130),     // indigo
            new Color(255, 140, 0),    // dark orange
            new Color(0, 128, 128),    // teal
            new Color(128, 0, 128),    // purple
            new Color(0, 100, 0)       // dark green
        };

        // Shuffle the palette based on tile number for consistent but varied colors
        int index = (tile * 7 + tile * tile) % palette.length;
        return palette[index];
    }

    // Goal state uses sequential colors (blue gradient)
    private static Color getGoalTileColor(int tile, int maxTile) {
        float hue = (float) (tile - 1) / maxTile * 0.6f;  // Blue to green range
        return Color.getHSBColor(hue, 0.7f, 0.6f);
    }

    private static int[] getTiles(Board board) {
        String s = board.toString();
        String[] lines = s.split("\n");
        int n = board.dimension();
        int[] tiles = new int[n * n];

        // Skip first line (dimension), parse n rows
        for (int i = 1; i <= n; i++) {
            String[] tokens = lines[i].trim().split("\\s+");
            for (int j = 0; j < n; j++) {
                tiles[(i - 1) * n + j] = Integer.parseInt(tokens[j]);
            }
        }
        return tiles;
    }

    private static void drawMessage(String message, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 24));
        StdDraw.text(n / 2.0, n / 2.0, message);
        StdDraw.setFont();
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.text(n / 2.0, n / 2.0 - 0.5, "(close window to exit)");
    }

    private static void drawSolvedMessage(int moves, int n) {
        StdDraw.clear();
        StdDraw.setPenColor(new Color(46, 204, 113));  // Green
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 32));
        StdDraw.text(n / 2.0, n / 2.0 + 0.5, "SOLVED!");
        StdDraw.setFont();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setFont(new Font("SansSerif", Font.BOLD, 20));
        StdDraw.text(n / 2.0, n / 2.0 - 0.3, moves + " moves");
        StdDraw.setFont();
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.text(n / 2.0, n / 2.0 - 0.9, "(close window to exit)");
    }
}
