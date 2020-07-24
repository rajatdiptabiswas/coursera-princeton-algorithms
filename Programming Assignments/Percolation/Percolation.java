/*******************************************************************************
 * Course:          Algorithms, Part I by Princeton University
 * Instructor:      Bob Sedgewick, Kevin Wayne
 *
 * Assignment:      Week 1, Percolation
 *
 * Author:          Rajat Dipta Biswas
 * Last modified:   23/7/2020
 ******************************************************************************/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

    private final int n;
    // n-by-n grid
    // open     true
    // blocked  false
    private boolean[][] grid;
    private int openSites = 0;
    private final WeightedQuickUnionUF percolateUF;
    private final WeightedQuickUnionUF backwashUF;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n is less than or equal to 0");
        }

        this.n = n;

        // n^2 grid sites
        // (row,col) ranges from (1,1) to (n,n)
        grid = new boolean[n][n];

        // id array index ranges from 0 to n^2 + 1
        // 1 virtual top site, 1 virtual bottom site
        // array[0]         virtual top site
        // array[1 : n^2]   grid sites
        // array[n^2 + 1]   virtual bottom site
        percolateUF = new WeightedQuickUnionUF((n * n) + 2);

        // id array index ranges from 0 to n^2
        // 1 virtual top site only
        backwashUF = new WeightedQuickUnionUF((n * n) + 1);

        for (int row = 1; row <= n; row++) {
            for (int col = 1; col <= n; col++) {
                grid[row - 1][col - 1] = false;
            }
        }
    }

    // returns 2D grid to 1D array indexing
    private int gridTo1DArrayIndex(int row, int col) {
        return n * (row - 1) + col;
    }

    // is the (row, col) in the grid boundaries?
    private boolean isSafe(int row, int col) {
        return 1 <= row && row <= n && 1 <= col && col <= n;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isSafe(row, col)) {
            throw new IllegalArgumentException("(row, col) not in range");
        }

        if (!isOpen(row, col)) {
            grid[row - 1][col - 1] = true;
            openSites += 1;

            // connect grid site to virtual sites if necessary
            if (row == 1) {
                // connect to virtual top site
                percolateUF.union(0, gridTo1DArrayIndex(row, col));
                backwashUF.union(0, gridTo1DArrayIndex(row, col));
            }
            if (row == n) {
                // connect to virtual bottom site
                percolateUF.union((n * n) + 1, gridTo1DArrayIndex(row, col));
            }

            // connect grid site to neighbouring sites
            // left site
            if (isSafe(row, col - 1) && isOpen(row, col - 1)) {
                percolateUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row, col - 1));
                backwashUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row, col - 1));
            }
            // top site
            if (isSafe(row - 1, col) && isOpen(row - 1, col)) {
                percolateUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row - 1, col));
                backwashUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row - 1, col));
            }
            // right site
            if (isSafe(row, col + 1) && isOpen(row, col + 1)) {
                percolateUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row, col + 1));
                backwashUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row, col + 1));
            }
            // bottom site
            if (isSafe(row + 1, col) && isOpen(row + 1, col)) {
                percolateUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row + 1, col));
                backwashUF.union(gridTo1DArrayIndex(row, col), gridTo1DArrayIndex(row + 1, col));
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isSafe(row, col)) {
            throw new IllegalArgumentException("(row, col) not in range");
        }
        return grid[row - 1][col - 1];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isSafe(row, col)) {
            throw new IllegalArgumentException("(row, col) not in range");
        }
        return backwashUF.find(0) == backwashUF.find(gridTo1DArrayIndex(row, col));
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return openSites;
    }

    // does the system percolate?
    public boolean percolates() {
        return percolateUF.find(0) == percolateUF.find((n * n) + 1);
    }

    // test client (optional)
    public static void main(String[] args) {
        // no additional tests needed
    }
}
