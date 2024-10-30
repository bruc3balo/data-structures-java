import java.util.Arrays;
import java.util.HashSet;

class LongestStreak {
    public static void main(String[] args) {
        // int[] nums = new int[]{0, 1, 2, 4, 8, 5, 6, 7, 9, 3, 55, 88, 77, 99, 999999999};
        //int[] nums = new int[]{0, 1, 1 ,2};
        int[] nums = new int[]{100, 4, 200, 1, 3, 2};
        System.out.println("Longest streak is " + longestConsecutive(nums));
        System.out.println("Expected streak is " + longestConsecutiveBalo(nums));
    }

    public static int longestConsecutive(int[] nums) {
        if (nums.length == 0) return 0;

        HashSet<Integer> nextValues = new HashSet<>();

        int min = 0;
        int max = 0;

        for (int i : nums) {
            max = Math.max(max, i);
            min = Math.min(min, i);
            nextValues.add(i);
        }

        int streak = 0;
        int mostStreak = 0;

        for (int i = min; i <= max; ++i) {
            if (nextValues.contains(i)) {
                streak++;
                nextValues.remove(i);
            } else {
                streak = 0;
            }

            mostStreak = Math.max(streak, mostStreak);

            nums = Arrays.stream(nums).boxed().distinct().sorted().mapToInt(b -> b).toArray();
            if (nextValues.size() <= mostStreak && streak == 0) break;
        }
        return mostStreak;
    }

    public static int longestConsecutiveBalo(int[] nums) {
        if (nums.length == 0) return 0;
        Arrays.sort(nums);

        int streak = 1;
        int longestStreak = 1;

        for (int i = 0; i < nums.length - 1; i++) {
            int current = nums[i];
            int next = nums[i + 1];

            if (current + 1 == next) {
                streak++;
            } else if (current == next) {
                //do nothing
                continue;
            } else {
                //loop broken
                streak = 1;
            }

            longestStreak = Math.max(longestStreak, streak);
        }

        return longestStreak;
    }
}
