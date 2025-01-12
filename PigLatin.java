import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.*;

public class PigLatin {

    public static void main(String[] args) {
        String output = pigIt("Hello world !");
        System.out.println(output);
    }

    public static String pigIt(String str) {
        Pattern p = Pattern.compile("\\p{Punct}");

        return Arrays.stream(str.split(" "))
                .map(w -> p.matcher(w).matches() ? w : w.substring(1).concat(w.substring(0,1).concat("ay")))
                .collect(Collectors.joining(" "));
    }
}