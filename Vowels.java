import java.util.HashSet;
import java.util.List;

public class Vowels {

    public static void main(String[] args) {
        int count = getCount("o a kak ushakov lil vo kashu kakao");
    }
    
    public static int getCount(String str) {
        final HashSet<Character> vowels = new HashSet<>(List.of('a', 'e','i','o','u'));

        int count = 0;
        for(Character c : str.toCharArray()) if(vowels.contains(c)) count++;

        return count;
    }

}