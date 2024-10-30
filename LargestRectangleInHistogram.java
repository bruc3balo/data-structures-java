import java.util.Map;
import java.util.Stack;

class LargestRectangleInHistogram {
    public static void main(String[] args) {
//        int[] heights = new int[]{2,1,5,6,2,3};
        int[] heights = new int[]{2, 4};
//        int[] heights = new int[]{2, 1, 2};
        System.out.println(largestRectangleArea(heights));
    }

    public static int largestRectangleArea(int[] heights) {
        int area = 0;
        Stack<Map.Entry<Integer, Integer>> s = new Stack<>();

        //traverse
        for (int i = 0; i < heights.length; i++) {
            Integer h = heights[i];
            Integer start = i;
            while (!s.isEmpty() && s.peek().getValue() > h) {
                var p = s.pop();
                start = p.getKey();
                area = Math.max(area, findArea(i, p.getKey(), p.getValue()));
            }

            s.push(Map.entry(start, h));
        }


        int e = heights.length;
        while (!s.isEmpty()) {
            var p = s.pop();
            area = Math.max(area, findArea(p.getKey(), e, p.getValue()));
        }


        return area;
    }

    public static int findArea(int currentIndex, int boundaryIndex, int height) {
        int width = Math.abs(boundaryIndex - currentIndex);
        return width * height;
    }

}
