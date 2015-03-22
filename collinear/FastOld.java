import java.util.Arrays;
import java.util.ArrayList;

public class FastOld {
    
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
    
    private static ArrayList<PointPair> pp = new ArrayList<PointPair>();
    
    private static void show(Point[] a) {
        StdOut.println();
                
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i] + " ");
        }
    
        StdOut.println();
    }
    
    private static void printPoints(Point p, Point[] arr, int count, int hi) {
        // StdOut.println("sorted array in print");
        // show(arr);

        Point[] temp = new Point[count];
        
        for (int t = hi - count + 1, i = 0; t <= hi; t++, i++) {
            temp[i] = arr[t];
        }

        // StdOut.println("temp array in print");
        // show(temp);
        
        Arrays.sort(temp);
        
        PointPair p1 = new PointPair(temp[temp.length - 1], temp[temp.length - 2]);
        if (exists(p1))
            return;
        else
            pp.add(p1);
                
        StdOut.printf(p.toString());                
        for (int i = 0; i < temp.length; i++) {
            StdOut.printf(" -> " + temp[i].toString());
        }        
        StdOut.println();

        p.drawTo(temp[temp.length - 1]);        
    }
    
    private static boolean exists(PointPair p) {
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
    
    private static void copyPoints(Point[] src, int lo, Point[] tgt, int len) {
        for (int i = lo, j = 0; i < len; i++, j++) {
            tgt[j] = src[i];
        }
    }
    
    public static void main(String[] args) {
        test(args);
   }
   
   private static void test(String[] args) {
       In in = new In(args[0]);      // input file
       Point[] points, sortPoints;
       
       int N = in.readInt();
       points = new Point[N];
       
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
       
       pp.clear();
       
       for (int i = 0; i < N - 1; i++) {
           
           sortPoints = new Point[N - i - 1];
           copyPoints(points, i + 1, sortPoints, N);
           
           Arrays.sort(sortPoints, points[i].SLOPE_ORDER);
           
           // show(sortPoints);
           // showSlopes(points[i], sortPoints);
                       
           double slope = points[i].slopeTo(sortPoints[0]);
           int slopeCount = 0;
           
           int j = 0;
           while (j < sortPoints.length) {
               if (points[i].slopeTo(sortPoints[j]) == slope)
                   slopeCount++;
               else {
                   if (slopeCount >= 3) {
                       printPoints(points[i], sortPoints, slopeCount, j - 1);
                   }
                   
                   slope = points[i].slopeTo(sortPoints[j]);
                   slopeCount = 1;
               }
               
               j++;                
           }
           
           if (slopeCount >= 3) {
               printPoints(points[i], sortPoints, slopeCount, j - 1);
           }
       }       
   }
}