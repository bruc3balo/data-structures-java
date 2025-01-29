import java.util.HashMap;
import java.util.List;

public class CheckMagazine {

    public static void main(String[] args) {

    }

    public static void checkMagazine(List<String> magazine, List<String> note) {
        // Write your code here
        final HashMap<String, Integer> wordMap = new HashMap<>();

        for(String word : magazine) wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);


        for(String word : note) {

            Integer count = wordMap.get(word);
            if(count == null || count == 0) {
                System.out.println("No");
                return;
            }

            wordMap.put(word, count - 1);

        }

        System.out.println("Yes");
    }


}
