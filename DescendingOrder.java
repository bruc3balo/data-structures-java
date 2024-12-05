import java.util.Arrays;

public class DescendingOrder {

    public static void main(String[] args) {
        int i = sortDesc(123);
        System.out.println(i);
    }

    public static int sortDesc(final int num) {
        //Your code

        char[] characters = String.valueOf(num).toCharArray();
        Arrays.sort(characters);

        StringBuilder sb = new StringBuilder();
        for (int i = characters.length - 1; i >= 0; i--) sb.append(characters[i]);

        return Integer.parseInt(sb.toString());
    }
}
