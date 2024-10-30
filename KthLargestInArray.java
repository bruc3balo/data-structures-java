import java.util.PriorityQueue;

class KthLargestInArray {
    public static void main(String[] args) {
        int[] nums = new int[]{3, 2, 3, 1, 2, 4, 5, 5, 6};
        int k = 4;
        System.out.println("output is " + findKthLargest(nums, k));
    }

    public static int findKthLargest(int[] nums, int k) {
        if (k < 1) throw new AssertionError();
        if (nums.length < 1) throw new AssertionError();
        PriorityQueue<Integer> pq = new PriorityQueue<>();

        for (int n : nums) {

            if (pq.size() < k) {
                pq.offer(n);
                continue;
            }

            if (n > pq.peek()) {
                pq.poll();
                pq.offer(n);
            }

        }

        assert !pq.isEmpty();
        return pq.poll();
    }


}
