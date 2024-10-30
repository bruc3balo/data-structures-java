import java.util.*;

class BTORightSide {
    public static void main(String[] args) {
        result.clear();
        MyTree root = new MyTree(new TreeNode(3));
        root.insert(9);
        root.insert(20);
        root.insert(15);
        root.insert(7);


        System.out.println("output is " + levelOrder(root.root));

        System.out.println("Expected output is " + rightSideViewNeet(root.root));


    }

    private static HashMap<Integer, List<Integer>> result = new HashMap<>();

    public static List<List<Integer>> levelOrder(TreeNode root) {

        int level = 0;

        if (root != null) {
            result.put(level, List.of(root.getValue()));
            bfs(root, level + 1);
        }
        return result.values().stream().toList();
    }

    public static void bfs(TreeNode node, int level) {


        List<Integer> temp = result.get(level);

        List<Integer> list = temp != null ? temp : new ArrayList<>();

        if (node.left != null) list.add(node.left.getValue());

        if (node.right != null) list.add(node.right.getValue());

        if (!list.isEmpty()) result.put(level, list);

        if (node.left != null) bfs(node.left, level + 1);
        if (node.right != null) bfs(node.right, level + 1);
    }


    public static List<Integer> rightSideViewNeet(TreeNode root) {
        List<Integer> list = new ArrayList<>();
        if (root == null) return list;
        bfsNeet(list, root);
        return list;
    }

    public static void bfsNeet(List<Integer> list, TreeNode root) {
        Queue<TreeNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            int levelSize = q.size();
            for (int i = 0; i < levelSize; i++) {
                TreeNode cur = q.poll();
                if (i == 0) list.add(cur.getValue()); //last out
                if (cur.right != null) q.offer(cur.right);
                if (cur.left != null) q.offer(cur.left);
            }
        }
    }
}
