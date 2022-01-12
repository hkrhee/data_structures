package hw2;

import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF world;
    private WeightedQuickUnionUF percWorld;
    private WeightedQuickUnionUF[] worlds;
    private boolean[][] opens;
    private int dim;
    private int top;
    private int bottom;
    private int numOpens;

    // create N-by-N grid, with all sites initially blocked
    public Percolation(int N) {
        if (N <= 0) {
            throw new IllegalArgumentException();
        }
        dim = N;
        world = new WeightedQuickUnionUF(N * N + 1);
        percWorld = new WeightedQuickUnionUF(N * N + 2);
        worlds = new WeightedQuickUnionUF[]{world, percWorld};
        top = N * N;
        bottom = N * N + 1;
        opens = new boolean[N][N];
        numOpens = 0;
    }

    // open the site (row, col) if it is not open already
    public void open(int row, int col) {
        validate(row, col);
        int curr = xyto1D(row, col);
        if (!isOpen(row, col)) {
            for (WeightedQuickUnionUF W : worlds) {
                if (row == 0) {
                    W.union(xyto1D(row, col), top);
                }
                if (row - 1 >= 0 && isOpen(row - 1, col)) {
                    int adj = xyto1D(row - 1, col);
                    W.union(curr, adj);
                }
                if (row + 1 < dim && isOpen(row + 1, col)) {
                    int adj = xyto1D(row + 1, col);
                    W.union(curr, adj);
                }
                if (col - 1 >= 0 && isOpen(row, col - 1)) {
                    int adj = xyto1D(row, col - 1);
                    W.union(curr, adj);
                }
                if (col + 1 < dim && isOpen(row, col + 1)) {
                    int adj = xyto1D(row, col + 1);
                    W.union(curr, adj);
                }
            }
            if (row == dim - 1) {
                percWorld.union(xyto1D(row, col), bottom);
            }
            opens[row][col] = true;
            numOpens += 1;
            percolates();
        }
    }

    //helper method (2, 4) --> 14
    private int xyto1D(int r, int c) {
        return r * dim + c;
    }

    private void validate(int r, int c) {
        if (r < 0 || c < 0 || r >= dim || c >= dim) {
            throw new IndexOutOfBoundsException();
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        validate(row, col);
        return opens[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        validate(row, col);
        int curr = xyto1D(row, col);
        return world.connected(curr, top);
    }

    // number of open sites
    public int numberOfOpenSites() {
        return numOpens;
    }

    // does the system percolate?
    public boolean percolates() {
        return percWorld.connected(top, bottom);
    }

    public static void main(String[] args) {
        boolean full;
        boolean isConnected;
        boolean perc;

        Percolation t = new Percolation(10);
        t.open(9, 1);
        full = t.isFull(9, 1);
        t.open(1, 9);
        full = t.isFull(1, 9);
        t.open(5, 7);
        full = t.isFull(5, 7);
        t.open(1, 5);
        full = t.isFull(1, 5);
        t.open(0, 3);
        full = t.isFull(0, 3);
        t.open(7, 3);
        full = t.isFull(7, 3);
        t.open(9, 0);
        full = t.isFull(9, 0);
        t.open(3, 1);
        full = t.isFull(3, 1);
    }
}
