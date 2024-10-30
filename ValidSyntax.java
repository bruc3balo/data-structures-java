import java.util.Arrays;
import java.util.Stack;

//stack
class ValidSyntax {
    public enum SyntaxElements {

        b1("("),
        b2(")"),

        s1("["),
        s2("]"),

        c1("{"),
        c2("}");

        final String value;

        SyntaxElements(String value) {
            this.value = value;
        }

        static SyntaxElements getByValue(String s) {
            return Arrays.stream(values()).filter(i -> i.value.equals(s)).findFirst().orElse(null);
        }
    }

    public static void main(String[] args) {
        //String s = "(]";
        //String s = "()";
        //String s = "()[]{}";

        String s = "[](([[]]()))";
//        String s = "]";

        System.out.println(" is valid " + isValid(s));
    }

    public static boolean isValid(String s) {
        Stack<String> open = new Stack<>();

        for (int i = 0; i < s.length(); i++) {
            SyntaxElements in = SyntaxElements.getByValue(String.valueOf(s.charAt(i)));
            switch (in) {
                case b1, s1, c1 -> open.push(in.value);
                case b2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.b1.value)) return false;
                    open.pop();
                }
                case s2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.s1.value)) return false;
                    open.pop();
                }
                case c2 -> {
                    if (open.isEmpty() || !open.peek().equals(SyntaxElements.c1.value)) return false;
                    open.pop();
                }
            }
        }

        return open.isEmpty();
    }

}
