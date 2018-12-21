import java.util.Comparator;

/**
 * Brute force algorithm to find collinear points on a scatter plot (O(N^4))
 */
public class BruteCollinearPoints {
    private final LineSegment[] lineSegments;
    private int numberOfSegments;

    public BruteCollinearPoints(Point[] points) {
        // Reject null or duplicate points
        if (points == null) throw new IllegalArgumentException();
        for (int i = 0; i < points.length - 1; i++) {
            if (points[i] == null) throw new IllegalArgumentException("Argument contains a null point");
            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Argument contains duplicate points");
            }
        }
        numberOfSegments = -1;
        Node dummyNode = new Node(null);
        Node currentNode = dummyNode;
        mergeSort(points, (a, b) -> a.compareTo(b));
        for (int i = 0; i < points.length - 3; i++) {
            for (int j = i + 1; j < points.length - 2; j++) {
                for (int k = j + 1; k < points.length - 1; k++) {
                    for (int m = k + 1; m < points.length; m++) {
                        if (checkEqualSlopes(points[i], points[j], points[k], points[m])) {
                            currentNode.next = new Node(new LineSegment(points[i], points[m]));
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
        return lineSegments.clone();
    }

    private class Node {
        private final LineSegment data;
        private Node next;

        private Node(LineSegment data) {
            this.data = data;
            numberOfSegments++;
        }
    }

    private void mergeSort(Point[] data, Comparator<Point> comparator) {
        if (data.length < 2) return;
        Point[] aux = new Point[data.length];
        mergeSort(data, aux, 0, data.length, comparator);

    }

    private void mergeSort(Point[] data, Point[] aux, int lo, int hi, Comparator<Point> comparator) {
        if (lo >= hi - 1) return;
        int mid = lo + (int) Math.ceil((hi - lo) / 2.0);
        mergeSort(data, aux, lo, mid, comparator);
        mergeSort(data, aux, mid, hi, comparator);
        merge(data, aux, lo, mid, hi, comparator);
    }

    private void merge(Point[] data, Point[] aux, int lo, int mid, int hi, Comparator<Point> comparator) {
        for (int i = lo; i < hi; i++) {
            aux[i] = data[i];
        }
        int i = lo, j = mid;
        for (int k = lo; k < hi; k++) {
            if (i >= mid) data[k] = aux[j++];
            else if (j >= hi) data[k] = aux[i++];
            else if (comparator.compare(aux[i], aux[j]) <= 0) data[k] = aux[i++];
            else data[k] = aux[j++];
        }
    }

}
