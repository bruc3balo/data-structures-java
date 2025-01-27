public class RepeatedString {

    public static void main(String[] args) {
        //s = aba, n = 10, output = 7
        //a = a, n = 1000000000000, output = 1000000000000
        System.out.println("Occurrences of a is " + repeatedString("aba", 10));
    }

    public static long repeatedString(String s, long n) {

        //abaabaabaa
        int occurencesInS = 0;
        for (Character c : s.toCharArray()) if (c == 'a') occurencesInS++;

        long completeRepetitions = n / s.length();
        long incompleteRepetitions = n % s.length();

        long totalOccurences = occurencesInS * completeRepetitions;

        for (Character c : s.toCharArray()) {
            if (incompleteRepetitions-- == 0) break;
            if (c == 'a') totalOccurences++;
        }


        return totalOccurences;
    }
}
