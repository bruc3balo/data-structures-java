import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RotLeft {
    public static void main(String[] args) {
//        List<Integer> a = List.of(41, 73, 89, 7, 10, 1, 59, 58, 84, 77, 77, 97, 58, 1 ,86, 58, 26, 10, 86, 51); // 10
        List<Integer> a = List.of(1, 2, 3, 4, 5); // 4
        System.out.println("Rotating "+ rotLeft(a, 4));
    }

    public static List<Integer> rotLeft(List<Integer> a, int d) {
        // Write your code here
        final List<Integer> result = new ArrayList<>(a);

        int resIndex = Math.abs(d - a.size());

        for (Integer element : a) {
            result.set(resIndex, element);

            if (resIndex == (a.size() - 1)) resIndex = 0;
            else resIndex++;

        }


        return result;
    }
}
