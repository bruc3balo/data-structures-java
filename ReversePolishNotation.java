import java.util.Arrays;
import java.util.Stack;

class ReversePolishNotation {
    public static void main(String[] args) {
        //String[] tokens = new String[]{"2","1","+","3","*"}; //9
        //String[] tokens = new String[]{"4","13","5","/","+"}; //6
        String[] tokens = new String[]{"10", "6", "9", "3", "+", "-11", "*", "/", "*", "17", "+", "5", "+"}; //6
        System.out.println("Value is " + evalRPN(tokens));
        System.out.println("\nExpected 9");
    }

    public enum Ops {

        add("+"),
        sub("-"),
        mul("*"),
        div("/");

        private final String op;

        Ops(String op) {
            this.op = op;
        }

        static Ops getByValue(String s) {
            return Arrays.stream(values()).filter(i -> i.op.equals(s)).findFirst().orElse(null);
        }
    }

    //O(n) time
    //O(n) space
    public static int evalRPN(String[] tokens) {
        Stack<String> stack = new Stack<>();
        String toStack = null;

        Integer a;
        Integer b;

        Ops ops;

        for (String current : tokens) {
            if (!isNumber(current)) {

                ops = Ops.getByValue(current);
                b = Integer.parseInt(stack.pop());
                a = Integer.parseInt(stack.pop());

                switch (ops) {
                    case add -> toStack = String.valueOf(a + b);
                    case sub -> toStack = String.valueOf(a - b);
                    case mul -> toStack = String.valueOf(a * b);
                    case div -> toStack = String.valueOf(a / b);
                }

            } else {
                toStack = current;
            }

            stack.push(toStack);
            System.out.println(stack);
        }

        System.out.println(stack.stream().toList());
        System.out.println(stack.peek());

        return Integer.parseInt(stack.pop());
    }

    public static boolean isNumber(String c) {
        try {
            Integer.parseInt(c);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}
