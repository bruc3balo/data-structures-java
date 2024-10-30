//two pointers
class ValidPalindrome {

    public static void main(String[] args) {
        //String s = "race a car";
        String s = "A man, a plan, a canal: Panama";
        System.out.println("is valid " + isPalindrome(s));
    }

    public static boolean isPalindrome(String s) {

        StringBuilder f = new StringBuilder();
        StringBuilder b = new StringBuilder();

        for (int i = 0; i < s.length(); i++) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                f.append(String.valueOf(s.charAt(i)).toLowerCase());
            }
        }

        for (int i = s.length() - 1; i >= 0; i--) {
            if (Character.isLetterOrDigit(s.charAt(i))) {
                b.append(String.valueOf(s.charAt(i)).toLowerCase());
            }
        }


        return b.toString().equals(f.toString());
    }

}
