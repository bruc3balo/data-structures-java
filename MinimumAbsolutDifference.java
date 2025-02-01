import java.util.List;
import java.util.stream.Collectors;

public class MinimumAbsolutDifference {
    public static void main(String[] args) {

        List<Integer> arr = List.of(3, -7, 0);
        System.out.println("Difference is "+minimumAbsoluteDifference(arr));
    }

    public static int minimumAbsoluteDifference(List<Integer> arr) {
        // Write your code here
        int difference = Integer.MAX_VALUE;

        arr = arr.stream().sorted().collect(Collectors.toList());

        for(int a = 0; a < arr.size() - 1; a++) {
            int abDiff = Math.abs(arr.get(a) - arr.get(a + 1));
            difference = Math.min(difference, abDiff);
        }

        return difference;
    }

}
