import java.util.Arrays;
import java.util.ArrayList;

public class Fast {
    
    private static class PointPair implements Comparable<PointPair> {
        private Point p1;
        private Point p2;
        
        public PointPair(Point a, Point b) {
            this.p1 = a;
            this.p2 = b;
        }
        
        public int compareTo(PointPair that) {
            /* YOUR CODE HERE */
            if (this.p1.compareTo(that.p1) == 0 && this.p2.compareTo(that.p2) == 0)
                return 0;
            else
                return -1;
        }        
    }
    
    private static void show(Point[] a) {
        StdOut.println();
                
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
    
        StdOut.println();
    }
    
    private static void printPoints(Point p, Point[] sorted, ArrayList<PointPair> pp, int count, int hi) {
        Arrays.sort(sorted, hi - count + 1, hi);

        PointPair p1 = new PointPair(sorted[hi], sorted[hi - 1]);
        if (exists(pp, p1))
            return;
        else
            pp.add(p1);
                
        StdOut.printf(p.toString());                

        for (int t = hi - count + 1; t <= hi; t++) {
            StdOut.printf(" -> " + sorted[t].toString());
        }
        
        StdOut.println();

        p.drawTo(sorted[hi]);
    }
    
    private static boolean exists(ArrayList<PointPair> pp, PointPair p) {
        for (PointPair pair: pp) {
            if (pair.compareTo(p) == 0)
                return true;
        }
        
        return false;
    }
    
    private static void showSlopes(Point p, Point[] arr) {
        StdOut.println();
        for (int i = 0; i < arr.length; i++) {
            StdOut.print(p.slopeTo(arr[i]) + " ");
        }
        StdOut.println();        
    }

    public static void main(String[] args) {
        // test("input6.txt");
        // test("input8.txt");
        // test("input10.txt");
        test(args[0]);
    }
   
    private static void test(String file) {
        ArrayList<PointPair> pp = new ArrayList<PointPair>();

        Point[] points;
        Point[] sortPoints;          
       
        In in = new In(file);      // input file
       
        int N = in.readInt();
       
        points = new Point[N];
        sortPoints = new Point[N]; 
        pp.clear();    
       
        int n = 0;
       
        double pq, pr, ps;
       
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
       
        while (!in.isEmpty()) {
            int x = in.readInt();
            int y = in.readInt();
           
            points[n] = new Point(x, y);
            points[n].draw();
            n++;
        }
       
        Arrays.sort(points);
        // show(points);

        sortPoints = Arrays.copyOf(points, N);       
       
        // pp.clear();
       
        for (int i = 0; i < N - 3; i++) {
           
            //sortPoints = new Point[N - i - 1];
            //copyPoints(points, i + 1, sortPoints, N);
         
            Arrays.sort(sortPoints, i, N, points[i].SLOPE_ORDER);
           
            //show(points);
            //showSlopes(points[i], sortPoints);
                       
            double slope = points[i].slopeTo(sortPoints[i + 1]);
            int slopeCount = 0;
           
            int j = i + 1;
            while (j < N) {
                if (points[i].slopeTo(sortPoints[j]) == slope)
                    slopeCount++;
                else {
                    if (slopeCount >= 3) {
                        // StdOut.println("here");
                        printPoints(points[i], sortPoints, pp, slopeCount, j - 1);
                    }
                   
                    slope = points[i].slopeTo(sortPoints[j]);
                    slopeCount = 1;
                }
               
                j++;                
            }
           
            if (slopeCount >= 3) {
                // StdOut.println("here yo");
                printPoints(points[i], sortPoints, pp, slopeCount, j - 1);
            }
        }       
    }
}