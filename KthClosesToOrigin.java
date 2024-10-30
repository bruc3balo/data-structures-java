import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

class KthClosesToOrigin {
    public static void main(String[] args) {
        int[][] points = new int[][]{new int[]{1, 3}, new int[]{-2, 2}};
        int k = 2;
        System.out.println("Output is ");
        Arrays.stream(kClosest(points, k)).forEach(a -> System.out.println(Arrays.stream(a).boxed().toList()));
        System.out.println("Expected output is ");
        Arrays.stream(kClosestNeet(points, k)).forEach(a -> System.out.println(Arrays.stream(a).boxed().toList()));

    }

    public static int[][] kClosest(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>(Comparator.comparingInt((int[] a) -> a[0]));

        for (int[] point : points) {

            int x = point[0];
            int y = point[1];

            int key = (int) getDistance(x, y);

            q.offer(new int[]{key, x, y});
        }


        int kth = 0;
        int[][] list = new int[k][2];


        while (k > 0) {
            int[] m = q.poll();
            list[kth] = new int[]{m[1], m[2]};
            k--;
            kth++;
        }

        return list;
    }

    private static double getDistance(int x1, int y1) {

        double x = Math.pow(x1, 2);
        double y = Math.pow(y1, 2);

        return x + y;
    }

    public static int[][] kClosestNeet(int[][] points, int k) {
        PriorityQueue<int[]> q = new PriorityQueue<>((a, b) -> Integer.compare((b[0] * b[0] + b[1] * b[1]), (a[0] * a[0] + a[1] * a[1]))); //only this is changed (swapped)
        for (int[] point : points) {
            q.add(point);
            //remove when size increase k
            if (q.size() > k) {
                q.remove();
            }
        }
        int[][] ans = new int[k][2];
        for (int i = 0; i < k; i++) {
            int[] cur = q.poll();
            ans[i][0] = cur[0];
            ans[i][1] = cur[1];
        }
        return ans;
    }

}
