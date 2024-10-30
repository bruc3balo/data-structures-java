import java.util.Arrays;
import java.util.Stack;

class DailyTemperatures {
    public static void main(String[] args) {
        int[] temperatures = new int[]{73, 74, 75, 71, 69, 72, 76, 73};
        System.out.println("Output is " + Arrays.toString(dailyTemperatures(temperatures)));
        System.out.println("Expected Output is " + Arrays.toString(dailyTemperaturesNeet(temperatures)));
    }

    public static int[] dailyTemperatures(int[] temperatures) {

        //early exit
        if (temperatures.length == 1) return new int[]{0};


        int[] result = new int[temperatures.length];
        //in order
        //day 1

        main:
        for (int i = 0; i < temperatures.length; i++) {
            int day = temperatures[i];

            for (int next = i; next < temperatures.length; next++) {
                int nextDay = temperatures[next];
                if (nextDay > day) {
                    result[i] = next - i;
                    continue main;
                }
            }

            result[i] = 0;
        }


        return result;
    }


    public static int[] dailyTemperaturesNeet(int[] temperatures) {
        int[] ans = new int[temperatures.length];
        Stack<Integer> stack = new Stack<>();
        for (int currDay = 0; currDay < temperatures.length; currDay++) {
            //check previous indexes / days vs currentDay temp difference , if more, fill for result
            while (!stack.isEmpty() && temperatures[currDay] > temperatures[stack.peek()]) {
                int prevDay = stack.pop();
                ans[prevDay] = currDay - prevDay;
            }
            //store the index of previous day on the stack and check it in next iteration
            stack.add(currDay);
        }
        return ans;
    }

}
