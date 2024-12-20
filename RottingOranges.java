import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

class RottingOranges {
    public static void main(String[] args) {
        /*int[][] grid = new int[][]{
                new int[]{rotten,fresh,fresh},
                new int[]{fresh,fresh,empty},
                new int[]{empty,fresh,fresh}
        };*/
        /*int[][] grid = new int[][]{
                new int[]{rotten,fresh,fresh},
                new int[]{empty,fresh,fresh},
                new int[]{fresh,empty,fresh}
        };*/

        //int[][] grid = new int[][]{new int[]{0,2}};

        int[][] grid = new int[][]{
                new int[]{2, 0, 1, 1, 1, 1, 1, 1, 1, 1}, new int[]{1, 0, 1, 0, 0, 0, 0, 0, 0, 1}, new int[]{1, 0, 1, 0, 1, 1, 1, 1, 0, 1}, new int[]{1, 0, 1, 0, 1, 0, 0, 1, 0, 1}, new int[]{1, 0, 1, 0, 1, 0, 0, 1, 0, 1},
                new int[]{1, 0, 1, 0, 1, 1, 0, 1, 0, 1}, new int[]{1, 0, 1, 0, 0, 0, 0, 1, 0, 1}, new int[]{1, 0, 1, 1, 1, 1, 1, 1, 0, 1}, new int[]{1, 0, 0, 0, 0, 0, 0, 0, 0, 1}, new int[]{1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };


        System.out.println("Output is " + orangesRotting(Arrays.copyOf(grid, grid.length)));
        System.out.println("Expected output is " + orangesRottingNeet(Arrays.copyOf(grid, grid.length)));
    }

    static final int rotten = 2;
    static final int fresh = 1;
    static final int empty = 0;
    static int freshF = 0;

    static Queue<List<Integer>> r = new LinkedList<>();

    public static int orangesRotting(int[][] grid) {
        int time = 0;

        for (int i = 0; i < grid.length; i++)
            for (int j = 0; j < grid[0].length; j++)
                if (grid[i][j] == fresh) freshF++;
                else if (grid[i][j] == rotten) r.offer(List.of(i, j));


        do {
            boolean change = false;
            int size = r.size();
            for (int i = 0; i < size; i++) {
                List<Integer> poll = r.poll();
                assert poll != null;
                if (minute(poll.get(0), poll.get(1), grid)) change = true;
            }

            if (change) time++;
        } while (freshF != 0 && !r.isEmpty());

        return freshF > 0 ? -1 : time;
    }

    private static boolean minute(int i, int j, int[][] g) {
        //Bound check
        if (i < 0 || i == g.length) return false;
        if (j < 0 || j == g[0].length) return false;

        //Infect check
        if (g[i][j] == empty) return false;


        if (g[i][j] == fresh) {
            g[i][j] = rotten;
            --freshF;
            r.add(List.of(i, j));
            return true;
        }

        boolean left = false;
        boolean right = false;
        boolean bottom = false;
        boolean top = false;

        //Can infect left?
        if (j - 1 >= 0 && g[i][j - 1] == fresh) left = minute(i, j - 1, g);

        //Can infect right?
        if (j + 1 < g[0].length && g[i][j + 1] == fresh) right = minute(i, j + 1, g);

        //Can infect bottom?
        if (i + 1 < g.length && g[i + 1][j] == fresh) bottom = minute(i + 1, j, g);

        //Can infect top?
        if (i - 1 >= 0 && g[i - 1][j] == fresh) top = minute(i - 1, j, g);

        //return left || right || bottom || top;
        return right || left || bottom || top;

    }


    public static int orangesRottingNeet(int[][] grid) {
        int m = grid.length, n = grid[0].length;
        Queue<int[]> queue = new LinkedList<>();
        int fresh = 0;

        for (int i = 0; i < m; i += 1) {
            for (int j = 0; j < n; j += 1) {
                if (grid[i][j] == 2) queue.offer(new int[]{i, j});
                else if (grid[i][j] == 1) fresh += 1;
            }
        }

        int count = 0;
        int[][] dirs = {{1, 0}, {-1, 0}, {0, 1}, {0, -1}};
        while (!queue.isEmpty() && fresh != 0) {
            count += 1;
            int sz = queue.size();
            for (int i = 0; i < sz; i += 1) {
                int[] rotten = queue.poll();
                int r = rotten[0], c = rotten[1];
                for (int[] dir : dirs) {
                    int x = r + dir[0], y = c + dir[1];
                    if (0 <= x && x < m && 0 <= y && y < n && grid[x][y] == 1) {
                        grid[x][y] = 2;
                        queue.offer(new int[]{x, y});
                        fresh -= 1;
                    }
                }
            }
        }
        return fresh == 0 ? count : -1;
    }

    public static int orangesRottingAce(int[][] grid) {
        int minutes = 0;
        Queue<int[]> minuteBatchQueue = new LinkedList<>();


        // Find all rotten oranges first O(m.n) time, O(m.n) space
        Queue<int[]> rottenOrangesQ = new LinkedList<>();
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                int orange = grid[row][column];

                //Add if rotten
                if (orange == 2) rottenOrangesQ.offer(new int[]{row, column});
            }
        }

        // Begin infecting other oranges from each of the rotten oranges i.e. Each infection is counted as a minute O(1) time, O(1) space
        boolean hasInfected = false;
        while (!rottenOrangesQ.isEmpty()) {


            //Infect
            int[] rottenCellIndex = rottenOrangesQ.poll();
            int row = rottenCellIndex[0];
            int column = rottenCellIndex[1];


            // TopCell
            if (row > 0 && grid[row - 1][column] == 1) {
                grid[row - 1][column] = 2;
                minuteBatchQueue.add(new int[]{row - 1, column});
                hasInfected = true;
            }

            // BottomCell
            if (row < grid.length - 1 && grid[row + 1][column] == 1) {
                grid[row + 1][column] = 2;
                minuteBatchQueue.add(new int[]{row + 1, column});
                hasInfected = true;
            }

            // LeftCell
            if (column > 0 && grid[row][column - 1] == 1) {
                grid[row][column - 1] = 2;
                minuteBatchQueue.add(new int[]{row, column - 1});
                hasInfected = true;
            }

            // RightCell
            if (column < grid[row].length - 1 && grid[row][column + 1] == 1) {
                grid[row][column + 1] = 2;
                minuteBatchQueue.add(new int[]{row, column + 1});
                hasInfected = true;
            }

            if (rottenOrangesQ.isEmpty()) {
                if (hasInfected) minutes++;
                while (!minuteBatchQueue.isEmpty()) rottenOrangesQ.offer(minuteBatchQueue.poll());
                hasInfected = false;
            }

        }

        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid[row].length; column++) {
                int orange = grid[row][column];

                //invalidate if any fresh
                if (orange == 1) return -1;
            }
        }

        return minutes;

    }
}
