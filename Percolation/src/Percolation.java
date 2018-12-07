import java.util.Arrays;

/**
 * public class Percolation {
 *    public Percolation(int n)                // create n-by-n open, with all sites blocked
 *    public    void open(int row, int col)    // open site (row, col) if it is not open already
 *    public boolean isOpen(int row, int col)  // is site (row, col) open?
 *    public boolean isFull(int row, int col)  // is site (row, col) full?
 *    public     int numberOfOpenSites()       // number of open sites
 *    public boolean percolates()              // does the system percolate?
 *
 *    public static void main(String[] args)   // test client (optional)
 * }
 * Corner cases.  By convention, the row and column indices are integers between 1 and n, where (1, 1)
 * is the upper-left site: Throw a java.lang.IllegalArgumentException if any argument to open(), isOpen(),
 * or isFull() is outside its prescribed range. The constructor should throw a java.lang.IllegalArgumentException if n ≤ 0.
 *
 * Performance requirements.  The constructor should take time proportional to n2;
 * all methods should take constant time plus a constant number of calls to the union–find methods
 * union(), find(), connected(), and count().
 */
public class Percolation {

    private boolean[][] open;
    private int n;
    // Make array go from 0...n^2+1 for n2+2 elements to account for fake nodes.
    private int[] parent;
    private int numberOfOpenSites;
    private int topIndex;
    private int bottomIndex;

    public Percolation(int n){
        if(n<=0) throw new IllegalArgumentException("n must be greater than 0");
        this.n = n;
        this.open = new boolean[n][n];
        this.parent = new int[(n*n)+2];
        Arrays.fill(parent, -1);
        this.numberOfOpenSites=0;
        // Init parents for the first and last rows
        this.topIndex = 0;
        this.bottomIndex = (n*n)+1;
        for(int i =0; i<=n; i++){
            parent[i] = topIndex;
            parent[bottomIndex-i]=bottomIndex;
        }
    }

    public int numberOfOpenSites(){
        return numberOfOpenSites;
    }

    /**
     * Opens the site
     * @param row
     * @param col
     */
    public void open(int row, int col){
        try {
            this.rangeCheck(row,col);
            if(isOpen(row,col)) return;
            numberOfOpenSites++;
            open[row-1][col-1] = true;
            this.unionAllNeighbors(row, col);
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }

    public boolean isOpen(int row, int col){
        try {
            this.rangeCheck(row,col);
            return open[row-1][col-1];
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean isFull(int row, int col){
        try {
            this.rangeCheck(row,col);
            return isFull(getArrayPosition(row,col));
        } catch (IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
        return false;
    }

    public boolean percolates(){
        return isFull(bottomIndex);
    }

    private void union(int row1, int col1, int row2, int col2){
        // Find the root of each, attach the bigger to the smaller.
        // Add path compression
        if(!isOpen(row2, col2)) return;
        int root1 = root(getArrayPosition(row1, col1));
        int root2 = root(getArrayPosition(row2, col2));
        int smallRoot = Math.min(root1, root2);
        int largeRoot = smallRoot==root1 ? root2 : root1;
        parent[largeRoot] = smallRoot;
    }


    private int root(int arrayIndex){
        while(arrayIndex!=parent[arrayIndex]){
            parent[arrayIndex] = parent[parent[arrayIndex]];
            arrayIndex = parent[arrayIndex];
        }
        return arrayIndex;
    }

    /**
     * Helpers
     */
    private void rangeCheck(int row, int column) throws  IllegalArgumentException{
        if (!(row>0 && row<=n) || !(column>0 && column<=n)) throw new IllegalArgumentException("Bad Coordinates");
    }
    private void init(int row, int col) {
        int arrayPosition = this.getArrayPosition(row,col);
        int currentParent = parent[arrayPosition];
        parent[arrayPosition] = currentParent==-1 ? arrayPosition : currentParent;
    }
    private int getArrayPosition(int row, int col){
        return ((row-1)*n) + col;
    }
    private void unionAllNeighbors(int row, int col) {
        init(row,col);
        if(row>1){
            union(row,col,row-1,col);
        }
        if(col>1){
            union(row,col,row,col-1);
        }
        if(row<n){
            union(row,col,row+1,col);
        }
        if(col<n){
            union(row,col,row,col+1);
        }
    }
    private boolean isFull(int index){
        return root(index)==topIndex;
    }
}
