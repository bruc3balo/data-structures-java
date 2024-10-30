import java.util.*;

public class MaxPalindrome {

    public static void main(String[] args) {

        Map<String, Integer> inputs = new HashMap<>();
        inputs.put("aaaabc", 2);
        inputs.put("dd", 0);
        inputs.put("fknfkn", 2);

        for (var e : inputs.entrySet()) {
            int result = solution(e.getKey());
            if (result != e.getValue()) throw new RuntimeException("Expected " + e.getValue() + " but got " + result);
        }

        System.out.println("Solution is valid");
    }

    public static int solution(String s) {
        int length = 3;

        final Set<String> palindromes = new HashSet<>();

        //Early exit
        if (s.length() < length) return 0;

        //Store occurrences
        Map<Character, Integer> occurrences = new HashMap<>();

        //Count occurrences
        for (char c : s.toCharArray()) occurrences.put(c, occurrences.getOrDefault(c, 0) + 1);

        List<Character> list = occurrences.entrySet()
                .stream()
                .sorted((a, b) -> -a.getValue().compareTo(b.getValue()))
                .flatMap(a -> {
                    List<Character> chars = new ArrayList<>();
                    for (char c : a.getKey().toString().repeat(a.getValue()).toCharArray()) chars.add(c);
                    return chars.stream();
                })
                .toList();


        //Order list from the highest occurrences to lowest
        List<Character> highToLow = new ArrayList<>(list);

        //Order list from the lowest occurrences to highest
        List<Character> lowToHigh = new ArrayList<>();
        for (int i = highToLow.size() - 1; i >= 0; i--) lowToHigh.add(highToLow.get(i));

        //While can construct a palindrome
        while (highToLow.size() > length - 1) {
            Character first = highToLow.remove(0);
            Character second = lowToHigh.remove(0);
            Character third = highToLow.remove(0);

            String word = first.toString() + second.toString() + third.toString();
            if (!isPalindrome(word, length)) break;

            palindromes.add(word);
        }

        //Return count of palindromes
        return palindromes.size();
    }

    public static boolean isPalindrome(String s, int length) {
        if (s.length() != length) return false;
        return s.charAt(0) == s.charAt(length - 1);
    }
}
