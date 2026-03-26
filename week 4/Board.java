import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * An immutable data type Board
 */
public class Board /* implements Comparable<Board> */ {
    
    private final int size;
    private int blankPos;    // index position for the blank block
    private int hamming;     // cache the hamming priority value
    private int manhattan;   // cache the manhattan priority value
    private final int[] tiles;
    private Board twinBoard;    // to make the Board object immutable, so return the same twinBoard for the same Board instance
    
    /**
     * Construct a board from an n-by-n array of blocks
     * (where blocks[i][j] = block in row i, column j)
     */
    public Board(int[][] blocks) {
        
        size = blocks.length;        
        tiles = new int[size * size];
        twinBoard = null;
        hamming = -1;
        manhattan = -1;
        
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                tiles[i * size + j] = blocks[i][j];
                
                if (blocks[i][j] == 0)
                    blankPos = i * size + j;
            }
        }
    }
    
    /**
     * board dimension n
     */
    public int dimension() {
        return size;
    }
    
    /**
     * number of blocks out of place
     */
    public int hamming() {
        if (hamming != -1)
            return hamming;
        
        // int count = 0;
        hamming = 0;
        
        for (int i = 0; i < size * size; i++) {
            // blank block doesn't count
            if (tiles[i] != 0 && tiles[i] != (i + 1))
                // count++;
                hamming++;
        }
        
        // return count;
        return hamming;
    }
    
    /**
     * sum of Manhattan distances between blocks and goal
     */
    public int manhattan() {
        // return the already computed manhattan value
        if (manhattan != -1)
            return manhattan;
        
        int curX, curY;        // converted 2D position from 1D for current index
        int destX, destY;      // converted 2D position from 1D for the current tile's goal position
        // int count = 0;
        manhattan = 0;
        
        for (int i = 0; i < size * size; i++) {
            // blank block doesn't count
            if (tiles[i] != 0 && tiles[i] != (i + 1)) {
                curX = i / size;
                curY = i % size;
                destX = (tiles[i] - 1) / size;
                destY = (tiles[i] - 1) % size;
                // count = count + (Math.abs(curX - destX) + Math.abs(curY - destY));
                manhattan = manhattan + (Math.abs(curX - destX) + Math.abs(curY - destY));               
            }
        }       
        
        return manhattan;
        
    }
    
    /**
     * is this board the goal board?
     */
    public boolean isGoal() {
        
        /*
        for (int i = 0; i < size * size; i++) {
            // blank block doesn't count
            if (tiles[i] != 0 && tiles[i] != (i + 1))
                return false;
        }
        
        return true;
        */
        
        return (hamming() == 0);
    }
    
    /**
     * a board that is obtained by exchanging any pair of blocks
     * (the blank square is not a block)
     * 
     * @return twin board
     */
    public Board twin() {
        if (twinBoard == null) {
            int randPos1, randPos2;
            
            // Calculate two random positions for exchanging any pair of blocks
            // StdRandom.setSeed(0);
            randPos1 = StdRandom.uniform(size * size);
            randPos2 = StdRandom.uniform(size * size);
            if (tiles[randPos1] == 0)
                randPos1 = (randPos1 + 1) % (size * size);
            if (tiles[randPos2] == 0)
                randPos2 = (randPos2 + 1) % (size * size);
            
            if (randPos1 == randPos2) {
                randPos2 = (randPos2 + 1) % (size * size);
                
                // Need to check against the blank block again for the position of randPos2
                if (tiles[randPos2] == 0)
                    randPos2 = (randPos2 + 1) % (size * size);                
            }
            
            // Get the twin blocks
            int[][] twinBlocks = new int[size][size];
            for (int i = 0; i < size; i++) {
                for (int j = 0; j < size; j++)
                    twinBlocks[i][j] = tiles[i * size + j];
            }
            twinBlocks[randPos1 / size][randPos1 % size] = tiles[randPos2];
            twinBlocks[randPos2 / size][randPos2 % size] = tiles[randPos1];
            
            twinBoard = new Board(twinBlocks);
        }
        
        return twinBoard;
    }
    
    /**
     * Compare this board to the specified board
     * 
     * @param other the other board
     * @return {@code true} if this board equals {@code other}; {@code false} otherwise
     */
    public boolean equals(Object other) {
        if (other == this) return true;
        if (other == null) return false;
        if (other.getClass() != this.getClass()) return false;
        
        Board that = (Board) other;
        
        if (size != that.dimension()) return false;
        
        // return this.toString().equals(that.toString());
        // return (this.hamming() == that.hamming() && this.manhattan() == that.manhattan()); 
        for (int i = 0; i < size * size; i++) {
            if (this.tiles[i] != that.tiles[i])    // can access the object that' private array tiles directly
                return false;
        }
        
        return true;
    }
    
    /**
     * implement the comparable interface
     */
    /*
    public int compareTo(Board that) {
        if (this.manhattan() < that.manhattan()) return -1;
        if (this.manhattan() == that.manhattan()) return 0;
        return 1;
    }
    */
    
    /**
     * all neighboring boards
     */
    public Iterable<Board> neighbors() {
        Queue<Board> neighbors = new Queue<Board>();
        Board neighborBoard;
        
        int curX, curY;        // converted to 2D coordinates for blank block
        curX = blankPos / size;
        curY = blankPos % size;
        
        int[][] blocks = new int[size][size];
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++)
                blocks[i][j] = tiles[i * size + j];
        } 
        
        // Get the neighbor boards in 4 directions
        if (curX - 1 >= 0) {
            blocks[curX - 1][curY] = 0;
            blocks[curX][curY] = tiles[(curX - 1) * size + curY];
            neighborBoard = new Board(blocks);
            neighbors.enqueue(neighborBoard);
            
            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX - 1][curY] = tiles[(curX - 1) * size + curY];
        }
        
        if (curX + 1 <= (size - 1)) {
            blocks[curX + 1][curY] = 0;
            blocks[curX][curY] = tiles[(curX + 1) * size + curY];
            neighborBoard = new Board(blocks);
            neighbors.enqueue(neighborBoard);
            
            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX + 1][curY] = tiles[(curX + 1) * size + curY];            
        }        
        
        if (curY - 1 >= 0) {
            blocks[curX][curY - 1] = 0;
            blocks[curX][curY] = tiles[curX * size + (curY - 1)];
            neighborBoard = new Board(blocks);
            neighbors.enqueue(neighborBoard);
            
            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX][curY - 1] = tiles[curX * size + (curY - 1)];             
        }
        
        if (curY + 1 <= (size - 1)) {
            blocks[curX][curY + 1] = 0;
            blocks[curX][curY] = tiles[curX * size + (curY + 1)];
            neighborBoard = new Board(blocks);
            neighbors.enqueue(neighborBoard);
            
            // restore the state to the search node
            blocks[curX][curY] = 0;
            blocks[curX][curY + 1] = tiles[curX * size + (curY + 1)];              
        }        
        
        return neighbors;
    }
    
    /**
     * string representation of this board (in the output format specified below)
     */
    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(size + "\n");
        
        for (int i = 0; i < size * size; i++) {
            s.append(String.format("%2d ", tiles[i]));
            
            if ((i + 1) % size == 0)
                s.append("\n");
        }
        return s.toString();        
    }
    
    /**
     * unit tests (not graded)
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks); 
        StdOut.println(initial);
        StdOut.println("Manhattan value: " + initial.manhattan());        
        /*
         Board initialCopy = new Board(blocks);
         StdOut.println(initialCopy);
         if (initial.equals(initialCopy))
         StdOut.println("There are two identical boards.");
         */
        /*
         StdOut.println("Hamming value: " + initial.hamming());
         StdOut.println("Manhattan value: " + initial.manhattan());
         */
        /*
        StdOut.println("Generating twin board......");
        Board twinBoard = initial.twin();
        StdOut.println(twinBoard);
        if (twinBoard.isGoal())
            StdOut.println("The twin board is the goal!");
        */
        /*
         for (Board board : initial.neighbors())
         StdOut.println(board);
         */
    }
}