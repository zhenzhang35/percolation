import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;

public class PercolationStats {

    private int n;
    private int trials;
    private double[] thresholds;
    
    public PercolationStats(int N, int T){
        if (N <= 0 || T <= 0){
            throw new java.lang.IllegalArgumentException("n and trials should be greater than 0.");
        }else{
            this.n = N;
            this.trials = T;
            thresholds = new double[T];

            for (int i = 0; i < T; i++){
                Percolation p = new Percolation(N);

                while (!p.percolates()){
                    int row = StdRandom.uniform(1, N + 1);
                    int col = StdRandom.uniform(1, N + 1);
                    p.open(row, col);
                }

                int numOpened = p.numberOfOpenSites();
                thresholds[i] = (double) numOpened / (N * N);
            }
        }
    }

    public double mean(){
        return StdStats.mean(thresholds);
    }

    public double stddev(){
        return StdStats.stddev(thresholds);
    }

    public double confidenceLo(){
        return mean() - (1.96 * stddev()) / Math.sqrt(trials);
    }

    public double confidenceHi(){
        return mean() + (1.96 * stddev()) / Math.sqrt(trials);
    }

    public static void main(String[] args){
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
        PercolationStats ps = new PercolationStats(N, T);

        System.out.println("mean                    = " + ps.mean());
        System.out.println("stddev                  = " + ps.stddev());
        System.out.println("95% confidence interval = [" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
        System.out.println();
    }
}
