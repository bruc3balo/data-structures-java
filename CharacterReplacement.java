class CharacterReplacement {
    public static void main(String[] args) {

        String s = "ABAB";
        int k = 2;

//        String s = "AABABBA";
//       int  k = 1;

//        String s = "ABAA";
//        int k = 0;

        System.out.println("Longest character streak " + characterReplacement(s, k));
    }

    public static int characterReplacement(String s, int k) {

        if (s.length() == 1) return 1;

        int recordLeft = 0;
        int recordRight = 0;
        int record = 1;

        int start = 0;
        Integer streak = null;


        //find longest streak
        for (int i = 0; i < s.length() - 1; i++) {

            //save start and end of the longest streak
            char c = s.charAt(i);
            char n = s.charAt(i + 1);

            //new streak starting
            if (streak == null) {
                start = i;
                streak = 1;
            }

            //break streak
            if (c != n || (i == s.length() - 2)) {

                //is longest streak?
                if (streak >= record) {
                    if (i == s.length() - 2 && c == n) {
                        streak++;
                        i++;
                    }

                    record = streak;
                    recordLeft = start;
                }

                //reset
                streak = null;
            } else {
                //continue streak
                streak++;
            }
        }

        //replace adjacent characters with similar
        return longestStreakFromIndex(s, recordLeft, k);
    }

    private static int longestStreakFromIndex(String s, int i, int k) {
        int streak = 1;
        int repl = k;

        char c = s.charAt(i);

        int left = i - 1;
        int right = i + 1;

        //go as left as possible
        while (left >= 0) {
            char n = s.charAt(left);
            if (n != c) {
                if (repl > 0) repl--;
                else break;
            }

            streak++;
            left--;
        }

        //go as right as possible
        while (right < s.length()) {
            char n = s.charAt(right);
            if (n != c) {
                if (repl > 0) repl--;
                else break;
            }
            streak++;
            right++;
        }

        return streak;
    }

}
