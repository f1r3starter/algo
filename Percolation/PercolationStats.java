/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {
    private static final double CONFIDENCE = 1.96;
    private final double mean;
    private final double stddev;
    private final double confLo;
    private final double confHi;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) {
            throw new java.lang.IllegalArgumentException();
        }

        double[] results = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(n);
            while (true) {
                int x = StdRandom.uniform(1, n + 1);
                int y = StdRandom.uniform(1, n + 1);
                if (perc.isOpen(x, y)) {
                    continue;
                }

                perc.open(x, y);

                if (perc.percolates()) {
                    break;
                }
            }

            results[i] = (double) perc.numberOfOpenSites() / (n * n);
        }

        mean = StdStats.mean(results);
        stddev = StdStats.stddev(results);
        confLo = mean - CONFIDENCE * stddev() / Math.sqrt(results.length);
        confHi = mean + CONFIDENCE * stddev() / Math.sqrt(results.length);
    }

    public double mean() {
        return mean;
    }

    public double stddev() {
        return stddev;
    }

    public double confidenceLo() {
        return confLo;
    }

    public double confidenceHi() {
        return confHi;
    }

    public static void main(String[] args) {
        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(n, t);
        StdOut.println(String.format("mean                    = %s", stats.mean()));
        StdOut.println(String.format("stddev                  = %s", stats.stddev()));
        StdOut.println(String.format("95%% confidence interval = [%s, %s]", stats.confidenceLo(), stats.confidenceHi()));
    }
}
