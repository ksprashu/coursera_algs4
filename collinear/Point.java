import java.util.Comparator;
import java.util.Arrays;

public class Point implements Comparable<Point> {

    // compare points by slope
    public final Comparator<Point> SLOPE_ORDER = new BySlope();

    private final int x;                              // x coordinate
    private final int y;                              // y coordinate

    // create the point (x, y)
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    private class BySlope implements Comparator<Point> {
        public int compare(Point a, Point b) {
            if (a == null || b == null) throw new java.lang.NullPointerException("null object compared");
            
            if (slopeTo(a) < slopeTo(b)) return -1;
            else if (slopeTo(a) > slopeTo(b)) return 1;
            else return a.compareTo(b);
        }
    }

    // plot this point to standard drawing
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    // draw line between this point and that point to standard drawing
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    // slope between this point and that point
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new java.lang.NullPointerException("null object for slope");        
        
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if ((that.x - this.x) == 0) return Double.POSITIVE_INFINITY;
        if ((that.y - this.y) == 0) return 0.0;
        
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if (that == null) throw new java.lang.NullPointerException("null object compared");
        
        if (this.y < that.y) return -1;
        else if (this.y > that.y) return 1;
        else if (this.x < that.x) return -1;
        else if (this.x > that.x) return 1;
        else return 0;
    }

    // return string representation of this point
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    // unit test
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        Point p1, p2;

        p1 = new Point(10, 10);
        p2 = new Point(10, 10);
        
        StdOut.println("Is " + p1.toString() + " = " + p2.toString() + "? " + p1.compareTo(p2));
        StdOut.println(p1.toString() + " slope " + p2.toString() + " => " + p1.slopeTo(p2));

        p1 = new Point(10, 10);
        p2 = new Point(20, 10);
        StdOut.println(p1.toString() + " slope " + p2.toString() + " => " + p1.slopeTo(p2));
        
        p1 = new Point(10, 10);
        p2 = new Point(10, 20);
        StdOut.println(p1.toString() + " slope " + p2.toString() + " => " + p1.slopeTo(p2));

        p1 = new Point(10, 10);
        p2 = new Point(20, 20);
        StdOut.println(p1.toString() + " slope " + p2.toString() + " => " + p1.slopeTo(p2));
        
        Point[] pa = new Point[10];
        
        for (int i = 0; i < 10; i++) {
            int r = StdRandom.uniform(100);
            pa[i] = new Point(r * 10, r * 10);
        }
        
        show(pa);
        
        //Arrays.sort(pa);
        //show(pa);
        
        Arrays.sort(pa, pa[5].SLOPE_ORDER);
        show(pa);
    }
    
    private static void show(Point[] a) {
        StdOut.println();
        for (int i = 0; i < a.length; i++) {
            StdOut.print(a[i].toString() + " ");
        }
        StdOut.println();
    }
}