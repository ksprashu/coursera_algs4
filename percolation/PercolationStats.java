public class PercolationStats {
    private double[] prob;
    private int T;
        
    public PercolationStats(int N, int T) {
        if (N <= 0) throw new IllegalArgumentException("grid size is invalid");
        if (T <= 0) throw new IllegalArgumentException("iteration size is invalid");
        
        this.prob = new double[T];
        this.T = T;
        
        for (int i = 0; i < T; i++) {
            prob[i] = getProbability(N);
        }
    }
    
    public double mean() {
        return StdStats.mean(prob);
    }
    
    public double stddev() {
        return StdStats.stddev(prob);
    }
    
    public double confidenceLo() {
        return mean() - 1.96 * stddev() / Math.sqrt(T);
    }
    
    public double confidenceHi() {
        return mean() + 1.96 * stddev() / Math.sqrt(T);
    }
    
    private double getProbability(int N) {
        Percolation perc = new Percolation(N); 
        int i, j;
        int count = 0;

        i = StdRandom.uniform(1, N+1);
        j = StdRandom.uniform(1, N+1);

        while (!perc.percolates()) {       
            while (perc.isOpen(i, j)) {
                i = StdRandom.uniform(1, N+1);
                j = StdRandom.uniform(1, N+1);
            }
            perc.open(i, j);
            ++count;            
        }
        
        return count * 1.0 / N / N;
    }
    
    public static void main(String[] args) {
        if (args.length < 2)
            return;
                
        int N = Integer.parseInt(args[0]);
        int T = Integer.parseInt(args[1]);
           
        PercolationStats stat = new PercolationStats(N, T);
        
        StdOut.printf("mean %18s = %s\n", "", stat.mean());
        StdOut.printf("stddev %16s = %s\n", "", stat.stddev());
        StdOut.printf("95%% confidence interval = %s, %s\n", 
                      stat.confidenceLo(), stat.confidenceHi());
    }
}