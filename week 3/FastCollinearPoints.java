import java.util.Arrays;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

/**
 * A fast, sort-based solution
 */

public class FastCollinearPoints {
    private int countLines;           // the number of line segments containing 4 points
    private Node first;               // the first node of the linked line segments
    
    /**
     * finds all line segments containing 4 or more points
     * @param points a Point array
     * @throw a java.lang.IllegalArgumentException if the argument to the constructor is null, 
     * if any point in the array is null, or if the argument to the constructor contains a repeated point.
     */
    public FastCollinearPoints(Point[] points) {
        int i, j;
        
        // Throw a java.lang.IllegalArgumentException if the argument to the constructor is null, 
        // if any point in the array is null, or if the argument to the constructor contains a repeated point.
        if (points == null)
            // throw new NullPointerException();   
            throw new IllegalArgumentException("points are null");
        
        int n = points.length;
        Point[] clone = new Point[n];    // not to mutate the argument points by using a copy
        
        for (i = 0; i < n; i++) {
            if (points[i] == null)
                // throw new NullPointerException();
                throw new IllegalArgumentException("one of points is null");
        }
        
        for (i = 0; i < n; i++) {
            for (j = (i + 1); j < n; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("two points are identical");
            }
            
            clone[i] = points[i];
        }
        
        Arrays.sort(clone);
        
        if (n < 4)
            return;
        
        int index;   
        
        countLines = 0;
        
        first = null;           // Initialize the linked list
        
        int count;              // number of adjacent points that have the same slope
        Point[] tempPts = new Point[n - 1];        // temp points array for sorting by slope order
        Point curPt, minPt, maxPt;
        minPt = null;
        maxPt = null;
        
        for (i = 0; i < (n - 1); i++) {
            curPt = clone[i];
            minPt = maxPt = null;
            
            index = 0;
            for (j = 0; j < n; j++) {
                if (i != j) tempPts[index++] = clone[j];
            }
            
            Arrays.sort(tempPts, curPt.slopeOrder());
            
            count = 1;
            
            for (j = 0; j < (tempPts.length - 1); j++) {
                
                if (curPt.slopeTo(tempPts[j]) == curPt.slopeTo(tempPts[j + 1])) {
                    
                    if (minPt == null) {
                        if (curPt.compareTo(tempPts[j]) > 0) {
                            maxPt = curPt;
                            minPt = tempPts[j];
                        } else {
                            maxPt = tempPts[j];
                            minPt = curPt;
                        }
                    }
                    
                    if (minPt.compareTo(tempPts[j + 1]) > 0) {
                        minPt = tempPts[j + 1];
                    }
                    
                    if (maxPt.compareTo(tempPts[j + 1]) < 0) {
                        maxPt = tempPts[j + 1];
                    }
                    
                    count++;
                    
                    if (j == (tempPts.length - 2)) {
                        if (count >= 3 && curPt.compareTo(minPt) == 0) {
                            addLine(minPt, maxPt);
                        }
                        
                        count  = 1;
                        minPt = null;
                        maxPt = null;
                    }
                } else {
                    if (count >= 3 && curPt.compareTo(minPt) == 0) {
                        addLine(minPt, maxPt);
                    }
                    
                    count = 1;
                    minPt = null;
                    maxPt = null;
                }
            }
        }
    }
    
    /**
     * the number of line segments
     * @return the number of line segments
     */
    public int numberOfSegments() {
        return countLines;
    }
    
    /**
     * the line segments
     */
    public LineSegment[] segments() {
        LineSegment[] lines = new LineSegment[countLines];
        Node cur = first;
        
        for (int i = 0; i < countLines; i++) {
            lines[i] = cur.item;
            cur = cur.next;
        }
        
        return lines;
    }
    
    /**
     * Add the line segment to the linked line list
     * @param p, q
     * @return void
     */
    private void addLine(Point p, Point q) {
        LineSegment line = new LineSegment(p, q);
        
        Node oldfirst = first;
        first = new Node();
        first.item = line;
        first.next = oldfirst;
        countLines++;
        
        return;
    }
    
    private class Node {
        LineSegment item;
        Node next;
    }
    
    public static void main(String[] args) {
        
        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }
        
        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();
        
        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}