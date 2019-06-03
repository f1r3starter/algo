/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF weightedQuickUnionUF;
    private int size;

    public Percolation(int n)
    {
        if (n < 0) {
            throw new java.lang.IllegalArgumentException;
        }

        size = n;

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n);
    }

    public void open(int row, int col)
    {
        weightedQuickUnionUF.connected(toId(row, col), toId(row - 1, col));
    }

    public boolean isOpen(int row, int col)
    {
        return grid[row][col];
    }

    public boolean isFull(int row, int col) {}  // is site (row, col) full?
    public     int numberOfOpenSites() {}      // number of open sites
    public boolean percolates()          {}    // does the system percolate?

    public static void main(String[] args) {}

    private int toId(int row, int col)
    {
        return size * row + col;
    }
}
