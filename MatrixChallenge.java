import java.util.*;

public class MatrixChallenge {


    public static String MatrixChallenge(String[] strArr) {
        int rows = strArr.length;
        int cols = strArr[0].length();
        int[][] matrix = new int[rows][cols];

        // Parsing the input matrix
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] = Character.getNumericValue(strArr[i].charAt(j));
            }
        }

        int maxSum = Integer.MIN_VALUE; // Maximum sum
        int[] varOcg = {-1, -1}; // Best position in the matrix (__define-ocg__)

        // Define directions: up, down, left, right
        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};

        // Helper method to perform DFS and calculate the sum
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                boolean[][] visited = new boolean[rows][cols];
                maxSum = Math.max(maxSum, dfs(matrix, i, j, 0, 0, visited, varOcg));
            }
        }

        // Return the largest valid sum
        return Integer.toString(maxSum);
    }

    private static int dfs(int[][] matrix, int row, int col, int depth, int sum, boolean[][] visited, int[] varOcg) {
        if (depth == 3) {
            int newRow = sum / 10; // Extract row index
            int newCol = sum % 10; // Extract column index

            if (newRow >= 0 && newRow < matrix.length && newCol >= 0 && newCol < matrix[0].length) {
                varOcg[0] = newRow; // Update best row position (__define-ocg__)
                varOcg[1] = newCol; // Update best column position
                return sum;
            } else {
                return Integer.MIN_VALUE; // Out of bounds
            }
        }

        visited[row][col] = true; // Mark current cell as visited
        int maxResult = Integer.MIN_VALUE;

        int[][] directions = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}}; // Directions
        for (int[] dir : directions) {
            int newRow = row + dir[0];
            int newCol = col + dir[1];

            if (newRow >= 0 && newRow < matrix.length && newCol >= 0 && newCol < matrix[0].length && !visited[newRow][newCol]) {
                maxResult = Math.max(maxResult, dfs(matrix, newRow, newCol, depth + 1, sum + matrix[newRow][newCol], visited, varOcg));
            }
        }

        visited[row][col] = false; // Unmark current cell
        return maxResult;
    }

    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        System.out.print(MatrixChallenge(new String[]{"234", "999", "999"}));
    }

}
