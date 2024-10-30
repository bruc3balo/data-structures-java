import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

class TopKElements {
    public static void main(String[] args) {
        //int[] nums = new int[]{5,5,5,7,7,10};
        int k = 2;
        int[] nums = new int[]{1, 2};
        int[] topKElements = topKFrequenBalo(nums, k);

        for (int i : topKElements) {
            System.out.println(i + ", \n");
        }
    }

    public static int[] topKFrequenBalo(int[] nums, int k) {

        int[] res = new int[k];
        //fast look up time
        //store value & frequency
        HashMap<Integer, Integer> frequenciesMap = new HashMap<>();

        //early exit
        if (nums.length == 1) {
            return nums;
        }

        // O(n)
        for (int i = 0; i < nums.length; i++) {
            int n = nums[i];
            if (frequenciesMap.containsKey(n)) {
                int oldFrequency = frequenciesMap.get(n);
                int newFrequency = ++oldFrequency;

                frequenciesMap.put(n, newFrequency);
            } else {
                frequenciesMap.put(n, 1);
            }
        }

        System.out.println(frequenciesMap.toString());

        //to order frequencies
        final Comparator<Map.Entry<Integer, Integer>> valueComparator = Map.Entry.comparingByValue();

        //to store ordered pairs
        PriorityQueue<Map.Entry<Integer, Integer>> frequecyQueue = new PriorityQueue<>(valueComparator.reversed());

        //O(n)
        for (Map.Entry<Integer, Integer> set : frequenciesMap.entrySet()) {
            frequecyQueue.offer(set);
        }

        int size = frequecyQueue.size();

        //O(n)
        for (int i = 0; i < size; i++) {
            if (i == k) break;
            //O(1)
            res[i] = frequecyQueue.poll().getKey();
        }

        return res;
    }


}
