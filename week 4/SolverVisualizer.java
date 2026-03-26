import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Visualizer for the 8-puzzle solver.
 * Animates the solution step by step.
 */
public class SolverVisualizer {

    private static final int DEFAULT_DELAY_MS = 2000;

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

        // Solve the puzzle
        Solver solver = new Solver(initial);

        // Set up drawing
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(-0.5, n + 0.5);
        StdDraw.setYscale(-0.5, n + 0.5);

        // Draw initial board
        drawBoard(initial, "Initial Board");
        StdDraw.show();
        StdDraw.pause(delayMs);

        if (!solver.isSolvable()) {
            drawMessage("No solution possible", n);
            StdDraw.show();
            return;
        }

        // Animate solution
        // Note: solution() includes the initial board, so we skip it and start from Move 1
        int moveCount = 0;
        boolean isFirst = true;
        for (Board board : solver.solution()) {
            if (isFirst) {
                isFirst = false;
                continue;  // Skip initial board (already shown)
            }
            moveCount++;
            String title = String.format("Move %d", moveCount);
            drawBoard(board, title);
            StdDraw.show();
            StdDraw.pause(delayMs);
        }

        // Draw completion message
        drawMessage("Solved in " + moveCount + " moves!", n);
        StdDraw.show();
    }

    private static void drawBoard(Board board, String title) {
        int n = board.dimension();
        double tileRadius = 0.48;

        // Clear the canvas
        StdDraw.clear();

        // Draw title
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.textLeft(0, n + 0.3, title);

        // Get tiles from board
        int[] tiles = getTiles(board);

        // Draw all tiles (blue for numbered, white for blank)
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i * n + j];
                double x = j + 0.5;
                double y = n - i - 0.5;

                if (tile == 0) {
                    StdDraw.setPenColor(StdDraw.WHITE);
                } else {
                    StdDraw.setPenColor(StdDraw.BOOK_LIGHT_BLUE);
                }
                StdDraw.filledSquare(x, y, tileRadius);
            }
        }

        // Draw tile numbers
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                int tile = tiles[i * n + j];
                if (tile == 0) continue;
                double x = j + 0.5;
                double y = n - i - 0.5;
                StdDraw.text(x, y, Integer.toString(tile));
            }
        }

        // Draw grid lines (thin)
        StdDraw.setPenRadius(0.001);
        StdDraw.setPenColor(StdDraw.BLACK);
        for (int j = 1; j < n; j++) {
            StdDraw.line(j, 0, j, n);
        }
        for (int i = 1; i < n; i++) {
            StdDraw.line(0, i, n, i);
        }

        // Draw border as 4 line segments
        StdDraw.setPenRadius(0.002);
        StdDraw.line(0, 0, n, 0);      // bottom
        StdDraw.line(0, n, n, n);      // top
        StdDraw.line(0, 0, 0, n);      // left
        StdDraw.line(n, 0, n, n);      // right
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
        StdDraw.text(n / 2.0, n / 2.0, message);
        StdDraw.setPenColor(StdDraw.GRAY);
        StdDraw.text(n / 2.0, n / 2.0 - 0.5, "(close window to exit)");
    }
}
