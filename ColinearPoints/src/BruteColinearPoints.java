import java.util.Comparator;

/**
 * Brute force. Write a program BruteCollinearPoints.java that examines 4 points at a time and
 * checks whether they all lie on the same line segment, returning all such line segments.
 * To check whether the 4 points p, q, r, and s are collinear, check whether the three slopes
 * between p and q, between p and r, and between p and s are all equal.
 * <p>
 * public class BruteCollinearPoints {
 * public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
 * public           int numberOfSegments()        // the number of line segments
 * public LineSegment[] segments()                // the line segments
 * }
 * The method segments() should include each line segment containing 4 points exactly once.
 * If 4 points appear on a line segment in the order p→q→r→s, then you should include either the
 * line segment p→s or s→p (but not both) and you should not include subsegments such as p→r or q→r.
 * For simplicity, we will not supply any input to BruteCollinearPoints that has 5 or more collinear points.
 * <p>
 * Corner cases. Throw a java.lang.IllegalArgumentException if the argument to the constructor is null,
 * if any point in the array is null, or if the argument to the constructor contains a repeated point.
 * <p>
 * Performance requirement. The order of growth of the running time of your program should be n4 in the worst case
 * and it should use space proportional to n plus the number of line segments returned.
 */
public class BruteColinearPoints {
    private LineSegment[] lineSegments;
    private int numberOfSegments;

    public BruteColinearPoints(Point[] points) {
        // Corner cases
        if (points == null) throw new IllegalArgumentException();
        // Quadratic time checking for null or equal points
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Argument contains a null point");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Argument contains a null point");
            }
        }
        // n^4 brute force algorithm (INEFFICIENT)
        numberOfSegments = 0;
        Node dummyNode = new Node(null);
        Node currentNode = dummyNode;
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int l = k + 1; l < points.length; l++) {
                        if (checkEqualSlopes(points[i], points[j], points[k], points[l])) {
                            numberOfSegments++;
                            currentNode.next = new Node(new LineSegment(points[i], points[l]));
                            currentNode = currentNode.next;
                        }
                    }
                }
            }
        }
        lineSegments = new LineSegment[numberOfSegments];
        for (int i = 0; i < numberOfSegments; i++) {
            lineSegments[i] = dummyNode.next.data;
            dummyNode = dummyNode.next;
        }
    }

    private static boolean checkEqualSlopes(Point a, Point b, Point c, Point d) {
        Comparator<Point> comparator = a.slopeOrder();
        return comparator.compare(b, c) == 0 && comparator.compare(c, d) == 0;
    }

    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        return lineSegments;
    }

    private class Node {
        private LineSegment data;
        private Node next;

        private Node(LineSegment data) {
            this.data = data;
        }
    }

    public static void main(String[] args) {
        Point p1 = new Point(1, 1);
        Point p2 = new Point(2, 2);
        Point p3 = new Point(3, 3);
        Point p4 = new Point(4, 4);
        assert BruteColinearPoints.checkEqualSlopes(p1, p2, p3, p4) == true;
        Point[] points = new Point[]{p1, p2, p3, p4};
        BruteColinearPoints bruteColinearPoints = new BruteColinearPoints(points);
        assert bruteColinearPoints.numberOfSegments == 1;

        Point p1a = new Point(1, 1);
        Point p2a = new Point(2, 2);
        Point p3a = new Point(3, 3);
        Point p4a = new Point(4, 5);
        assert BruteColinearPoints.checkEqualSlopes(p1a, p2a, p3a, p4a) == false;
        Point[] pointsa = new Point[]{p1a, p2a, p3a, p4a};
        BruteColinearPoints bruteColinearPointsa = new BruteColinearPoints(pointsa);
        assert bruteColinearPointsa.numberOfSegments == 0;
    }
}
