import edu.princeton.cs.algs4.StdRandom;

import java.util.Arrays;
import java.util.Comparator;

/**
 * Given a scatter plot of points, find all the collinear points (4 or more) in n^2*logn time
 */

public class FastCollinearPoints {
    private int numberOfSegments;
    private Node lineSegments;

    public FastCollinearPoints(Point[] points) {
        // Verify input
        if (points == null) throw new IllegalArgumentException("Points cannot be null");
        mergeSort(points, (a, b) -> a.compareTo(b));
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i + 1; j < points.length; j++) {
                if (points[i] == null) throw new IllegalArgumentException("No points can be null");
                if (points[i].compareTo(points[j]) == 0)
                    throw new IllegalArgumentException("Array cannot contain duplicate points");
            }
        }
        if (points[points.length - 1] == null) throw new IllegalArgumentException("No points can be null");

        // init variables
        numberOfSegments = 0;
        lineSegments = null;

        // Make an aux array to sort by
        Point[] pointsOrderedBySlope = new Point[points.length];


        // For each point, sort the array by slope order and add LineSegments to linked list.
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                pointsOrderedBySlope[j] = points[j];
            }
            Point thisPoint = points[i];
            mergeSort(pointsOrderedBySlope, thisPoint.slopeOrder());
            checkForLines(thisPoint, pointsOrderedBySlope);
        }
    }

    private void checkForLines(Point point, Point[] pointsOrderedBySlope) {
        int counter = 1;
        Comparator<Point> comparator = point.slopeOrder();
        for (int i = 1; i < pointsOrderedBySlope.length; i++) {
            if (comparator.compare(pointsOrderedBySlope[i], pointsOrderedBySlope[i - 1]) == 0) {
                counter++;
                if (i == pointsOrderedBySlope.length - 1 && counter >= 3) {
                    addLineSegment(pointsOrderedBySlope[i - counter + 1], pointsOrderedBySlope[i]);
                }
            } else if (counter >= 3) {
                addLineSegment(pointsOrderedBySlope[i - counter], pointsOrderedBySlope[i - 1]);
                counter = 1;
            } else {
                counter = 1;
            }
        }
    }

    private void addLineSegment(Point p1, Point p2) {
        Node currentNode = lineSegments;
        boolean foundLine = false;

        // Check each current set of endpoints for duplicates of this new line segment
        while (currentNode != null && !foundLine) {
            // If either of the endpoints are the same
            if (currentNode.p1.compareTo(p1) == 0 || currentNode.p2.compareTo(p2) == 0) {
                // Are one of the endpoints different and do both those points have the same slope to the other endpoint?
                if ((currentNode.p1.compareTo(p1) != 0 && p2.slopeOrder().compare(currentNode.p1, p1) == 0)
                        || (currentNode.p2.compareTo(p2) != 0 && p1.slopeOrder().compare(currentNode.p2, p2) == 0)) {
                    // Then Same LINE! Must fix endpoints
                    currentNode.fixPoints(p1, p2);
                    foundLine = true;
                } else {
                    // Both end points the same, SAME LINE! (no need to fix)
                    foundLine = true;
                }
            }
            currentNode = currentNode.next;
        }
        if (!foundLine) {
            numberOfSegments++;
            Node oldHead = lineSegments;
            lineSegments = new Node(p1, p2);
            lineSegments.next = oldHead;
        }
    }

    private class Node {
        private Node next;
        private Point p1;
        private Point p2;

        private Node(Point p1, Point p2) {
            this.p1 = p1;
            this.p2 = p2;
        }

        private void fixPoints(Point newP1, Point newP2) {
            if (newP1.compareTo(this.p1) < 0) this.p1 = newP1;
            if (newP2.compareTo(this.p2) > 0) this.p2 = newP2;
        }
    }


    public int numberOfSegments() {
        return numberOfSegments;
    }

    public LineSegment[] segments() {
        LineSegment[] segments = new LineSegment[numberOfSegments];
        Node currentNode = lineSegments;
        for (int i = 0; i < numberOfSegments; i++) {
            segments[i] = new LineSegment(currentNode.p1, currentNode.p2);
            currentNode = currentNode.next;
        }
        return segments;
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
