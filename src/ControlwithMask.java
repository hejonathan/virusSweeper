import java.util.*;
/**
 *TODO: Protect from dumbass users
 * (what if user inputs -1 for an array index?)
 */
public class ControlwithMask {
    public int[][] count;
    public boolean[][] revealed;
    public boolean[][] flagged;
    public boolean[][] mine;
    public int numMask;
    public int numRows, numCols;
    private int[] dx = {-1, -1, -1, 0, 0, 1, 1, 1};
    private int[] dy = {-1, 0, 1, -1, 1, -1, 0, 1};
    public void initialize(int mines, int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        count = new int[numRows][numCols];
        revealed = new boolean[numRows][numCols];
        flagged = new boolean[numRows][numCols];
        mine = new boolean[numRows][numCols];
        for (int i=0; i<mines;) {
            int randX = (int)(Math.random()*numRows);
            int randY = (int)(Math.random()*numCols);
            if (mine[randX][randY]) continue;
            i++;
            mine[randX][randY] = true;
        }
        numMask = mines/10;
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                for (int k=0; k<8; k++) {
                    if (i+dx[k]>=0 && i+dx[k]<numRows && j+dy[k]>=0 && j+dy[k]<numCols && mine[i+dx[k]][j+dy[k]]) {
                        count[i][j]++;
                    }
                }
            }
        }
    }
    //Returns true when successfully revealed location x, y (KILL PLAYER IF FALSE)
    public boolean reveal (int x, int y) {
        if (mine[x][y]) {
            if(numMask>0) numMask--;
            else return false;
        }
        revealed[x][y] = true;
        if (count[x][y]!=0) return true;
        LinkedList<Integer> lx = new LinkedList<>();
        LinkedList<Integer> ly = new LinkedList<>();
        lx.add(x);
        ly.add(y);
        while (!lx.isEmpty()) {
            int cx = lx.removeFirst();
            int cy = ly.removeFirst();
            for (int k=0; k<8; k++) {
                int nx = cx+dx[k];
                int ny = cy+dy[k];
                if (nx>=0 && nx<numRows && ny>=0 && ny < numCols && !revealed[nx][ny]) {
                    revealed[nx][ny] = true;
                    if (count[nx][ny]==0) {
                        lx.addLast(nx);
                        ly.addLast(ny);
                    }
                }
            }
        }
        return true;
    }
    //Flags a square
    public void flag(int x, int y) {
        flagged[x][y] = true;
    }
    public boolean isFinished() {
        for (int i=0; i<numRows; i++) {
            for (int j=0; j<numCols; j++) {
                if (!revealed[i][j] && !mine[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
}