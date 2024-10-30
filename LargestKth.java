import java.util.PriorityQueue;

class LargestKth {
    public static void main(String[] args) {
        KthLargest kthLargest = new KthLargest(3, new int[]{4, 5, 8, 2});
        kthLargest.add(3);
    }

    static class KthLargest {

        PriorityQueue<Integer> nums = new PriorityQueue<>();
        int k;

        public KthLargest(int k, int[] nums) {
            this.k = k;
            for (int num : nums) this.nums.add(num);
        }

        public int add(int val) {
            nums.add(val);
            while (nums.size() > k) {
                nums.poll();
            }
            return nums.peek();
        }


    }
}
