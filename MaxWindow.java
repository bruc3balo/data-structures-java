import java.util.*;

class MaxWindow {
    public static void main(String[] args) {
        int[] nums = new int[]{1, 3, -1, -3, 5, 3, 6, 7};
        int k = 3;
//        int[] nums = new int[]{1};
//        int k = 1;
        System.out.println(Arrays.stream(maxSlidingWindow4(nums, k)).boxed().toList());
    }

    // prio que
    public static int[] maxSlidingWindow(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while (end < nums.length) {

            //max b4 slide
            PriorityQueue<Integer> q = new PriorityQueue<>(Comparator.comparingInt(a -> -a));
            q.addAll(l.subList(start, end + 1));
            list.add(q.poll());

            //slide
            start++;
            end++;
        }


        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

    //sort
    public static int[] maxSlidingWindow2(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while (end < nums.length) {

            //max b4 slide
            List<Integer> integers = l.subList(start, end + 1).stream().sorted().toList();
            list.add(integers.get(integers.size() - 1));

            //slide
            start++;
            end++;
        }


        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

    //sort 3& priority
    public static int[] maxSlidingWindow3(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        List<Integer> sub = new ArrayList<>();
        LinkedHashMap<Integer, List<Integer>> map = new LinkedHashMap<>();
        LinkedHashMap<Integer, List<Integer>> orderMap = new LinkedHashMap<>();
        PriorityQueue<Map.Entry<Integer, List<Integer>>> q = new PriorityQueue<>(Map.Entry.comparingByKey(Comparator.comparingInt(a -> -a)));


        for (int i = 0; i < nums.length; i++) {
            sub.add(nums[i]);
            int finalI = i;
            map.compute(nums[i], (key, value) -> {
                if (value == null) value = new ArrayList<>();
                value.add(finalI);
                return value;
            });
        }

        q.addAll(map.entrySet());


        while (!q.isEmpty()) {
            Map.Entry<Integer, List<Integer>> poll = q.poll();
            orderMap.put(poll.getKey(), poll.getValue());
        }

        while (end < nums.length) {
            List<Integer> subList = sub.subList(start, end + 1);
            for (Integer key : orderMap.keySet()) {
                if (subList.contains(key)) {
                    list.add(key);
                    break;
                }
            }

            end++;
            start++;
        }


        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }


    public static int[] maxSlidingWindow4(int[] nums, int k) {
        List<Integer> list = new ArrayList<>();
        int end = k - 1;
        int start = 0;

        //List<Integer> l = Arrays.stream(nums).boxed().toList();

        //slide window
        while (end < nums.length) {

            int max = Integer.MIN_VALUE;
            //max b4 slide
            for (int i = start; i <= end; i++) max = Math.max(max, nums[i]);
            list.add(max);

            //slide
            start++;
            end++;
        }

        int[] res = new int[list.size()];
        for (int i = 0; i < list.size(); i++) res[i] = list.get(i);

//        list.stream().mapToInt(i->i).toArray();
        return res;
    }

}
