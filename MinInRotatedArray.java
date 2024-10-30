//https://neetcode.io/problems/find-minimum-in-rotated-sorted-array
public class MinInRotatedArray {
    public static void main(String[] args) {
//        int min = findMin(new int[]{3,4,5,6,1,2});
        int min = findMin(new int[]{4,5,0,1,2,3});

        System.out.println("Min is " + min);
    }

    public static int findMin(int[] nums) {

        //Early exit
        if (nums.length == 1) return nums[0];


        //Initialize min
        int min = nums[0];

        //Bound the array
        int leftIndex = 0;
        int rightIndex = nums.length - 1;

        //e.g. [4, 5, 0, 1, 2, 3] i.e. [4, 5, 0], [1, 2, 3]
        //

        while (leftIndex != rightIndex) {

            int arraySize = (rightIndex + 1) - leftIndex; //
            int midSize = Math.floorDiv(arraySize, 2);

            //Split the array into 2
            int leftInBoundIndex = leftIndex;
            int leftInBoundValue = nums[leftInBoundIndex];
            int leftOutBoundIndex = leftIndex + (midSize - 1);
            int leftOutBoundValue = nums[leftOutBoundIndex];

            int rightInBoundIndex = leftIndex + midSize;
            int rightInBoundValue = nums[rightInBoundIndex];
            int rightOutBoundIndex = rightIndex;
            int rightOutBoundValue = nums[rightOutBoundIndex];

            //Use smaller array i.e. smallest value

            if (leftOutBoundValue < rightInBoundValue && leftOutBoundValue < leftInBoundValue) {
                //use left side
                min = Math.min(min, Math.min(leftInBoundValue, leftOutBoundValue));
                rightIndex = leftOutBoundIndex;
            } else {
                //ruse right side
                min = Math.min(min, Math.min(rightInBoundValue, rightOutBoundValue));
                leftIndex = rightInBoundIndex;
            }
        }

        return min;
    }
}
