import java.util.Iterator;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdIn;

/**
 *  Client program Permutation.java that takes an integer k as a command-line argument; 
 *  reads in a sequence of strings from standard input using StdIn.readString(); 
 *  and prints exactly k of them, uniformly at random. 
 *  Print each item from the sequence at most once.
 */

public class Permutation {
    
    public static void main(String[] args) {
        // StdOut.println(args.length);
       
        if (args.length < 1) {
            StdOut.println("Please provide the number of items to be printed out");
            return;
        }
        
        int k = Integer.parseInt(args[0]);
        
        RandomizedQueue<String> queue = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()) {
            String item = StdIn.readString();
            queue.enqueue(item);
        }
        
        Iterator<String> i = queue.iterator();
        while (i.hasNext() && k > 0) {
            String s = i.next();
            StdOut.println(s);
            k--;
        }
    }
    
}