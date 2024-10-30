import java.util.HashMap;
import java.util.Map;

class TwoSum {

    public static void main(String[] args) {
        int[] nums = new int[]{2, 7, 11, 15};
        int target = 13;
        int[] output = twoSum(nums, target);
        final Map<Character, Integer> countInWindow = new HashMap<>();


        for (int j : output) {
            System.out.println("Output is " + j);
        }

    }

    // O(n)
    public static int[] twoSum(int[] nums, int target) {
        //save complements with their index
        HashMap<Integer, Integer> complementIndicesMap = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int currentVal = nums[i];
            int complement = target - currentVal;

            //check if value exists
            if (complementIndicesMap.containsKey(currentVal)) {
                //return index of complement and current value index
                return new int[]{complementIndicesMap.get(currentVal), i};
            }


            //add complement to map
            complementIndicesMap.put(complement, i);

            //i.e. Value + Complement = target;
            // if you store complement, you need to find value
            // You say if i find your buddy, i will come back to you i.e. I n1 are looking for n2 ... if you find him tell him where i am, here's my location

            //Example
            //int[] nums = new int[]{2,7,11,15};
            //int target = 13;
            //Step 1: val = 2, comp = (13 - 2) = 11, target 13 ... look for 11 ? that is answer i.e (2 + 11 = 13)  : you store 11
            //Step 2: val = 7, comp = (13 - 7) = 6, target 13 ... look for 7 ? that is answer i.e (7 + 6 = 13) : you store 6
            //Step 3: val = 11, comp = (13 - 11) = 2, target 13 ... look for 11 ? that is answer i.e. (11 + 2 = 13) : you store 2
        }

        return new int[]{};
    }
}
