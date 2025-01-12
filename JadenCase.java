import java.util.*;
import java.util.stream.*;


public class JadenCase {

    public String toJadenCase(String phrase) {
        // TODO put your code below this comment

        return Arrays.stream(phrase.split(" "))
                .map(a -> a.substring(0, 1).toUpperCase().concat(a.substring(1)))
                .collect(Collectors.joining(" ")
                );
    }

}

