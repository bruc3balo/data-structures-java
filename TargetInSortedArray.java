//https://neetcode.io/problems/find-target-in-rotated-sorted-array
public class TargetInSortedArray {

    public static void main(String[] args) {
        int[] nums = new int[]{3, 4, 5, 6, 1, 2};
        int target = 1;
        //output = 4

//        int[] nums = new int[]{3, 5, 6, 0, 1, 2};
//        int target = 4;
        //output = -1


        int result = search(nums, target);
        System.out.println("Result is " + result);
    }

    public static int search(int[] nums, int target) {

        //Early exit
        if (nums.length == 1) return nums[0] == target ? 0 : -1;

        int leftIndex = 0;
        int rightIndex = nums.length - 1;

        while (leftIndex != rightIndex) {

            int size = (rightIndex + 1) - leftIndex;
            int midSize = Math.floorDiv(size, 2);

            //Divide array into two

            //Left array
            int leftInBoundIndex = leftIndex;
            int leftInBoundValue = nums[leftInBoundIndex];

            int leftOutBoundIndex = leftIndex + (midSize - 1);
            int leftOutBoundValue = nums[leftOutBoundIndex];

            //Right array
            int rightInBoundIndex = leftIndex + midSize;
            int rightInBoundValue = nums[rightInBoundIndex];

            int rightOutBoundIndex = rightIndex;
            int rightOutBoundValue = nums[rightOutBoundIndex];

            //TODO: search between 2 arrays
            if (leftInBoundValue <= target || leftOutBoundValue <= target) {
                //left side
                if (leftInBoundValue == target) return leftInBoundIndex;
                else if (leftOutBoundValue == target) return leftOutBoundIndex;
                else rightIndex = leftInBoundIndex;

            } else {
                if(rightInBoundValue == target) return rightInBoundIndex;
                else if (rightOutBoundValue == target) return rightOutBoundIndex;
                leftIndex = rightInBoundIndex;
            }

        }


        return -1;
    }
}
