class ProductNotSelf {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 2, 3, 4};
        productExceptSelf(nums);
    }

    //O(n)
    public static int[] productExceptSelf(int[] nums) {
        int[] arr = new int[nums.length];

        //calculate initial values
        int prefix = 1, postfix = 1;

        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];

            //prefix array
            arr[i] = prefix;

            //next iteration calculation
            prefix = n * prefix;
            System.out.println("Store " + arr[i] + " and left is " + prefix);
        }

        for (int i = nums.length - 1; i >= 0; i--) {
            int n = nums[i];

            //postfix array
            arr[i] = arr[i] * postfix;

            //next iteration calculation
            postfix = postfix * n;
        }
        return arr;
    }

}
