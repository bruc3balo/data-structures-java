import java.util.Arrays;
import java.util.List;

public class CountSwaps {
    public static void main(String[] args) {
//        List<Integer> a = List.of(1, 2, 3);
//        List<Integer> a = Arrays.asList(3, 2, 1);
        List<Integer> a = Arrays.asList(4, 2, 3, 1);
        countSwaps(a);
    }

    public static void countSwaps(List<Integer> a) {
        // Write your code here

        int swaps = 0;

        for (int i = 0; i < a.size(); i++) {

            for (int j = 0; j < a.size() - 1; j++) {

                if (a.get(j) > a.get(j + 1)) {
                    //Hold variables
                    Integer large = a.get(j);
                    Integer small = a.get(j + 1);

                    //Swap variables
                    a.set(j, small);
                    a.set(j + 1, large);
                    swaps++;
                }

            }

        }

        System.out.println("Array is sorted in " + swaps + " swaps.");
        System.out.println("First Element: " + a.get(0));
        System.out.println("Last Element: " + a.get(a.size() - 1));
    }
}
