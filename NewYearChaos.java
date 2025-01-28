import java.util.ArrayList;
import java.util.List;

public class NewYearChaos {
    public static void main(String[] args) {

//        List<Integer> q = List.of(1, 2, 3, 5, 4, 6, 7, 8, 9); //1
//        List<Integer> q = List.of(5, 1, 2, 3, 7, 8, 6, 4); //Too chaotic
        List<Integer> q = List.of(1, 2, 5, 3, 7, 8, 6, 4); //7
//        List<Integer> q = List.of(4, 1, 2, 3); //Too chaotic
//        List<Integer> q = List.of(2, 1, 5, 3, 4); //3
//        List<Integer> q = List.of(2, 5, 1, 3, 4); // Too chaotic
        minimumBribes(q);
    }


    public static void minimumBribes(List<Integer> q) {
        // Write your code here
        //Get element and find original position

        int bribes = 0;

        for (int i = 0; i < q.size(); i++) {
            int swapPosition = i + 1;
            int initialPosition = q.get(i);

            int difference = initialPosition - swapPosition;

            if (difference <= 0) continue;

            if (difference > 2) {
                System.out.println("Too chaotic");
                return;
            }


            bribes += difference;
        }


        System.out.println(bribes);

    }
}
