class FindTheDuplicateNumber {
    public static void main(String[] args) {
//        int[] nums = new int[]{1,3,4,2,2};
//        int[] nums = new int[]{3,1,3,4,2};
//        int[] nums = new int[]{4,3,1,4,2};
        int[] nums = new int[]{9, 4, 9, 5, 7, 2, 8, 9, 3, 9};
        //System.out.println("output is "+ findDuplicateFloydBalo(nums));
        System.out.println("Expected output is " + findDuplicate(nums));
        System.out.println("Expected output is " + findDuplicateNeet(nums));
    }

    //O(n2) time
    //O(1) space
    public static int findDuplicateBalo(int[] nums) {
        int repeated = 0;
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];

            for (int j = i + 1; j < nums.length; j++) {
                int r = nums[j];

                if (r == n) return n;
            }
        }
        return repeated;
    }

    //O(1) space
    //O(n) time
    public static int findDuplicateFloydBalo(int[] nums) {
        //like counting sort
        //each value will point to another index in the array i.e. non of values are less than 1 or more than nums.length
        //none will point at index 0
        //min number is 1;
        //max number is n = nums.length - 1;
        //n + 1 = nums.length
        int slow = 0;
        int fast = 1;

        while (nums[slow] != nums[fast] || slow == fast) {
            slow = slow + 1 >= nums.length ? 1 : slow + 1;
            fast = fast + 2 >= nums.length ? 1 : fast + 2;
        }

        return nums[slow];
    }

    public static int findDuplicate(int[] nums) {
        ///Floyd's hare and tortoise algorithm
        //Conditions:
        //1. min number is 1;
        //2. max number is n = nums.length - 1;
        //3. n + 1 = nums.length
        //Only 1 duplicate

        //x -> distance before beginning of cycle i.e. x =k3l + z  ... 3 is subscript
        //y -> beginning of cycle to meeting point
        //z -> meeting point to rest of cycle
        //l -> length of cycle i.e. y + z
        //k -> no of loops
        //hare travel distance = x + kl + y
        //tortoise travels distance = x + 2kl + y
        //hare = 2 * tortoise


        int hare = nums[0];
        int tortoise = nums[0];
        //find point of intersection
        //y = z; i.e. p is distance between intersection and start a.k.a. 0
        //l + z = cycle i.e. z remainder point of intersection to cycle, l length of cycle
        //advance tortoise by 1 and hare by 2 till they meet
        ///2.tortoise = hare  ///hare -> y + 2l - x /// i.e. y = z


        do {
            tortoise = nums[tortoise];
            hare = nums[nums[hare]];
        } while (hare != tortoise);

        //create tortoise 2 variable for both tortoise to meet
        //they meet at beginning of cycle -> y
        //keep shifting by 1 till meet
        //tortoise is m here

        hare = nums[0];
        while (hare != tortoise) {

            //one node at a time
            hare = nums[hare];
            tortoise = nums[tortoise];

            //point of intersection is always going to be result
            // if (hare == tortoise) return tortoise;
        }
        //always meet at the entry point
        return tortoise;
    }

    public static int findDuplicateNeet(int[] nums) {
        //min number is 1;
        //max number is n = nums.length - 1;
        //n + 1 = nums.length

        int fast = nums[0];
        int slow = nums[0];
        boolean first = true;
        //find point of intersection
        //p = x; i.e. p is distance between intersection and start a.k.a. 0
        //c + x = cycle i.e. x remainder point of intersection to cycle, c length of cycle
        //advance slow by 1 and fast by 2 till they meet
        ///2.slow = fast  ///fast -> p + 2c - x /// i.e. p = x


        while (first || fast != slow) {
            if (first) first = false;
            slow = nums[slow];
            fast = nums[nums[fast]];
            if (fast == slow) break;
        }

        //create slow 2 variable for both slow to meet
        //keep shifting by 1 till meet
        int slow2 = nums[0];
        while (slow2 != slow) {
            if (first) first = false;
            slow2 = nums[slow2];
            slow = nums[slow];
            //point of intersection is always going to be result
            if (slow2 == slow) return slow;
        }
        return slow;
    }
}
