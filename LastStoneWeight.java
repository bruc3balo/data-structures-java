import java.util.Arrays;
import java.util.PriorityQueue;

import static java.util.Comparator.reverseOrder;

class LastStoneWeight {
    public static void main(String[] args) {

        int[] stones = new int[]{2, 7, 4, 1, 8, 1};

        System.out.println("Output is " + lastStoneWeight(stones));
        System.out.println("Expected output is " + lastStoneWeightNeet(stones));
    }

    public static int lastStoneWeight(int[] stones) {

        if (stones.length == 1) return 1;

        PriorityQueue<Integer> m = new PriorityQueue<>(reverseOrder());

        for (int s : stones) m.offer(s);

        System.out.println(Arrays.stream(stones).boxed().toList());

        while (m.size() > 1) {
            int big = m.poll();
            int small = m.poll();


            //default destroyed

            //one more
            if (big > small) m.offer(big - small);
        }

        return m.isEmpty() ? 0 : m.poll();
    }

    public static int lastStoneWeightNeet(int[] stones) {
        PriorityQueue<Integer> maxHeap = new PriorityQueue<>();
        for (int stone : stones) maxHeap.add(-stone);
        while (maxHeap.size() > 1) {
            int stone1 = maxHeap.remove();
            int stone2 = maxHeap.remove();
            if (stone1 != stone2) maxHeap.add(stone1 - stone2);
        }
        return maxHeap.size() != 0 ? (-maxHeap.remove()) : 0;
    }
}
