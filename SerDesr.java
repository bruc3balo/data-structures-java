import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class SerDesr {

    public static void main(String[] args) {
        TreeNode treeNode = new TreeNode(1);
        treeNode.left = new TreeNode(2);
        treeNode.right = new TreeNode(3);
        treeNode.right.right = new TreeNode(5);
        treeNode.right.left = new TreeNode(4);

        String serialize = serialize(treeNode);
        System.out.println("ser is " + serialize);

        TreeNode deserialize = deserialize(serialize);
        System.out.println("de is " + deserialize);

        System.out.println("equals " + treeNode.equals(deserialize));

    }

    private static HashMap<String, List<Integer>> b = new HashMap<>();
    private static String pre = "p";
    private static String in = "i";

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        b.clear();
        inOrder(root);
        preOrder(root);
        return fromMap(b);
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        b = toMap(data);
        return dfs(b.get(pre), b.get(in));
    }

    private static void inOrder(TreeNode node) {
        if (node == null) return;

        //L
        inOrder(node.left);

        //N
        List<Integer> l = b.getOrDefault(in, new ArrayList<>());
        l.add(node.getValue());
        b.put(in, l);

        //R
        inOrder(node.right);

    }

    private static void preOrder(TreeNode node) {
        if (node == null) return;

        //N
        List<Integer> l = b.getOrDefault(pre, new ArrayList<>());
        l.add(node.getValue());
        b.put(pre, l);

        //L
        preOrder(node.left);

        //R
        preOrder(node.right);

    }

    private static String fromList(List<Integer> list) {

        StringBuilder sb = new StringBuilder();

        for (int i : list) sb.append(i).append(",");

        return sb.toString();
    }

    private static List<Integer> toList(String s) {
        List<Integer> list = new ArrayList<>();
        if (s.equals("")) return list;
        boolean negative = false;
        for (int i = 0; i < s.length(); i++) {
            String a = String.valueOf(s.charAt(i));
            if (a.equals(",")) continue;
            else if (a.equals("-")) negative = true;
            else if (negative) {
                Integer k = Integer.parseInt("-".concat(a));
                list.add(k);
                negative = false;
            } else list.add(Integer.parseInt(a));
        }
        return list;
    }

    private static String fromMap(HashMap<String, List<Integer>> map) {
        StringBuilder sb = new StringBuilder();

        for (Map.Entry<String, List<Integer>> e : map.entrySet()) {
            sb.append(e.getKey()).append("=").append(fromList(e.getValue())).append(".");
        }

        return sb.toString();
    }

    private static HashMap<String, List<Integer>> toMap(String s) {
        HashMap<String, List<Integer>> map = new HashMap<>();
        if (s.equals("")) return map;
        for (int i = 0; i < s.length(); i++) {
            String a = String.valueOf(s.charAt(i));
            List<Integer> g = map.getOrDefault(a, new ArrayList<>());
            StringBuilder sb = new StringBuilder();
            for (int j = i + 1; j < s.length(); j++) {
                String v = String.valueOf(s.charAt(j));

                if (v.equals("=")) continue;
                else if (v.equals(".")) {
                    g = toList(sb.toString());
                    i = j;
                    break;
                } else {
                    sb.append(v);
                }
            }
            map.put(a, g);
        }
        return map;
    }

    private static TreeNode dfs(List<Integer> preorder, List<Integer> inorder) {
        if (preorder.size() == 0 || inorder.size() == 0) return null;

        int r = preorder.get(0);
        int mid = findMid(r, inorder);

        TreeNode root = new TreeNode(r);


        root.left = dfs(preorder.subList(1, mid + 1), inorder.subList(0, mid));
        root.right = dfs(preorder.subList(mid + 1, preorder.size()), inorder.subList(mid + 1, inorder.size()));

        return root;
    }

    public static int findMid(int mid, List<Integer> arr) {

        for (int i = 0; i < arr.size(); i++) {
            if (mid == arr.get(i)) return i;
        }

        return 0;
    }

}
