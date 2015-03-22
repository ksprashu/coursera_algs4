public class Percolation {

    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF shadowGrid;
    
    private int N;
    private int elements;
    private boolean[] gridOpenStatus;
    
    public Percolation(int N) {
        if (N <= 0) throw new IllegalArgumentException("grid size is invalid");
            
        this.N = N;
        this.elements = N*N;
        
        grid = new WeightedQuickUnionUF(this.elements + 2);
        shadowGrid = new WeightedQuickUnionUF(this.elements + 1);

        gridOpenStatus = new boolean[this.elements];
    }
    
    public void open(int i, int j) {
        if (i <= 0 || i > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        if (j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("col index j out of bounds");
        
        int currIndex = getFlatIndex(i, j);
        int sideIndex;
        
        gridOpenStatus[currIndex] = true;
        
        // connect to adjoining open sites
        if (isValid(i-1, j)) {
            if (isOpen(i-1, j)) {
                sideIndex = getFlatIndex(i-1, j);
                grid.union(currIndex, sideIndex);
                shadowGrid.union(currIndex, sideIndex);
            }
        }
        
        if (isValid(i+1, j)) {
            if (isOpen(i+1, j)) {
                sideIndex = getFlatIndex(i+1, j);
                grid.union(currIndex, sideIndex);
                shadowGrid.union(currIndex, sideIndex);
            }
        }
        
        if (isValid(i, j-1)) {
            if (isOpen(i, j-1)) {
                sideIndex = getFlatIndex(i, j-1);
                grid.union(currIndex, sideIndex);
                shadowGrid.union(currIndex, sideIndex);
            }
        }
        
        if (isValid(i, j+1)) {
            if (isOpen(i, j+1)) {
                sideIndex = getFlatIndex(i, j+1);
                grid.union(currIndex, sideIndex);
                shadowGrid.union(currIndex, sideIndex);
            }
        }
        
        // connect to virtual top site
        if (i == 1) {
            grid.union(currIndex, topIndex());
            shadowGrid.union(currIndex, topIndex());
        }
        
        // connect to virtual bottom site
        if (i == N) {
            grid.union(currIndex, bottomIndex());
        }
    }
    
    public boolean isOpen(int i, int j) {
        if (i <= 0 || i > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        if (j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("col index j out of bounds");
            
        return gridOpenStatus[getFlatIndex(i, j)];
    }
    
    public boolean isFull(int i, int j) {
        if (i <= 0 || i > N) 
            throw new IndexOutOfBoundsException("row index i out of bounds");
        
        if (j <= 0 || j > N) 
            throw new IndexOutOfBoundsException("col index j out of bounds");

        return shadowGrid.connected(getFlatIndex(i, j), topIndex());
    }
    
    public boolean percolates() {
        return grid.connected(topIndex(), bottomIndex());
    }
    
    private boolean isValid(int i, int j) {
        if (i <= 0 || i > N) 
            return false;
        
        if (j <= 0 || j > N) 
            return false;
        
        return true;
    }
    
    private int getFlatIndex(int i, int j) {
        return (i-1) * N + (j-1);
    }
    
    private int topIndex() {
        return elements;
    }
    
    private int bottomIndex() {
        return elements + 1;
    }
    
    public static void main(String[] args) {
        tdd();
        
        //monteCarlo(5);
        //monteCarlo(10);
        //monteCarlo(20);
        //monteCarlo(100);
        //monteCarlo(200);
        //monteCarlo(500);
        //monteCarlo(2000);
    }
    
    private static void tdd() {
        Percolation perc = new Percolation(2);
        
        StdOut.println("1,1 is open? " + perc.isOpen(1, 1));
        StdOut.println("2,2 is open? " + perc.isOpen(2, 2));
        StdOut.println("Percolates? " + perc.percolates());
        
        StdOut.println("Opening 1,1");
        perc.open(1, 1);
        StdOut.println("1,1 is open? " + perc.isOpen(1, 1));
        StdOut.println("Percolates? " + perc.percolates());
        
        StdOut.println("Opening 2,1");
        perc.open(2, 1);
        StdOut.println("2,1 is open? " + perc.isOpen(2, 1));
        
        StdOut.println("1,1 is full? " + perc.isFull(1, 1));
        StdOut.println("2,1 is full? " + perc.isFull(2, 1));
        StdOut.println("2,2 is full? " + perc.isFull(2, 2));
        
        StdOut.println("Percolates? " + perc.percolates());
    
        perc = new Percolation(3);
        
        perc.open(1, 1);
        perc.open(2, 1);
        perc.open(3, 1);
        perc.open(3, 3);
        
        StdOut.println("3, 3 is full? " + perc.isFull(3, 3));
        StdOut.println("Percolates? " + perc.percolates());
    }
    
    private static void monteCarlo(int N) {
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
               
        StdOut.println("open sites = " + count);
        StdOut.println("probability = " + count * 1.0 / N / N);
    }
}