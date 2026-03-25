/*
 * Implement the Percolation data tppe using the WeightedQuickUnionUF algorithm
 * 
 */
// import edu.princeton.cs.algs4.StdRandom;
// import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    // Store the grid in 1D format
    private boolean[] grid;
    
    // Utilize the weighted quick union find algorithm to determine the full open sites
    private final WeightedQuickUnionUF ufFull;
    
    // Utilize the weighted quick union find algorithm to determine if percolate
    // Though it's not that efficient for memory usage by using two UF data structure, 
    // but it allows constant number of UF operations. 
    private final WeightedQuickUnionUF ufPerc;    
    
    // grid size
    private final int size;
    // number of opened sites
    private int openCount;
    
    // virtual site for the top row
    private final int virtualTop;
    
    // virtual site for the bottom row
    private final int virtualBottom;

    /*
     * Create n-by-n grid, with all sites blocked
     */
    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n should be greater than 0");

        this.size = n;
        this.grid = new boolean[n * n];
        
        // Initialize sites to be blocked 
        for (int i = 0; i < n * n; i++) {
            this.grid[i] = false;
        }
        
        this.openCount = 0;
        
        // '+ 1' for 1 additional virtual top site, which are placed at the end
        this.ufFull = new WeightedQuickUnionUF(n * n + 1);
        
        // '+ 2' for 2 additional virtual top and bottom sites, which are placed at the end
        this.ufPerc = new WeightedQuickUnionUF(n * n + 2);
        
        this.virtualTop = n * n;
        this.virtualBottom = n * n + 1;
    }
    
    /*
     * Open site (row, col) if it is not open already
     */
    public void open(int row, int col) {
        if (isOpen(row, col))
            return;
        
        int index = xyTo1D(row, col);
        grid[index] = true;
        openCount++;
        
        // Merge the virtual top site if open the site on the first row
        if (row == 1) {
            ufFull.union(index, virtualTop);
            ufPerc.union(index, virtualTop);         
        }
        
        // Merge the virtual bottom site if open the site on the bottom row                               
        if (row == this.size) 
            ufPerc.union(index, virtualBottom);

        // Merge the component above
        if (isValid(row - 1, col) && isOpen(row - 1, col)) {
            ufFull.union(index, xyTo1D(row - 1, col));
            ufPerc.union(index, xyTo1D(row - 1, col));           
            // return;
        }

        // Merge the component on the right
        if (isValid(row, col + 1) && isOpen(row, col + 1)) {
            ufFull.union(index, xyTo1D(row, col + 1));
            ufPerc.union(index, xyTo1D(row, col + 1));           
            // return;
        }
        
        // Merge the component below
        if (isValid(row + 1, col) && isOpen(row + 1, col)) {
            ufFull.union(index, xyTo1D(row + 1, col));
            ufPerc.union(index, xyTo1D(row + 1, col));           
            // return;
        }   
     
        // Merge the component on the left
        if (isValid(row, col - 1) && isOpen(row, col - 1)) {
            ufFull.union(index, xyTo1D(row, col - 1));
            ufPerc.union(index, xyTo1D(row, col - 1));          
           // return;
        }
        
        return;
    }
    
    /*
     * Is site (row, col) open?
     */
    public boolean isOpen(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException("row and col should be between 1 and n");
        
        return grid[xyTo1D(row, col)];
    }
    
    /*
     * Is site (row, col) full?
     */
    public boolean isFull(int row, int col) {
        if (!isValid(row, col))
            throw new IllegalArgumentException("row and col should be between 1 and n");
        
        return ufFull.connected(xyTo1D(row, col), virtualTop);
    }
    
    /*
     * Number of open sites
     */
    public int numberOfOpenSites() {
        return openCount;
    }
    
    /*
     * Does the system percolate?
     */
    public boolean percolates() {
        return ufPerc.connected(virtualTop, virtualBottom);
    }
    
    /*
     * Map 2D cooridinates to 1D coordinates
     * The coordinate (row, col) already being validated before calling this helper function
     */
    private int xyTo1D(int row, int col) {
        return (row - 1) * this.size + (col - 1);
        
    }
  
    /*
     * Check if the coordinate(row, col) is valid
     */
    private boolean isValid(int row, int col) {
        return (row >= 1 && row <= this.size && col >= 1 && col <= this.size);
    }
    
    /*
     * Test client
     */
    public static void main(String[] args) {
        // Use PercolationVisualizer and InteractivePercolationVisualizer to test the functionality of Percolation
    }

}