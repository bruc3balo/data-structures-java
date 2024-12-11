public class FindTheNthNoIntThePattern {
//    Can you find the pattern?
//
//    Find the next number in sequence: 1, 11, 21, 1211, 111221, 312211, _____ 13112221
//
//    Once the pattern is found, write code to find the nth term in the sequence

//Differences
//1 - 11 = 10
//        21 - 11 = 10
//        1211 - 21 = 1190
//        111221 - 1211 = 110010
//        312211 - 111221 = 200990

// 1 - 11
// Pairing
// Concatinating — !!!
// No of occurences – !!!

    // 11 - 21
// 2(1 + 1) - 1

//111221 - 312211
//

//

    //3, 13, 1113, 3113, 132113, 1113122113


    // 132113 - 1 (ones), 1 (threes), 1 (twos), 2 (ones), 1 (threes)
    //    1113122113 - 3 (ones), 1 (three), 1 (one), 2 (twos), 2 (ones), 1 (three)
    //    = 311311222113


//Assumptions
//n -> 0 index
//sequence starts with 1
// imports

    public static void main(String[] args) {
        int n = 4;
        String nthElementInThePattern = findNthTerm(n);
        System.out.println("The " + n + "th element in the pattern is: " + nthElementInThePattern);
    }

    //1, 11, 21, 1211, 111221, 312211, _____ 13112221
    //n = 1
    static String findNthTerm(int n) {
        String result = "1";
        while (n > 0) {
            StringBuilder sb = new StringBuilder();
            int occurrences = 0;
            Character previous = result.charAt(0);

            for (Character c : result.toCharArray()) {

                //while characters are same
                //count the occurrences
                if (previous == c) occurrences++;
                else {
                    //occurrences + c
                    sb.append(occurrences).append(previous);
                    occurrences = 1;
                }

                previous = c;
            }

            if (occurrences > 0) sb.append(occurrences).append(previous);
            result = sb.toString();
            n--;
        }
        return result;
    }


}
