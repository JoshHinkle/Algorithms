/**
 * Simulate a percolation model, opening cells and checking when the
 * 'bottom' is in the same connected component as the 'top'
 */
public class Percolation {

    private final int n;
    private final int[] parent;
    private final boolean[] open;
    private int numberOfOpenSites;
    private final int topIndex;
    private final int bottomIndex;

    public Percolation(int n) {
        if (n <= 0) throw new IllegalArgumentException("n must be greater than 0");
        this.n = n;
        this.parent = new int[(n * n) + 2];
        this.open = new boolean[(n * n) + 2];
        for (int i = 0; i < parent.length; i++) {
            parent[i] = -1;
        }
        this.numberOfOpenSites = 0;
        this.topIndex = 0;
        this.bottomIndex = (n * n) + 1;
        for (int i = 0; i <= n; i++) {
            parent[i] = topIndex;
            parent[bottomIndex - i] = bottomIndex;
        }
    }

    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    public void open(int row, int col) {
        this.rangeCheck(row, col);
        if (isOpen(row, col)) return;
        numberOfOpenSites++;
        this.init(row, col);
        this.unionAllNeighbors(row, col);
    }

    public boolean isOpen(int row, int col) {
        this.rangeCheck(row, col);
        return open[getArrayPosition(row, col)];
    }

    public boolean isFull(int row, int col) {
        this.rangeCheck(row, col);
        if (!isOpen(row, col)) return false;
        return isFull(getArrayPosition(row, col));

    }

    public boolean percolates() {
        return isFull(bottomIndex);
    }

    private void union(int row1, int col1, int row2, int col2) {
        if (!isOpen(row2, col2)) return;
        int root1 = root(getArrayPosition(row1, col1));
        int root2 = root(getArrayPosition(row2, col2));
        int smallRoot = Math.min(root1, root2);
        int largeRoot = smallRoot == root1 ? root2 : root1;
        parent[largeRoot] = smallRoot;
    }


    private int root(int arrayIndex) {
        while (arrayIndex != parent[arrayIndex]) {
            parent[arrayIndex] = parent[parent[arrayIndex]];
            arrayIndex = parent[arrayIndex];
        }
        return arrayIndex;
    }

    // Helpers
    private void rangeCheck(int row, int column) {
        if (!(row > 0 && row <= n) || !(column > 0 && column <= n))
            throw new IllegalArgumentException("Bad Coordinates");
    }

    private void init(int row, int col) {
        int arrayPosition = this.getArrayPosition(row, col);
        open[arrayPosition] = true;
        int currentParent = parent[arrayPosition];
        parent[arrayPosition] = currentParent == -1 ? arrayPosition : currentParent;
    }

    private int getArrayPosition(int row, int col) {
        return ((row - 1) * n) + col;
    }

    private void unionAllNeighbors(int row, int col) {
        if (row > 1) {
            union(row, col, row - 1, col);
        }
        if (col > 1) {
            union(row, col, row, col - 1);
        }
        if (row < n) {
            union(row, col, row + 1, col);
        }
        if (col < n) {
            union(row, col, row, col + 1);
        }
    }

    private boolean isFull(int index) {
        return root(index) == topIndex;
    }
}
