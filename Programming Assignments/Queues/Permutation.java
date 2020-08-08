import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

/*******************************************************************************
 * Course:          Algorithms, Part I by Princeton University
 * Instructor:      Bob Sedgewick, Kevin Wayne
 *
 * Assignment:      Week 2, Queues
 *
 * Author:          Rajat Dipta Biswas
 * Last modified:   8/8/2020
 ******************************************************************************/

public class Permutation {

    public static void main(String[] args) {
        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomQueue = new RandomizedQueue<>();

        while (!StdIn.isEmpty()) {
            randomQueue.enqueue(StdIn.readString());
        }

        for (int x = 0; x < k; x++) {
            StdOut.println(randomQueue.dequeue());
        }

    }

}
