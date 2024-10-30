import java.util.HashSet;

class NoOfIslands {

    static char water = '0';
    static char land = '1';

    public static void main(String[] args) {

        char[][] grid = new char[][]{
                {'1', '1', '0', '0', '0'},
                {'1', '1', '0', '0', '0'},
                {'0', '0', '1', '0', '0'},
                {'0', '0', '0', '1', '1'},
        };

        /* char[][] grid = new char[][]{
                {'1','1','1','1','0'},
                {'1','1','0','1','0'},
                {'1','1','0','0','0'},
                {'0','0','0','0','0'},
                };*/

        /*char[][] grid = new char[][]{
                {'1','1','1'},
                {'0','1','0'},
                {'0','1','0'},
        };*/


        System.out.println("output is " + numIslandsBalo(grid));
        System.out.println("Expected is 1");
    }

    public static int numIslandsBalo(char[][] grid) {
        int islands = 0;

        int v = grid.length;
        int e = grid[0].length;

        HashSet<String> processed = new HashSet<>();

        for (int i = 0; i < v; i++) {
            for (int j = 0; j < e; j++) {
                if (grid[i][j] == land)
                    if (!processed.contains(getPoint(i, j)))
                        if (hasIsland(i, j, grid, processed)) ++islands;
            }
        }


        return islands;
    }


    public static boolean hasIsland(int row, int col, char[][] grid, HashSet<String> processed) {
        HashSet<String> seen = new HashSet<>();
        dfs(row, col, grid, seen);
        processed.addAll(seen);
        return true;
    }

    public static void dfs(int row, int col, char[][] grid, HashSet<String> seen) {
        if (grid[row][col] == water || seen.contains(getPoint(row, col))) return; // base case

        //process
        seen.add(getPoint(row, col));

        //children
        //left
        if (col - 1 >= 0) dfs(row, col - 1, grid, seen);

        //right
        if (col + 1 < grid[0].length) dfs(row, col + 1, grid, seen);

        //top
        if (row - 1 >= 0) dfs(row - 1, col, grid, seen);

        //bottom
        if (row + 1 < grid.length) dfs(row + 1, col, grid, seen);
    }


    public static boolean isIsland(char top, char bottom, char left, char right) {
        return top == water && bottom == water && left == water && right == water;
    }

    public static String getPoint(int i, int j) {
        return String.valueOf(i).concat(String.valueOf(j));
    }
}
