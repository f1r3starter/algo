/* *****************************************************************************
 *  Name: Andrey
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private final WeightedQuickUnionUF weightedQuickUnionUF;
    private final int size;
    private boolean[] sites;
    private int openSites;

    public Percolation(int n) {
        if (n <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        size = n;
        openSites = 0;

        sites = new boolean[n * n + 2];
        sites[0] = true;
        for (int i = 1; i <= n * n + 1; i++) {
            sites[i] = false;
        }

        weightedQuickUnionUF = new WeightedQuickUnionUF(n * n + 2);
    }

    public void open(int row, int col) {
        int id = xyTo1D(row, col);

        if (!sites[id]) {
            sites[id] = true;
            ++openSites;

            if (row - 1 == 0) {
                weightedQuickUnionUF.union(id, 0);
            }
            else if (isOpen(row - 1, col)) {
                weightedQuickUnionUF.union(id, xyTo1D(row - 1, col));
            }

            if (col - 1 > 0 && isOpen(row, col - 1)) {
                weightedQuickUnionUF.union(id, xyTo1D(row, col - 1));
            }

            if (col + 1 <= size && isOpen(row, col + 1)) {
                weightedQuickUnionUF.union(id, xyTo1D(row, col + 1));
            }

            if (row == size) {
                if (isFull(row, col)) {
                    weightedQuickUnionUF.union(id, size * size + 1);
                }
            }
            else if (isOpen(row + 1, col)) {
                weightedQuickUnionUF.union(id, xyTo1D(row + 1, col));
            }
        }
    }

    public boolean isOpen(int row, int col) {
        return sites[xyTo1D(row, col)];
    }

    public boolean isFull(int row, int col) {
        return weightedQuickUnionUF.connected(0, xyTo1D(row, col));
    }

    public int numberOfOpenSites() {
        return openSites;
    }

    public boolean percolates() {
        return weightedQuickUnionUF.connected(0, size * size + 1);
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(3);
        StdOut.println(perc.isFull(3, 1));
        perc.open(1, 3);

        StdOut.println(perc.isFull(3, 1));
        perc.open(2, 3);

        StdOut.println(perc.isFull(3, 1));
        perc.open(3, 3);

        StdOut.println(perc.isFull(3, 1));
        perc.open(3, 1);


        StdOut.println(perc.isFull(3, 1));
    }

    private int xyTo1D(int x, int y) {
        isInBounds(x);
        isInBounds(y);
        return size * (x - 1) + y;
    }

    private void isInBounds(int i) {
        if (i <= 0 || i > size)
            throw new IllegalArgumentException(String.format("row index %s out of bounds", i));
    }
}
