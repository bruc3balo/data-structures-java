import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class SherlockAndValidString {
    public static void main(String[] args) {
        //aaaabbcc //NO
        //aaaaabc //NO
    }

    public static String isValid(String s) {
        // Write your code here
        //Store frequencies
        final HashMap<Character, Integer> frequencyMap = new HashMap<>();

        //Count frequencies
        for(Character c : s.toCharArray()) frequencyMap.put(c, frequencyMap.getOrDefault(c, 0) + 1);

        //Check different frequencies
        final HashMap<Integer, Integer> frequencyCounterMap = new HashMap<>();
        for(Map.Entry<Character, Integer> e : frequencyMap.entrySet()) frequencyCounterMap.put(e.getValue(), frequencyCounterMap.getOrDefault(e.getValue(), 0) + 1);

        if(frequencyCounterMap.size() == 1) return "YES";

        //Check if can use remove
        if(frequencyCounterMap.size() > 2) return "NO";

        //try use remove
        boolean usedRemove = false;
        List<Integer> f = frequencyCounterMap.values().stream().collect(Collectors.toList());

        if(f.get(0) > 1 && f.get(1) > 1) return "NO";

        return "YES";

    }
}
