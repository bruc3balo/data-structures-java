import java.util.Arrays;
import java.util.HashMap;
import java.util.PriorityQueue;

public class SmallestPositiveIntegerNotInA {
    public static void main(String[] args) {
        System.out.println("Hello world!");


        int[] A = new int[]{1, 3, 6, 4, 1, 2};

        System.out.println("Result map is " + solutionMap(A));
        System.out.println("Result var is " + solutionVariable(A));
        System.out.println("Result sort is " + solutionSort(A));

    }

    //44%
    public static int solutionMap(int[] A) {
        int MIN_POSITIVE_VALUE = 1;
        if (A.length == 0) return MIN_POSITIVE_VALUE;

        final HashMap<Integer, Integer> nextMap = new HashMap<>();
        final PriorityQueue<Integer> q = new PriorityQueue<>();

        // O(n)
        for (int current : A) {

            // Only +ve values
            if (current < 1) continue;

            // Possible value
            int possibleValue = current + 1;

            // Skip duplicates
            if (nextMap.containsKey(current)) continue;

            nextMap.put(current, possibleValue);
            q.offer(possibleValue);
        }

        // O(n)
        while (!q.isEmpty()) {
            Integer possibleValue = q.poll();

            // Not in A
            if (!nextMap.containsKey(possibleValue)) return possibleValue;

        }

        // No positive value found, return 1
        return MIN_POSITIVE_VALUE;
    }

    //11%
    public static int solutionVariable(int[] A) {
        Integer result = null;
        PriorityQueue<Integer> q = new PriorityQueue<>();
        for (int j : A) q.offer(j);

        while (!q.isEmpty()) {
            int current = q.poll();
            if (current <= 0) continue;

            int possibleResult = current + 1;

            boolean lastValue = q.isEmpty();

            if (!lastValue) {
                int next = q.poll();

                //Not in A
                if (current == next || possibleResult == next) continue;
            }

            //set result
            result = result == null ? possibleResult : Math.min(result, possibleResult);
        }

        if (result == null) result = 1;
        return result;
    }

    //55%
    public static int solutionSort(int[] A) {
        Integer result = null;
        Arrays.sort(A);

        for (int i = 0; i < A.length; i++) {
            int current = A[i];
            if (current <= 0) continue;

            int possibleResult = current + 1;
            boolean lastValue = i == A.length - 1;

            if (!lastValue) {
                int next = A[i + 1];

                //Not in A
                if (current == next || possibleResult == next) continue;
            }

            result = result == null ? possibleResult : Math.min(result, possibleResult);

        }

        if (result == null) result = 1;
        return result;
    }


}