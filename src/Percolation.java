import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation{

    private int n;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF full;
    private int top;
    private int bottom;
    private boolean[] openSites;

    public Percolation(int N){
        if (N <= 0){
            throw new java.lang.IllegalArgumentException("N must be greater than 0.");
        }else{
            this.n = N;
            grid = new WeightedQuickUnionUF(N * N + 2);
            full = new WeightedQuickUnionUF(N * N + 1);
            top = getIndex(N, N) + 1;
            bottom = getIndex(N, N) + 2;
            openSites = new boolean [N * N];
        }
    }

    private boolean isValid(int row, int col){
        return (row >= 1 && row <= n && col >= 1 && col <= n);
    }

    private int getIndex(int row, int col){
        if (!isValid(row, col)){
            throw new java.lang.IllegalArgumentException("indices must be between 1 and n.");
        }else{
            int idx = (row - 1) * n + col - 1;
            return idx;
        }
    }

    public void open(int row, int col){        
        if (!isValid(row, col)){
            throw new java.lang.IllegalArgumentException("indices must be between 1 and n.");
        }else{
            int idx = getIndex(row, col);
            openSites[idx] = true;
        
            if (row == 1){
                grid.union(top, idx);
                full.union(top, idx);
            }

            if (row == n){
                grid.union(bottom, idx);
            }

            if (isValid(row - 1, col) && isOpen(row - 1, col)){
                grid.union(getIndex(row - 1, col), idx);
                full.union(getIndex(row - 1, col), idx);
            }

            if (isValid(row + 1, col) && isOpen(row + 1, col)){
                grid.union(getIndex(row + 1, col), idx);
                full.union(getIndex(row + 1, col), idx);
            }

            if (isValid(row, col - 1) && isOpen(row, col - 1)){
                grid.union(getIndex(row, col - 1), idx);
                full.union(getIndex(row, col - 1), idx);
            }

            if (isValid(row, col + 1) && isOpen(row, col + 1)){
                grid.union(getIndex(row, col + 1), idx);
                full.union(getIndex(row, col + 1), idx);
            }
        }
    }

    public boolean isOpen(int row, int col){
        if (!isValid(row, col)){
            throw new java.lang.IllegalArgumentException("indices must be between 1 and n.");
        }else{
            return openSites[getIndex(row, col)];
        }
    }

    public boolean isFull(int row, int col){        
        if (!isValid(row, col)){
            throw new java.lang.IllegalArgumentException("indices must be between 1 and n.");
        }else{
            return full.connected(getIndex(row, col), top);
        }
    }

    public int numberOfOpenSites(){
        int opened = 0;
        for (int row = 1; row <= n; row++){
            for (int col = 1; col <= n; col++){
                if (isOpen(row, col)){
                    opened++;
                }
            }
        }
        return opened;
    }

    public boolean percolates(){
        return grid.connected(top, bottom);
    }

}