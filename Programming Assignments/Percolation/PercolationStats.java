/*******************************************************************************
 * Course:          Algorithms, Part I by Princeton University
 * Instructor:      Bob Sedgewick, Kevin Wayne
 *
 * Assignment:      Week 1, Percolation
 *
 * Author:          Rajat Dipta Biswas
 * Last modified:   23/7/2020
 ******************************************************************************/

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private static final double CONFIDENCE_95 = 1.96;
    private final double[] percolationThreshold;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int n, int trials) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is less than or equal to 0");
        }
        if (trials <= 0) {
            throw new IllegalArgumentException("trials is less than or equal to 0");
        }

        percolationThreshold = new double[trials];

        for (int trial = 0; trial < trials; trial++) {
            Percolation percolationTrial = new Percolation(n);

            while (!percolationTrial.percolates()) {
                percolationTrial.open(StdRandom.uniform(1, n + 1),
                                      StdRandom.uniform(1, n + 1));
            }

            percolationThreshold[trial] = (double) percolationTrial.numberOfOpenSites()
                    / (n * n);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(percolationThreshold);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return StdStats.stddev(percolationThreshold);
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return mean() - (CONFIDENCE_95 * stddev() / Math.sqrt(percolationThreshold.length));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return mean() + (CONFIDENCE_95 * stddev() / Math.sqrt(percolationThreshold.length));
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);

        StdOut.println("mean                    = " + stats.mean());
        StdOut.println("stddev                  = " + stats.stddev());
        StdOut.println("95% confidence interval = ["
                               + stats.confidenceLo() + ", "
                               + stats.confidenceHi() + "]");
    }

}
