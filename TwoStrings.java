import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

public class TwoStrings {
    public static void main(String[] args) {
        System.out.println(twoStrings("be", "cat"));
    }

    public static String twoStrings(String s1, String s2) {

        // Write your code here
        final HashSet<Character> s1Set = new HashSet<>();

        for (Character c : s1.toCharArray()) s1Set.add(c);

        for (Character c : s2.toCharArray()) if (s1Set.contains(c)) return "YES";

        return "NO";
    }
}
