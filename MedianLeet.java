import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;

class MedianLeet {
    public static void main(String[] args) {
        MedianFinder2 medianFinder = new MedianFinder2();
        medianFinder.addNum(1);
        medianFinder.addNum(2);
        //medianFinder.addNum(3);
        //medianFinder.addNum(4);
        //medianFinder.addNum(5);

        System.out.println("Median is " + medianFinder.findMedian());
    }

    static class MedianFinder {

        final PriorityQueue<Integer> all = new PriorityQueue<>();

        public MedianFinder() {

        }

        public void addNum(int num) {
            all.add(num);
        }

        public double findMedian() {
            if (all.isEmpty()) return 0;
            if (all.size() == 1) return all.peek();
            int size = all.size();
            int half = size / 2;
            List<Integer> looked = new ArrayList<>();

            for (int i = 0; i < half; i++) looked.add(all.poll());


            if (size % 2 == 0) {
                //even
                //e.g. [1, 2] h = 1
                int a = looked.get(looked.size() - 1);
                int b = all.poll();

                looked.add(b);
                all.addAll(looked);

                return (double) (a + b) / 2;
            } else {
                //odd
                //e.g. [1, 2, 3] h = 1

                int b = all.poll();

                looked.add(b);
                all.addAll(looked);

                return b;
            }
        }


    }

    static class MedianFinder2 {

        final List<Integer> all = new ArrayList<>();

        public MedianFinder2() {

        }

        public void addNum(int num) {
            all.add(num);
            all.sort(Comparator.naturalOrder());
        }

        public double findMedian() {
            if (all.isEmpty()) return 0;
            if (all.size() == 1) return all.get(0);
            int size = all.size();
            int half = size / 2;


            if (size % 2 == 0) {
                //even
                //e.g. [1, 2] h = 1
                int a = all.get(half - 1);
                int b = all.get(half);

                return (double) (a + b) / 2;
            } else {
                //odd
                //e.g. [1, 2, 3] h = 1

                return all.get(half);
            }
        }


    }
}
