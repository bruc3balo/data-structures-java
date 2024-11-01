import java.util.HashSet;

public class MinSubstringNoDuplicate {



public static void main(String[] args) {
        System.out.println("======== Starting tests ======");

//        String test = "abacdec"; //3
//        String test = "world"; //1
//        String test = "dddd"; //4
//        String test = "cycle"; //2
        String test = "abba"; //2
        System.out.println("Result is " + solution(test));
    }
    public static int solution(String S) {
        //Early exit
        if (S.length() == 1) return 1;

        int result = 0;

        //Temporarily Store substrings
        // O(n) Memory
        final HashSet<Character> substringSet = new HashSet<>();

        // O(n) Time
        for (Character c : S.toCharArray()) {

            //Contains duplicate
            if (substringSet.contains(c)) {
                result++;
                substringSet.clear();
            }
            substringSet.add(c);
        }

        //Add the last substring
        return ++result;
    }
}
