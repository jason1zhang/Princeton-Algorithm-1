import edu.princeton.cs.algs4.Stack;
// import edu.princeton.cs.algs4.SET;
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
            
            // SET<Node> set = new SET<Node>();
            // SET<Node> twinSet = new SET<Node>();
            
            boolean pqEmpty = false;
            boolean twinPQEmpty = false;
            
            // int twinMoves = 0;
            
            /*
             StdOut.println("initial node......................");    
             StdOut.println("current node manhattan value: " + root.curBoard.manhattan()); 
             StdOut.println("current node move value: " + root.nMoves);            
             StdOut.println(root.curBoard);
             StdOut.println("---------------------------------------\n"); 
             */
            
            pq.insert(root);
            twinPQ.insert(twinNode);
            
            /*
             StdOut.println("Original board");
             StdOut.println(pq.min().curBoard);
             StdOut.println("Twin board");
             StdOut.println(twinPQ.min().curBoard);        
             */
            
            while (!pq.isEmpty() || !twinPQ.isEmpty()) {
                
                if (pq.isEmpty())
                    pqEmpty = true;
                if (twinPQ.isEmpty())
                    twinPQEmpty = true;
                
                if (!pqEmpty) {
                    curNode = pq.delMin();
                    // set.add(curNode);
                }
                
                if (!twinPQEmpty) {
                    curTwinNode = twinPQ.delMin();
                    // twinSet.add(curTwinNode);
                }
                
                // Found the goal board on the original node
                if (!pqEmpty && curNode != null && curNode.curBoard.isGoal()) {
                    // StdOut.println("Hooray, the goal board is found on the original board!");
                    
                    // this.moves++;
                    /*
                     StdOut.println("current node manhattan value: " + curNode.curBoard.manhattan());
                     StdOut.println("current node move value: " + curNode.nMoves);
                     StdOut.println(curNode.curBoard);
                     StdOut.println("---------------------------------------\n"); 
                     */
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
                    /*
                     StdOut.println("Oops, the goal board is found on the twin board!");
                     do {
                     StdOut.println("in the twin......");
                     StdOut.println(curTwinNode.curBoard);
                     curTwinNode = curTwinNode.prevNode;
                     } while (curTwinNode != null);
                     */
                    solvable = false;
                    this.moves = -1;
                    return false;                
                }
                
                // moves++;        // increment the number of moves
                
                // Iterate through all the neighbors of the currently dequeueed node
                if (curNode != null) {
                    // this.moves++;
                    /*
                     StdOut.println("current node manhattan value: " + curNode.curBoard.manhattan());
                     StdOut.println("current node move value: " + curNode.nMoves);
                     StdOut.println(curNode.curBoard);
                     StdOut.println("---------------------------------------\n");                   
                     */
                    
                    for (Board neighbor : curNode.curBoard.neighbors()) {
                        if (curNode.prevNode != null && neighbor.equals(curNode.prevNode.curBoard))
                            // if (set.contains(new Node(neighbor, curNode, moves)))
                            continue;
                        
                        pq.insert(new Node(neighbor, curNode, neighbor.manhattan(), curNode.nMoves + 1));
                    }
                }
                
                // Remove the curNode (the one with min priority) from the priority queue
                
                // if (!pqEmpty) pq.delMin();
                
                // Iterate through all the neighbors of the currently dequeueed node
                if (curTwinNode != null) {
                    // twinMoves++;
                    for (Board twinNeighbor : curTwinNode.curBoard.neighbors()) {
                        if (curTwinNode.prevNode != null && twinNeighbor.equals(curTwinNode.prevNode.curBoard))
                            // if (twinSet.contains(new Node(twinNeighbor, curTwinNode, moves)))
                            continue;
                        
                        twinPQ.insert(new Node(twinNeighbor, curTwinNode, twinNeighbor.manhattan(), curTwinNode.nMoves + 1));
                    }
                }
                
                // Remove the curNode (the one with min priority) from the priority queue
                // if (!twinPQEmpty) twinPQ.delMin();
                
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
        /*
         if (!solvable)
         return -1;
         else
         return this.moves;
         */
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
        int manhattan;
        
        Node(Board curBoard, Node prevNode, int manhattan, int nMoves) {
            this.curBoard = curBoard;
            this.prevNode = prevNode;
            this.manhattan = manhattan;
            this.nMoves = nMoves;            
        }
        
        public int compareTo(Node that) {
            /*
            if ((this.curBoard.manhattan() + this.nMoves) == (that.curBoard.manhattan() + that.nMoves))
                return 0;
            
            if ((this.curBoard.manhattan() + this.nMoves) < (that.curBoard.manhattan() + that.nMoves))
                return -1;
            
            return 1;
            */
            
            if ((this.manhattan + this.nMoves) == (that.manhattan + that.nMoves))
                return 0;
            
            if ((this.manhattan + this.nMoves) < (that.manhattan + that.nMoves))
                return -1;
            
            return 1;            
        }
        
        
    }
    
    /**
     * solve a slider puzzle
     */
    public static void main(String[] args) {
        // create initial board from file
        In in = new In(args[0]);
        // In in = new In("./8puzzle-testing/puzzle07.txt");
        int n = in.readInt();
        int[][] blocks = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
            blocks[i][j] = in.readInt();
        Board initial = new Board(blocks);
        
        /*
         StdOut.println("initial node......................");
         StdOut.println(initial);
         StdOut.println("---------------------------------------\n");         
         */
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        } 
        
    }
}