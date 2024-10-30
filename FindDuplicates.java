import java.util.HashSet;

class FindDuplicates {
    public static void main(String[] args) {

        int[] nums = new int[]{64, 25, 12, 22, 11, 64};

        // System.out.println("\n Does it have duplicates ? "+hasDuplicates(nums));

        // System.out.println("\n Does it have duplicates hash ? "+hasDuplicatedHashSet(nums));


        System.out.println("\n sorted array is: ");

        //bucketSort(new int[]{1,4,1,2,7,5,2,9,10});
        nums = Sort.bucketSort(nums);
        for (int i : nums) {
            System.out.println("\n v : " + i);
        }
    }

    // O(n)
    private static boolean hasDuplicates(int[] nums) {
        //early exit
        if (nums.length == 1) {
            System.out.print("Early exit");
            return false;
        }

        //edge cases
        //sort
        //selection sort
        //O(n)
        Sort.countingSort(nums);


        System.out.print("\n ");


        //merge sort the array
        //O(log n)


        int previousNum = nums[0];
        //look for O(n)
        for (int i = 1; i < nums.length; i++) {
            int current = nums[i];

            if (current == previousNum) {
                System.out.print("\n Found " + current + " and " + previousNum);
                return true;
            }
            previousNum = nums[i];
        }

        System.out.print("\n Not found");
        return false;
    }

    //O(n)
    private static boolean hasDuplicatedHashSet(int[] nums) {
        HashSet<Integer> seen = new HashSet<>();
        for (int i : nums) {
            if (seen.contains(i)) {
                return true;
            }
            seen.add(i);
        }
        return false;
    }

}
