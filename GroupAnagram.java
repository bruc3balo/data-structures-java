import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

class GroupAnagram {

    public static void main(String[] args) {
        String[] strs = new String[]{"eat", "tea", "tan", "ate", "nat", "bat"};
        System.out.println(groupAnagrams(strs));
    }

    //O(mn)
    public static List<List<String>> groupAnagrams(String[] strs) {
        //return
        List<List<String>> res = new ArrayList<>();
        //early exit
        if (strs.length == 0) return res;
        //group anagrams
        HashMap<String, List<String>> map = new HashMap<>();

        // handle each entry
        //O(m)
        for (String s : strs) {
            //hold characters of string
            char[] hash = new char[26];
            //find offset of each character from a

            //O(n) * 26
            for (char c : s.toCharArray()) {
                hash[c - 'a']++;
            }

            //Construct char to string representation of chars
            String key = new String(hash);

            //find if key is absent add string to new list
            map.computeIfAbsent(key, k -> new ArrayList<>());

            //else add new group and add key
            map.get(key).add(s);
        }

        //add list of groups to main group
        res.addAll(map.values());
        return res;
    }

    public static List<List<String>> groupAnagramsAce(String[] strs) {
        HashSet<Integer> foundIndices = new HashSet<>();
        List<List<String>> result = new ArrayList<>();


        for (int i = 0; i < strs.length; i++) {
            if (foundIndices.contains(i)) continue;
            result.add(findAnagramsAce(strs, strs[i], foundIndices));
        }


        return result;
    }

    public static List<String> findAnagramsAce(String[] strs, String s, HashSet<Integer> foundIndices) {

        List<String> list = new ArrayList<>();
        list.add(s);

        String str;
        //assume no duplicates
        //loop


        for (int i = 0; i < strs.length; i++) {

            str = strs[i];

            if (str.equals(s)) continue;

            if (str.length() != s.length()) continue;

            if (foundIndices.contains(i)) continue;

            int[] offsets = new int[26];

            for (int j = 0; j < s.length(); j++) {
                offsets[s.charAt(j) - 'a']++;
                offsets[str.charAt(j) - 'a']--;
            }

            boolean isAnagram = true;

            for (int n : offsets) {
                if (n != 0) isAnagram = false;
            }

            if (!isAnagram) continue;

            //is anagram
            list.add(str);
            foundIndices.add(i);
        }

        return list;
    }

}
