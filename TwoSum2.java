import java.util.Arrays;
import java.util.HashMap;

class TwoSum2 {
    public static void main(String[] args) {
        int[] numbers = new int[]{2, 3, 4}; //1,3
        int target = 6;
        System.out.println("Output is " + Arrays.stream(twoSum2Pointer(numbers, target)).boxed().toList().toString());
    }

    public static int[] twoSum2(int[] numbers, int target) {

        HashMap<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < numbers.length; i++) {
            if (map.containsKey(numbers[i])) {
                return new int[]{map.get(numbers[i]) + 1, i + 1};
            }

            map.put(target - numbers[i], i);
        }

        return new int[]{};
    }

    public static int[] twoSum2Pointer(int[] numbers, int target) {
        int p1 = 0;
        int p2 = numbers.length - 1;
        int total;
        for (int i = 0; i < numbers.length; i++) {
            total = numbers[p1] + numbers[p2];

            if (total > target) {
                p2--;
                continue;
            }

            if (total < target) {
                p1++;
                continue;
            }

            return new int[]{++p1, ++p2};
        }

        return new int[]{};
    }

    public static int[] twoSum2Values(int[] numbers, int target) {
        int p1 = 0;
        int p2 = numbers.length - 1;
        int total;
        for (int i = 0; i < numbers.length; i++) {
            total = numbers[p1] + numbers[p2];

            if (total > target) {
                p2--;
                continue;
            }

            if (total < target) {
                p1++;
                continue;
            }

            return new int[]{numbers[p1], numbers[p2]};
        }

        return new int[]{};
    }
}
