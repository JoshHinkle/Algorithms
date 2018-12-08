import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private final double[] trialMeans;
    private final int trials;

    public PercolationStats(int n, int trials) {
        if (n <= 0 || trials <= 0) throw new IllegalArgumentException("Must be more than 0 elements and trials");
        this.trials = trials;
        trialMeans = new double[trials];
        for (int i = 0; i < trials; i++) {
            Percolation p = new Percolation(n);
            boolean percolating = false;
            while (!percolating) {
                p.open(StdRandom.uniform(1, n + 1), StdRandom.uniform(1, n + 1));
                percolating = p.percolates();
            }
            trialMeans[i] = p.numberOfOpenSites() / (double) (n * n);
        }

    }

    public double mean() {
        return StdStats.mean(trialMeans);
    }

    public double stddev() {
        return StdStats.stddev(trialMeans);

    }

    public double confidenceLo() {
        return mean() - ((1.96) * stddev()) / Math.sqrt((double) trials);
    }

    public double confidenceHi() {
        return mean() + ((1.96) * stddev()) / Math.sqrt((double) trials);
    }

    public static void main(String[] args) {
        int n, trials;
        try {
            n = Integer.parseInt(args[0]);
            trials = Integer.parseInt(args[1]);
            PercolationStats ps = new PercolationStats(n, trials);
            System.out.println("Mean: " + ps.mean());
            System.out.println("Stddev: " + ps.stddev());
            System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        } catch (NumberFormatException e) {
            System.out.println(e.getMessage());
        }

    }
}