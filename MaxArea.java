class MaxArea {
    public static void main(String[] args) {
        int[][] grid = new int[][]{
                {0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 1, 1, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 0},
                {0, 1, 0, 0, 1, 1, 0, 0, 1, 1, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
        };

        System.out.println("Area is " + maxAreaOfIsland(grid));
    }

    public static int maxAreaOfIsland(int[][] grid) {
        if (grid.length == 0) return 0;

        int area = 0;

        for (int i = 0; i < grid.length; i++) {

            for (int j = 0; j < grid[0].length; j++) {

                int c = grid[i][j];
                if (c == 1) {
                    area = Math.max(dfs(i, j, grid), area);
                }

            }

        }


        return area;
    }

    private static int dfs(int i, int j, int[][] grid) {
        if (i < 0 || i >= grid.length) return 0;
        if (j < 0 || j >= grid[0].length) return 0;
        if (grid[i][j] == 0) return 0;

        grid[i][j] = 0;
        int area = 1;

        //left
        int l = dfs(i, j - 1, grid);

        //right
        int r = dfs(i, j + 1, grid);

        //bottom
        int b = dfs(i + 1, j, grid);

        //top
        int t = dfs(i - 1, j, grid);

        return l + r + b + t + area;
    }

}
