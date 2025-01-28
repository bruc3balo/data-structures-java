import java.util.Arrays;
import java.util.List;

public class HourGlassSum {
    public static void main(String[] args) {
        List<List<Integer>> arr = Arrays.asList(
                Arrays.asList(1, 1, 1, 0, 0, 0),
                Arrays.asList(0, 1, 0, 0, 0, 0),
                Arrays.asList(1, 1, 1, 0, 0, 0),
                Arrays.asList(0, 0, 0, 0, 0, 0),
                Arrays.asList(0, 0, 0, 0, 0, 0),
                Arrays.asList(0, 0, 0, 0, 0, 0)
        );

        int result = hourglassSum(arr);
        System.out.println("Maximum Hourglass Sum: " + result);
    }

    public static int hourglassSum(List<List<Integer>> arr) {
        // Write your code here

        int maxSum = Integer.MIN_VALUE;

        //Traverse the array
        for (int row = 1; row < arr.size() - 1; row++) {

            for (int column = 1; column < arr.get(row).size() - 1; column++) {

                //For each cell, detect hour glass

                //Calculate and record sum

                int a = arr.get(row - 1).get(column - 1);
                int b = arr.get(row - 1).get(column);
                int c = arr.get(row - 1).get(column + 1);

                int d = arr.get(row).get(column);

                int e = arr.get(row + 1).get(column - 1);
                int f = arr.get(row + 1).get(column);
                int g = arr.get(row + 1).get(column + 1);

                int sum = a + b + c + d + e + f + g;
                maxSum = Math.max(sum, maxSum);
            }

        }

        return maxSum;

    }
}
