import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

class PacificAtlantic {
    public static void main(String[] args) {

    /*   int[][] heights = new int[][] {
                new int[]{1, 2, 3},
                new int[]{8, 9, 4},
                new int[]{7, 6, 5},
        }; */

    /* int[][] heights = new int[][] {
                new int[]{1,2,2,3,5},
                new int[]{3,2,3,4,4},
                new int[]{2,4,5,3,1},
                new int[]{6,7,1,4,5},
                new int[]{5,1,1,2,4},
        };*/


        int[][] heights = new int[][]{
                new int[]{1, 2, 3, 4},
                new int[]{12, 13, 14, 5},
                new int[]{11, 16, 15, 6},
                new int[]{10, 9, 8, 7},
        };

        System.out.println("Output is " + pacificAtlantic(heights));
        System.out.println("Expected is [[0, 3], [1, 0], [1, 1], [1, 2], [1, 3], [2, 0], [2, 1], [2, 2], [2, 3], [3, 0], [3, 1], [3, 2], [3, 3]]");
    }


    public static List<List<Integer>> pacificAtlantic(int[][] heights) {
        if (heights.length == 0) return new ArrayList<>();

        HashSet<List<Integer>> paths = new HashSet<>();
        HashSet<List<Integer>> pacific = new HashSet<>();
        HashSet<List<Integer>> atlantic = new HashSet<>();

        for (int i = 0; i < heights.length; i++)
            for (int j = 0; j < heights[0].length; j++) {

                if (j == 0 || i == 0) {
                    pacific.add(getPoints(i, j));
                }

                if (j == heights[0].length - 1 || i == heights.length - 1) {
                    atlantic.add(getPoints(i, j));
                }

            }


        for (List<Integer> i : new ArrayList<>(pacific)) {
            dfsPacific(i.get(0), i.get(1), 0, heights, pacific);
        }


        for (List<Integer> i : new ArrayList<>(atlantic)) {
            dfsAtlantic(i.get(0), i.get(1), 0, heights, atlantic);
        }

        //System.out.print("\n atlantic");
        //System.out.print(atlantic);

        for (List<Integer> i : atlantic) {
            if (pacific.contains(i)) paths.add(i);
        }


        return new ArrayList<>(paths);
    }

    private static void dfsPacific(int i, int j, int prev, int[][] h, HashSet<List<Integer>> p) {

        if (i < 0 || i > h.length - 1) return;
        if (j < 0 || j > h[0].length - 1) return;
        if (h[i][j] < prev) return;

        p.add(getPoints(i, j));


        //left
        if (!p.contains(getPoints(i, j - 1))) {
            dfsPacific(i, j - 1, h[i][j], h, p);
        }

        //right
        if (!p.contains(getPoints(i, j + 1))) {
            dfsPacific(i, j + 1, h[i][j], h, p);
        }

        //top
        if (!p.contains(getPoints(i - 1, j))) {
            dfsPacific(i - 1, j, h[i][j], h, p);
        }

        //bottom
        if (!p.contains(getPoints(i + 1, j))) {
            dfsPacific(i + 1, j, h[i][j], h, p);
        }

    }

    private static void dfsAtlantic(int i, int j, int prev, int[][] h, HashSet<List<Integer>> a) {

        if (i < 0 || i > h.length - 1) return;
        if (j < 0 || j > h[0].length - 1) return;
        if (h[i][j] < prev) return;
        a.add(getPoints(i, j));


        //left
        if (!a.contains(getPoints(i, j - 1))) {
            dfsAtlantic(i, j - 1, h[i][j], h, a);
        }

        //right
        if (!a.contains(getPoints(i, j + 1))) {
            dfsAtlantic(i, j + 1, h[i][j], h, a);
        }

        //top
        if (!a.contains(getPoints(i - 1, j))) {
            dfsAtlantic(i - 1, j, h[i][j], h, a);
        }

        //bottom
        if (!a.contains(getPoints(i + 1, j))) {
            dfsAtlantic(i + 1, j, h[i][j], h, a);
        }

    }


    private static List<Integer> getPoints(int i, int j) {
        return List.of(i, j);
    }


}
