import java.util.List;
import java.util.Stack;

public class LargestRectangle {
    public static void main(String[] args) {
        List<Integer> h = List.of(1, 2, 3, 4, 5);
        System.out.println("Largest Rectangle "+largestRectangleArea(h));
    }

    public static int largestRectangleArea(List<Integer> heights) {
        Stack<Integer> stack = new Stack<>();
        int maxArea = 0;
        int n = heights.size();

        for (int i = 0; i <= n; i++) {
            // If we reached the end, consider height = 0
            int currentHeight = (i == n) ? 0 : heights.get(i);

            // Maintain increasing order in the stack
            while (!stack.isEmpty() && currentHeight < heights.get(stack.peek())) {
                int height = heights.get(stack.pop());
                int width = stack.isEmpty() ? i : (i - stack.peek() - 1);
                maxArea = Math.max(maxArea, height * width);
            }

            stack.push(i);
        }

        return maxArea;
    }


    public static long largestRectangle(List<Integer> h) {
        // Write your code here
        long area = Integer.MIN_VALUE;
        //Stack<Integer> stack = new Stack<>();

        for(int i = 0 ; i < h.size(); i++) {
            int height = h.get(i);
            int width = 1;

            //how many buildings can i join to the right

            //join right
            for(int j = i + 1; j < h.size(); j++) {
                int rightHeight = h.get(j);
                if(rightHeight < height) break;
                width++;
            }

            long thisArea = (long) width * height;
            area = Math.max(area, thisArea);
        }

        return area;
    }
}
