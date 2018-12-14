import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x, y;

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        if (this.compareTo(that) == 0) return Double.NEGATIVE_INFINITY;
        if (this.x == that.x) return Double.POSITIVE_INFINITY;
        return ((double) (that.y - this.y) / (that.x - this.x));
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return 1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return 1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return (pointOne, pointTwo) -> {
            double tolerance = .0001;
            if (this.slopeTo(pointOne) - this.slopeTo(pointTwo) < -1 * tolerance) return -1;
            if (this.slopeTo(pointOne) - this.slopeTo(pointTwo) > tolerance) return 1;
            return 0;
        };
    }


    /**
     * Returns a string representation of this point.
     */
    public String toString() {
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        Point p1 = new Point(0, 0);
        Point p1Clone = new Point(0, 0);
        Point flatFromP1 = new Point(1, 0);
        Point horizontalFromP1 = new Point(0, 10);

        assert p1.slopeTo(p1Clone) == Double.NEGATIVE_INFINITY;
        assert p1.slopeTo(flatFromP1) == 0;
        assert p1.slopeTo(horizontalFromP1) == Double.POSITIVE_INFINITY;

        Point p2 = new Point(3, 3);
        assert p1.slopeTo(p2) == 1;
        Point p3 = new Point(3, 5);
        Double tolerance = .1;
        assert Math.abs(p1.slopeTo(p3) - 1.6) < tolerance;
        Comparator<Point> comparator = p1.slopeOrder();
        assert comparator.compare(p2, p3) == -1;
        Point p4 = new Point(3, 2);
        assert comparator.compare(p2, p4) == 1;
        assert comparator.compare(p2, p2) == 0;

    }
}