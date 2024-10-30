import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

class GenerateParentheses {
    public static void main(String[] args) {
        int n = 3;
        //System.out.println("Output is "+generateParenthesis(n));
        System.out.println("Expected output is " + generateParenthesisNeet(n));
    }

    public static List<String> generateParenthesis(int n) {
        List<String> result = new ArrayList<>();
        gen(0, 2 * n, result, new Stack<>());
        return result;
    }


    private static void gen(int diff, int n, List<String> result, Stack<Character> combinations) {

        if (diff < 0 || diff > n) {
            //base case
            return;
        }

        if (n == 0 && diff == 0) {
            //answer
            var it = combinations.iterator();
            StringBuilder sb = new StringBuilder();
            while (it.hasNext()) sb.append(it.next());
            result.add(sb.toString());
        } else {
            //add opening
            combinations.push('(');
            gen(diff + 1, n - 1, result, combinations);
            combinations.pop();

            //add closing
            combinations.push(')');
            gen(diff - 1, n - 1, result, combinations);
            combinations.pop();
        }

    }

    static Stack<Character> stack = new Stack<>();
    static List<String> res = new ArrayList<>();

    public static List<String> generateParenthesisNeet(int n) {
        backtrack(0, 0, n);
        return res;
    }

    private static void backtrack(int openN, int closedN, int n) {
        if (openN == closedN && closedN == n) {
            Iterator<Character> vale = stack.iterator();
            StringBuilder temp = new StringBuilder();
            while (vale.hasNext()) {
                temp.append(vale.next());
            }
            res.add(temp.toString());
        }
        if (openN < n) {
            stack.push('(');
            backtrack(openN + 1, closedN, n);
            stack.pop();
        }

        if (closedN < openN) {
            stack.push(')');
            backtrack(openN, closedN + 1, n);
            stack.pop();
        }
    }

}
