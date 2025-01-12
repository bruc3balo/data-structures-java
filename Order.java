import java.util.*;
import java.util.stream.*;

public class Order {
    public static String order(String words) {
        if(words == null || words.isEmpty()) return "";

        TreeMap<Integer, String> wMap = new TreeMap<>();
        wordLoop : for(String w : words.split(" ")) {

            for(Character c : w.toCharArray()) {

                if(Character.isDigit(c)) {
                    Integer a = c - '0';
                    wMap.put(a, w);
                    continue wordLoop;
                }

            }

        }

        return String.join(" ", wMap.values());
    }
}