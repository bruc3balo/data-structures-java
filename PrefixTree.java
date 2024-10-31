import java.util.HashMap;
import java.util.Map;

public class PrefixTree {

    final Map<Integer, Character> dic = new HashMap<>();

    public PrefixTree() {

    }

    public void insert(String word) {
        for (int i = 0; i < word.length(); i++) dic.put(i, word.charAt(i));
        dic.put(word.length(), '.');
    }

    public boolean search(String word) {
        for (int i = 0; i < word.length(); i++) if (!dic.containsKey(i)) return false;
        return dic.get(word.length()).equals('.');
    }

    public boolean startsWith(String prefix) {
        for (int i = 0; i < prefix.length(); i++) if (!dic.containsKey(i)) return false;
        return true;
    }

    public static void main(String[] args) {
        //["PrefixTree", "insert", "apple", "search", "apple", "search", "app", "startsWith", "app", "insert", "app", "search", "app"]
        PrefixTree tree = new PrefixTree();
        tree.insert("apple");
        boolean appleSearch = tree.search("apple");
        boolean appSearch = tree.search("app");
        boolean appStarts = tree.startsWith("app");
        tree.insert("app");
        boolean appS = tree.search("app");

        //[null,null,true,false,true,null,true]
    }

}
