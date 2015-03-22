import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        In in = new In(args[0]);      // input file
        Point[] points;
        
        int N = in.readInt();
        points = new Point[N];
        
        int n = 0;
        
        double pq, pr, ps;
        
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);        
        
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
            
            points[n++] = new Point(x, y);
            
            points[n - 1].draw();
        }
        
        Arrays.sort(points);
        
        for (int i = 0; i < N - 3; i++) {
            
            for (int j = i + 1; j < N - 2; j++) {
                for (int k = j + 1; k < N - 1; k++) {
                    for (int l = k + 1; l < N; l++) {
                        pq = points[i].slopeTo(points[j]);
                        pr = points[i].slopeTo(points[k]);
                        ps = points[i].slopeTo(points[l]);
                        if (pq == pr && pr == ps) {
                            StdOut.printf(points[i].toString() + " -> ");
                            StdOut.printf(points[j].toString() + " -> ");
                            StdOut.printf(points[k].toString() + " -> ");
                            StdOut.printf(points[l].toString() + "\n");  
                            
                            points[i].drawTo(points[l]);
                        }
                    }
                }
            }
        }
    }
}
