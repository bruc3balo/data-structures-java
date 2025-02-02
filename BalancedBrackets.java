import java.util.Stack;

public class BalancedBrackets {
    public static void main(String[] args) {
        //String s = "{(([])[])[]}"; //YES
        //String s = "{(([])[])[]]}"; //NO
        String s = "{(([])[])[]}[]"; //YES

        System.out.println(isBalanced(s));
    }

    public static String isBalanced(String s) {
        // Write your code here
        Stack<Character> stack = new Stack<>();

        for (Character c : s.toCharArray()) {

            switch (c) {
                case '(':
                case '[':
                case '{':
                    stack.push(c);
                    break;

                case ')':
                    if (stack.isEmpty()) return "NO";
                    if (stack.pop() != '(') return "NO";
                    break;

                case ']':
                    if (stack.isEmpty()) return "NO";
                    if (stack.pop() != '[') return "NO";
                    break;

                case '}':
                    if (stack.isEmpty()) return "NO";
                    if (stack.pop() != '{') return "NO";
                    break;
            }


        }

        return stack.isEmpty() ? "YES" : "NO";
    }
}
