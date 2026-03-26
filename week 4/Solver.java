import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

/**
 * Solver for 8 puzzle problem
 */
public class Solver {
    private int moves;                // min moves to reach the goal board
    private boolean solvable;         // is the board solvable?
    private boolean calledSolvable;
    private Stack<Board> solution;    // solution if solvable
    private final Node root;                // initial Node corresponding to initial board
    
    /**
     * find a solution to the initial board (using the A* algorithm)
     * 
     * @param initial initial board
     */
    public Solver(Board initial) {
        if (initial == null)
            // throw new NullPointerException();   
            throw new IllegalArgumentException("points are null");
        
        moves = 0;
        calledSolvable = false;
        solvable = false;
        solution = null;
        root = new Node(initial, null, initial.manhattan(), 0);
    }
    
    /**
     * is the initial board solvable?
     * 
     * Run the A* algorithm on two puzzle instances, one with the initial board 
     * and one with the initial board modified by swapping a pair of blocks in lockstep 
     * (alternating back and forth between exploring search nodes in each of the two game trees). 
     * Exactly one of the two will lead to the goal board.
     * 
     * @return true if solvable; false otherwise
     */
    public boolean isSolvable() {
        if (!calledSolvable) {
            calledSolvable = true;
            
            Board twinBoard = this.root.curBoard.twin();
            Node twinNode = new Node(twinBoard, null, twinBoard.manhattan(), 0);
            
            Node curNode = null;
            Node curTwinNode = null;
            
            MinPQ<Node> pq = new MinPQ<Node>();   
            MinPQ<Node> twinPQ = new MinPQ<Node>();
            
            boolean pqEmpty = false;
            boolean twinPQEmpty = false;
            
            pq.insert(root);
            twinPQ.insert(twinNode);
            
            while (!pq.isEmpty() || !twinPQ.isEmpty()) {
                
                if (pq.isEmpty())
                    pqEmpty = true;
                if (twinPQ.isEmpty())
                    twinPQEmpty = true;
                
                if (!pqEmpty) {
                    curNode = pq.delMin();
                }
                
                if (!twinPQEmpty) {
                    curTwinNode = twinPQ.delMin();
                }
                
                // Found the goal board on the original node
                if (!pqEmpty && curNode != null && curNode.curBoard.isGoal()) {

                    this.moves = curNode.nMoves;
                    
                    solution = new Stack<Board>();
                    do {
                        solution.push(curNode.curBoard);
                        curNode = curNode.prevNode;
                    } while (curNode != null);
                    
                    solvable = true;
                    return true;
                }
                
                // Found the goal board on the twin node
                if (!twinPQEmpty && curTwinNode != null && curTwinNode.curBoard.isGoal()) {
                    solvable = false;
                    this.moves = -1;
                    return false;                
                }
                
                // Iterate through all the neighbors of the currently dequeueed node
                if (curNode != null) {
                   
                    for (Board neighbor : curNode.curBoard.neighbors()) {
                        if (curNode.prevNode != null && neighbor.equals(curNode.prevNode.curBoard))
                            continue;
                        
                        pq.insert(new Node(neighbor, curNode, neighbor.manhattan(), curNode.nMoves + 1));
                    }
                }
                
                // Iterate through all the neighbors of the currently dequeueed node
                if (curTwinNode != null) {
                    for (Board twinNeighbor : curTwinNode.curBoard.neighbors()) {
                        if (curTwinNode.prevNode != null && twinNeighbor.equals(curTwinNode.prevNode.curBoard))
                            continue;
                        
                        twinPQ.insert(new Node(twinNeighbor, curTwinNode, twinNeighbor.manhattan(), curTwinNode.nMoves + 1));
                    }
                }
                
                curNode = null;
                curTwinNode = null;
                
            }        
            return false;
        }
        
        return solvable;
    }
    
    /**
     * min number of moves to solve initial board; -1 if unsolvable
     */
    public int moves() {
        if (!calledSolvable) {
            if (!isSolvable())
                return -1;
            else 
                return this.moves;
        }
        else
            return this.moves;
    }
    
    /**
     * sequence of boards in a shortest solution; null if unsolvable
     */
    public Iterable<Board> solution() {
        if (!calledSolvable) {
            if (!isSolvable())
                return null;
            else
                return this.solution;
        }
        else
            return this.solution;
    }
    
    private class Node implements Comparable<Node> {
        Board curBoard;
        Node prevNode;
        int nMoves;
        int manhattan;    // cache the manhattan value to avoid the overhead of too many functiona calls
        
        Node(Board curBoard, Node prevNode, int manhattan, int nMoves) {
            this.curBoard = curBoard;
            this.prevNode = prevNode;
            this.manhattan = manhattan;
            this.nMoves = nMoves;            
        }
        
        public int compareTo(Node that) {
            if ((this.manhattan + this.nMoves) == (that.manhattan + that.nMoves))
                return 0;
            else if ((this.manhattan + this.nMoves) < (that.manhattan + that.nMoves))
                return -1;
            else
                return 1;            
        }
    }
    
    /**
     * solve a slider puzzle
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

        // solve the puzzle
        Solver solver = new Solver(initial);
       
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        } 
    }
}