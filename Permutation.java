//slidingWindow
class Permutation {
    public static void main(String[] args) {


        String s1 = "abc";
        String s2 = "ccccbbbbaaaa";

        System.out.println(checkInclusion(s1, s2));

    }

    public static boolean checkInclusion(String s1, String s2) {
        int target = transform(s1);
        int l = s1.length();
        int left = 0;
        int right = left + l - 1;


        //same length &&
        while (right < s2.length()) {
            String test = s2.substring(left, right + 1);

            int tt = transform(test);

            if (tt == target && isAnagram(s1, test)) {
                return true;
            }
            right++;
            left++;
        }

        return false;
    }

    public static int transform(String s) {
        int val = 0;

        for (int i = 0; i < s.length(); i++)
            val += 'a' - s.charAt(i);

        return val;
    }


    public static boolean isAnagram(String original, String test) {
        if (original.length() != test.length()) return false;

        int[] store = new int[26];

        for (int i = 0; i < original.length(); i++) {
            store[original.charAt(i) - 'a']++;
            store[test.charAt(i) - 'a']--;
        }

        for (int n : store) if (n != 0) return false;

        return true;
    }
}
